package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.finance.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.finance.ui.testutil.GuiTestAssert.assertListMatching;

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
import guitests.guihandles.RecordListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.finance.TestApp;
import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.ClearCommand;
import seedu.finance.logic.commands.ListCommand;
import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.logic.commands.SelectCommand;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.testutil.TypicalRecords;
import seedu.finance.ui.CommandBox;


/**
 * A system test class for FinanceTracker, which provides access to handles of GUI components and helper methods
 * for test verification.
 */

public abstract class FinanceTrackerSystemTest {
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

    protected FinanceTracker getInitialData() {
        return TypicalRecords.getTypicalFinanceTracker();
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

    public RecordListPanelHandle getRecordListPanel() {
        return mainWindowHandle.getRecordListPanel();
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

        //waitUntilBrowserLoaded(getBrowserPanel());
    }


    /**
     * Displays all records in the finance record.
     */

    protected void showAllRecords() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getFinanceTracker().getRecordList().size(), getModel().getFilteredRecordList().size());
    }


    /**
     * Displays all records with any parts of their names matching {@code keyword} (case-insensitive).
     */

    protected void showRecordsWithName(String keyword) {
        executeCommand(SearchCommand.COMMAND_WORD + " -name " + keyword);
        assertTrue(getModel().getFilteredRecordList().size() < getModel().getFinanceTracker().getRecordList().size());
    }


    /**
     * Selects the record at {@code index} of the displayed list.
     */

    protected void selectRecord(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getRecordListPanel().getSelectedCardIndex());
    }


    /**
     * Deletes all records in the finance record.
     */

    protected void deleteAllRecords() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getFinanceTracker().getRecordList().size());
    }


    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same record objects as {@code expectedModel}
     * and the record list panel displays the records in the model correctly.
     */

    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new FinanceTracker(expectedModel.getFinanceTracker()), testApp.readStorageFinanceTracker());
        assertListMatching(getRecordListPanel(), expectedModel.getFilteredRecordList());
    }


    /**
     * Calls {@code BrowserPanelHandle}, {@code RecordListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */

    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        //getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getRecordListPanel().rememberSelectedRecordCard();
    }


    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     *
     */

    protected void assertSelectedCardDeselected() {
        //assertEquals(BrowserPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertFalse(getRecordListPanel().isAnyCardSelected());
    }


    /**
     * Asserts that the browser's url is changed to display the details of the record in the record list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see RecordListPanelHandle#isSelectedRecordCardChanged()
     */

    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getRecordListPanel().navigateToCard(getRecordListPanel().getSelectedCardIndex());
        String selectedCardName = getRecordListPanel().getHandleToSelectedCard().getName();

        //assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getRecordListPanel().getSelectedCardIndex());
    }


    /**
     * Asserts that the browser's url and the selected card in the record list panel remain unchanged.
     *
     * @see RecordListPanelHandle#isSelectedRecordCardChanged()
     */

    protected void assertSelectedCardUnchanged() {
        //assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getRecordListPanel().isSelectedRecordCardChanged());
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
        assertListMatching(getRecordListPanel(), getModel().getFilteredRecordList());
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
