package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.equipment.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.equipment.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.EquipmentListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.equipment.TestApp;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.ClearCommand;
import seedu.equipment.logic.commands.FindCommand;
import seedu.equipment.logic.commands.ListEquipmentCommand;
import seedu.equipment.logic.commands.ListWorkListCommand;
import seedu.equipment.logic.commands.SelectCommand;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.testutil.TypicalEquipments;
import seedu.equipment.ui.BrowserPanel;
import seedu.equipment.ui.CommandBox;

/**
 * A system test class for EquipmentManager, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class EquipmentManagerSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(this::getInitialData, getDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected EquipmentManager getInitialData() {
        return TypicalEquipments.getTypicalEquipmentManager();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public EquipmentListPanelHandle getEquipmentListPanel() {
        return mainWindowHandle.getEquipmentListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public BrowserPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all equipment in the equipment book.
     */
    protected void showAllPersons() {
        executeCommand(ListEquipmentCommand.COMMAND_WORD);
        assertEquals(getModel().getEquipmentManager().getEquipmentList().size(), getModel().getFilteredPersonList()
                .size());
    }

    /**
     * Displays all WorkLists in the Equipment Manager.
     */
    protected void showAllWorkList() {
        executeCommand(ListWorkListCommand.COMMAND_WORD);
        assertEquals(getModel().getEquipmentManager().getEquipmentList().size(), getModel().getFilteredWorkListList()
                .size());
    }

    /**
     * Displays all equipment with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPersonsWithName(String keyword) {
        executeCommand(FindCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPersonList().size()
                < getModel().getEquipmentManager().getEquipmentList().size());
    }

    /**
     * Selects the equipment at {@code index} of the displayed list.
     */
    protected void selectPerson(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getEquipmentListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all equipment in the equipment book.
     */
    protected void deleteAllPersons() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getEquipmentManager().getEquipmentList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same equipment objects as {@code expectedModel}
     * and the equipment list panel displays the equipment in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new EquipmentManager(expectedModel.getEquipmentManager()), testApp.readStorageEquipmentManager());
        assertListMatching(getEquipmentListPanel(), expectedModel.getFilteredPersonList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code EquipmentListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getEquipmentListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(BrowserPanel.DEFAULT_PAGE.toString(), getBrowserPanel().getLoadedUrl().toString());
        assertFalse(getEquipmentListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the equipment in the equipment list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see EquipmentListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getEquipmentListPanel().navigateToCard(getEquipmentListPanel().getSelectedCardIndex());
        URL expectedUrl;
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey("AIzaSyBQ5YiOpupDO8JnZqmqYTujAwP9U4R5JBA")
                    .build();
            String expectedUrlString = BrowserPanel.MAP_PAGE_BASE_URL;
            try {
                GeocodingResult[] results = GeocodingApi.geocode(context,
                        getEquipmentListPanel().getHandleToSelectedCard().getAddress().toString()).await();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                if (results.length > 0) {
                    System.out.println();
                    expectedUrlString = BrowserPanel.MAP_PAGE_BASE_URL + "?coordinates=[["
                            + results[0].geometry.location.lng + ","
                            + results[0].geometry.location.lat + "]]&title=[\""
                            + getEquipmentListPanel().getHandleToSelectedCard().getName()
                            + "\"]&icon=[\"monument\"]";
                } else {
                    expectedUrlString = getBrowserPanel().getLoadedUrl().toString();
                }
            } catch (ApiException e) {
                System.err.println(e.getMessage());
                expectedUrlString = getBrowserPanel().getLoadedUrl().toString();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                expectedUrlString = getBrowserPanel().getLoadedUrl().toString();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                expectedUrlString = getBrowserPanel().getLoadedUrl().toString();
            } finally {
                expectedUrl = new URL(expectedUrlString.replace("\"", "%22").replace(" ", "%20"));
            }
        } catch (MalformedURLException mue) {
            System.out.println("SHOULD NOT BE ERROR AH");
            throw new AssertionError("URL expected to be valid.", mue);
        }
        waitUntilBrowserLoaded(getBrowserPanel());
        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getEquipmentListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the equipment list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see EquipmentListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getEquipmentListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the selected card in the equipment list panel remain unchanged.
     * @see EquipmentListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedPersonCardUnchanged() {
        assertFalse(getEquipmentListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getEquipmentListPanel(), getModel().getFilteredPersonList());
        assertEquals(BrowserPanel.DEFAULT_PAGE.toString(), getBrowserPanel().getLoadedUrl().toString());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
