package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilInfoPanelLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//import seedu.address.model.HealthWorkerBook;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertHealthWorkerListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertRequestListMatching;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.HealthWorkerListPanelHandle;
import guitests.guihandles.InfoPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.RequestListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;

import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.ClearCommand;
//import seedu.address.logic.commands.FilterHealthWorkerCommand;
//import seedu.address.logic.commands.ListHealthWorkerCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.request.DeleteRequestCommand;
import seedu.address.logic.commands.request.EditRequestCommand;
import seedu.address.logic.commands.request.FilterRequestCommand;
import seedu.address.logic.commands.request.ListRequestCommand;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.Model;
import seedu.address.model.RequestBook;
import seedu.address.testutil.TypicalHealthWorkers;
import seedu.address.testutil.TypicalRequests;
import seedu.address.ui.AutoCompleteTextField;
import seedu.address.ui.InfoPanel;

/**
 * A system test class for HealthWorkerBook and RequestBook, which provides access to handles of GUI components and
 * helper methods for test verification.
 */
public abstract class HealthHubSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", AutoCompleteTextField.ERROR_STYLE_CLASS);

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
        testApp = setupHelper.setupApplication(this::getInitialHealthWorkerData, getHealthWorkerDataFileLocation(),
                this::getInitialRequestData, getRequestDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilInfoPanelLoaded(getInfoPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the health worker book data to be loaded into the file {@link #getHealthWorkerDataFileLocation()}
     */
    protected HealthWorkerBook getInitialHealthWorkerData() {
        return TypicalHealthWorkers.getTypicalHealthWorkerBook();
    }

    /**
     * Returns the request book data to be loaded into the file {@link #getRequestDataFileLocation()}
     */
    protected RequestBook getInitialRequestData() {
        return TypicalRequests.getTypicalRequestBook();
    }

    /**
     * Returns the directory of the health worker data file.
     */
    protected Path getHealthWorkerDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING_HEALTHWORKERBOOK;
    }

    /**
     * Returns the directory of the health worker data file.
     */
    protected Path getRequestDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING_REQUESTBOOK;
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    public RequestListPanelHandle getRequestListPanel() {
        return mainWindowHandle.getRequestListPanel();
    }

    public InfoPanelHandle getInfoPanel() {
        return mainWindowHandle.getInfoPanel();
    }

    public HealthWorkerListPanelHandle getHealthWorkerListPanel() {
        return mainWindowHandle.getHealthWorkerListPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
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
        waitUntilInfoPanelLoaded(getInfoPanel());
    }

    /**
     * Displays all requests in the request book.
     */
    protected void showAllRequests() {
        executeCommand(ListCommand.COMMAND_WORD + " " + ListRequestCommand.COMMAND_OPTION);

        // for some reason list command has to be executed twice due to non-deterministic behaviour of command box
        executeCommand(ListCommand.COMMAND_WORD + " " + ListRequestCommand.COMMAND_OPTION);
        assertEquals(getModel().getRequestBook().getRequestList().size(), getModel().getFilteredRequestList().size());
    }

    /**
     * Displays all patients with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPatientsWithName(String keyword) {
        executeCommand(FilterRequestCommand.COMMAND_WORD + " request n/" + keyword);
        //assertTrue(getModel().getFilteredRequestList().size()
        // < getModel().getRequestBook().getRequestList().size());
    }

    /**
     * Selects the request at {@code index} of the displayed list.
     */
    protected void selectRequest(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getRequestListPanel().getSelectedCardIndex());
    }

    /**
     * Converts all ongoing requests to completed (for systemtests).
     */
    protected void convertOngoingRequests() {
        executeCommand(FilterRequestCommand.COMMAND_WORD + " request st/ONGOING");
        int size = getModel().getFilteredRequestList().size();
        for (int i = 1; i < size + 1; i++) {
            executeCommand(EditRequestCommand.COMMAND_WORD + " " + EditRequestCommand.COMMAND_OPTION
                    + " 1 st/COMPLETED");
            executeCommand(FilterRequestCommand.COMMAND_WORD + " request st/ONGOING");
        }
    }

    /**
     * Deletes all requests in the request book.
     */
    protected void deleteAllRequests() {
        int size = getModel().getRequestBook().getRequestList().size();
        for (int i = 0; i < size; i++) {
            executeCommand(DeleteRequestCommand.COMMAND_WORD + " " + DeleteRequestCommand.COMMAND_OPTION + " " + 1);
        }
        assertEquals(0, getModel().getRequestBook().getRequestList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage getSpecialisation the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        //assertEquals(new HealthWorkerBook(expectedModel.getHealthWorkerBook()), testApp.readStorageAddressBook());
        assertRequestListMatching(getRequestListPanel(), expectedModel.getFilteredRequestList());
        assertHealthWorkerListMatching(getHealthWorkerListPanel(), expectedModel.getFilteredHealthWorkerList());
    }

    /**
     * Calls {@code RequestListPanelHandle}, {@code InfoPanel}, {@code HealthWorkerListPanelHandle} and
     * {@code StatusBarFooterHandle} to remember their current states.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getInfoPanel().rememberContent();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getRequestListPanel().rememberSelectedRequestCard();
        getHealthWorkerListPanel().rememberSelectedHealthWorkerCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and there is no new load event.
     * @see InfoPanelHandle#isLoaded()
     */
    protected void assertSelectedCardDeselected() {
        assertFalse(!getInfoPanel().isLoaded());
        assertFalse(getRequestListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the info panel's content is updated to display the details of a request from the request list panelt
     * at {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see InfoPanelHandle#isLoaded()
     * @see RequestListPanelHandle#isSelectedRequestCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getRequestListPanel().navigateToCard(getRequestListPanel().getSelectedCardIndex());
        assertTrue(getInfoPanel().isLoaded());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getRequestListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the info panel's content and the selected card in the request list panel remains unchanged.
     * @see InfoPanelHandle#isLoadedContentChanged()
     * @see RequestListPanelHandle#isSelectedRequestCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        //assertFalse(getInfoPanel().isLoadedContentChanged());
        assertFalse(getRequestListPanel().isSelectedRequestCardChanged());
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
     * Asserts that the status bar sync and saved statuses are changed.
     */
    protected void assertStatusBarChanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertTrue(handle.isSaveLocationChanged());
        assertTrue(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM YYYY, hh:mm a");
        String timestamp = formatter.format(new Date(clockRule.getInjectedClock().millis()));
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
        assertRequestListMatching(getRequestListPanel(), getModel().getFilteredRequestList());
        assertEquals(InfoPanel.DEFAULT_PAGE, getInfoPanel().getLoadedUrl());
        assertHealthWorkerListMatching(getHealthWorkerListPanel(), getModel().getFilteredHealthWorkerList());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
