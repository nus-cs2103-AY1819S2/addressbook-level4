package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.hms.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.hms.ui.testutil.GuiTestAssert.assertListMatching;

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
import guitests.guihandles.CustomerListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.hms.TestApp;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.commands.ClearHotelManagementSystemCommand;
import seedu.hms.logic.commands.FindNameCommand;
import seedu.hms.logic.commands.ListCustomerCommand;
import seedu.hms.logic.commands.SelectCustomerCommand;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.testutil.TypicalCustomers;
import seedu.hms.ui.BrowserPanel;
import seedu.hms.ui.CommandBox;

/**
 * A system test class for HotelManagementSystem, which provides access to handles of GUI components and helper methods
 * for test verification.
 */

public abstract class HotelManagementSystemSystemTest {
    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
        Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);
    @ClassRule
    public static ClockRule clockRule = new ClockRule();
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
    protected HotelManagementSystem getInitialData() {
        return TypicalCustomers.getTypicalHotelManagementSystem();
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

    public CustomerListPanelHandle getCustomerListPanel() {
        return mainWindowHandle.getCustomerListPanel();
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
     * Displays all customers in the hms book.
     */
    protected void showAllCustomers() {
        executeCommand(ListCustomerCommand.COMMAND_WORD);
        assertEquals(getModel().getHotelManagementSystem().getCustomerList().size(),
            getModel().getFilteredCustomerList().size());
    }

    /**
     * Displays all customers with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showCustomersWithName(String keyword) {
        executeCommand(FindNameCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredCustomerList().size() < getModel().getHotelManagementSystem()
            .getCustomerList().size());
    }

    /**
     * Selects the customer at {@code index} of the displayed list.
     */
    protected void selectCustomer(Index index) {
        executeCommand(SelectCustomerCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getCustomerListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all customers in the hms book.
     */
    protected void deleteAllCustomers() {
        executeCommand(ClearHotelManagementSystemCommand.COMMAND_WORD);
        assertEquals(0, getModel().getHotelManagementSystem().getCustomerList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same customer objects as {@code expectedModel}
     * and the customer list panel displays the customers in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     CustomerModel expectedModel) {
        //assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new HotelManagementSystem(expectedModel.getHotelManagementSystem()),
            testApp.readStorageHotelManagementSystem());
        assertListMatching(getCustomerListPanel(), expectedModel.getFilteredCustomerList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code CustomerListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getCustomerListPanel().rememberSelectedCustomerCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertFalse(getCustomerListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the customer in the customer list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CustomerListPanelHandle#isSelectedCustomerCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getCustomerListPanel().navigateToCard(getCustomerListPanel().getSelectedCardIndex());
        String selectedCardName = getCustomerListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + selectedCardName.replaceAll
                (" ", "%20"));
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.", mue);
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getCustomerListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the customer list panel remain unchanged.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see CustomerListPanelHandle#isSelectedCustomerCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getCustomerListPanel().isSelectedCustomerCardChanged());
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
        assertListMatching(getCustomerListPanel(), getModel().getFilteredCustomerList());
        assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
            getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected CustomerModel getModel() {
        return testApp.getModel();
    }
}
