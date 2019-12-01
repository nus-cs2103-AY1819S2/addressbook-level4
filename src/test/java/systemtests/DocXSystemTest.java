package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

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
import guitests.guihandles.DoctorListPanelHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.doctor.ListDoctorCommand;
import seedu.address.logic.commands.doctor.SelectDoctorCommand;
import seedu.address.logic.commands.patient.ListPatientCommand;
import seedu.address.logic.commands.patient.SearchPatientCommand;
import seedu.address.logic.commands.patient.SelectPatientCommand;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;
import seedu.address.testutil.TypicalDoctors;
import seedu.address.testutil.TypicalPatients;
import seedu.address.ui.CommandBox;
import seedu.address.ui.patient.PatientInfoPanel;

//import seedu.address.logic.commands.doctor.DeleteDoctorCommand;

/**
 * A system test class for DocX, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class DocXSystemTest {
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
    protected DocX getInitialData() {
        DocX combined = new DocX();
        List<Patient> patients = TypicalPatients.getTypicalPatients();
        for (Patient p : patients) {
            combined.addPatient(p);
        }
        List<Doctor> doctors = TypicalDoctors.getTypicalDoctors();
        for (Doctor d : doctors) {
            combined.addDoctor(d);
        }
        return combined;
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

    public PatientListPanelHandle getPatientListPanel() {
        return mainWindowHandle.getPatientListPanel();
    }

    public DoctorListPanelHandle getDoctorListPanel() {
        return mainWindowHandle.getDoctorListPanel();
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
     * Displays all patients in the address book.
     */
    protected void showAllPatients() {
        executeCommand(ListPatientCommand.COMMAND_WORD);
        assertEquals(getModel().getDocX().getPatientList().size(), getModel().getFilteredPatientList().size());
    }

    /**
     * Displays all patients with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showPatientsWithName(String keyword) {
        executeCommand(SearchPatientCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredPatientList().size()
                < getModel().getDocX().getPatientList().size());
    }

    /**
     * Selects the patient at {@code index} of the displayed list.
     */
    protected void selectPatient(Index index) {
        executeCommand(SelectPatientCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getPatientListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all patients in the address book.
     */
    protected void deleteAllPatients() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getDocX().getPatientList().size());
    }

    /**
     * Displays all doctors in the address book.
     */
    protected void showAllDoctors() {
        executeCommand(ListDoctorCommand.COMMAND_WORD);
        assertEquals(getModel().getDocX().getDoctorList().size(), getModel().getFilteredDoctorList().size());
    }

    /**
     * Displays all doctors with any parts of their names matching {@code keyword} (case-insensitive).
     */
    protected void showDoctorsWithName(String keyword) {
        executeCommand(ListDoctorCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredDoctorList().size()
                < getModel().getDocX().getDoctorList().size());
    }

    /**
     * Selects the doctor at {@code index} of the displayed list.
     */
    protected void selectDoctor(Index index) {
        executeCommand(SelectDoctorCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getDoctorListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all doctors in the address book.
     */
    protected void deleteAllDoctors() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getDocX().getDoctorList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same patient objects as {@code expectedModel}
     * and the patient list panel displays the patients in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new DocX(expectedModel.getDocX()), testApp.readStorageDocX());
        assertListMatching(getPatientListPanel(), expectedModel.getFilteredPatientList());
    }

    /**
     * Calls {@code BrowserPanelHandle}, {@code PatientListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPatientListPanel().rememberSelectedPatientCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(PatientInfoPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
        assertFalse(getPatientListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the patient in the patient list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PatientListPanelHandle#isSelectedPatientCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getPatientListPanel().navigateToCard(getPatientListPanel().getSelectedCardIndex());
        String selectedCardName = getPatientListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPatientListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url is changed to display the details of the patient in the patient list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PatientListPanelHandle#isSelectedPatientCardChanged()
     */
    protected void assertSelectedCardChanged_doc(Index expectedSelectedCardIndex) {
        getDoctorListPanel().navigateToCard(getDoctorListPanel().getSelectedCardIndex());
        String selectedCardName = getDoctorListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getDoctorListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the patient list panel remain unchanged.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PatientListPanelHandle#isSelectedPatientCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPatientListPanel().isSelectedPatientCardChanged());
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
        assertListMatching(getPatientListPanel(), getModel().getFilteredPatientList());
        assertEquals(PatientInfoPanel.DEFAULT_PAGE, getBrowserPanel().getLoadedUrl());
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
