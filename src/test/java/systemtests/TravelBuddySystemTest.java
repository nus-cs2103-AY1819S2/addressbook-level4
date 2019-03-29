package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.travel.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.travel.ui.testutil.GuiTestAssert.assertListMatching;

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

import guitests.guihandles.BrowserPanelHandle;
import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PlaceListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.travel.TestApp;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.commands.ClearCommand;
import seedu.travel.logic.commands.ListCommand;
import seedu.travel.logic.commands.SearchCommand;
import seedu.travel.logic.commands.SelectCommand;
import seedu.travel.model.Model;
import seedu.travel.model.TravelBuddy;
import seedu.travel.testutil.TypicalPlaces;
import seedu.travel.ui.BrowserPanel;
import seedu.travel.ui.CommandBox;

/**
 * A system test class for TravelBuddy, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class TravelBuddySystemTest {
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

        //waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected TravelBuddy getInitialData() {
        return TypicalPlaces.getTypicalTravelBuddy();
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

    public PlaceListPanelHandle getPlaceListPanel() {
        return mainWindowHandle.getPlaceListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    //public BrowserPanelHandle getBrowserPanel() {
    //return mainWindowHandle.getBrowserPanel();
    //}

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

        //waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Displays all places in the travel book.
     */
    protected void showAllPlaces() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getTravelBuddy().getPlaceList().size(), getModel().getFilteredPlaceList().size());
    }

    /**
     * Displays all places with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPlacesWithName(String keyword) {
        executeCommand(SearchCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPlaceList().size() < getModel().getTravelBuddy().getPlaceList().size());
    }

    /**
     * Selects the place at {@code index} of the displayed list.
     */
    protected void selectPlace(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getPlaceListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all places in the travel book.
     */
    protected void deleteAllPlaces() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getTravelBuddy().getPlaceList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same place objects as {@code expectedModel}
     * and the place list panel displays the places in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new TravelBuddy(expectedModel.getTravelBuddy()), testApp.readStorageTravelBuddy());
        assertListMatching(getPlaceListPanel(), expectedModel.getFilteredPlaceList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PlaceListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        //getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPlaceListPanel().rememberSelectedPlaceCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        //assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertFalse(getPlaceListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the place in the place list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PlaceListPanelHandle#isSelectedPlaceCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getPlaceListPanel().navigateToCard(getPlaceListPanel().getSelectedCardIndex());
        String selectedCardName = getPlaceListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll(" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPlaceListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the place list panel remain unchanged.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PlaceListPanelHandle#isSelectedPlaceCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        //assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPlaceListPanel().isSelectedPlaceCardChanged());
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
        assertListMatching(getPlaceListPanel(), getModel().getFilteredPlaceList());
        //assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
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
