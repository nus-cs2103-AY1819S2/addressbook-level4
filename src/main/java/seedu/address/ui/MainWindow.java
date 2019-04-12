package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PatientInfoPanel patientInfoPanel;
    private MedHistBrowserPanel medHistBrowserPanel;
    private DoctorBrowserPanel doctorBrowserPanel;
    private PrescriptionBrowserPanel prescriptionBrowserPanel;
    private PatientListPanel patientListPanel;
    private MedHistListPanel medHistListPanel;
    private AppointmentListPanel appointmentListPanel;
    private PrescriptionListPanel prescriptionListPanel;
    private DoctorListPanel doctorListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CommandResult.ShowBrowser whichBrowser;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane patientListPanelPlaceholder;

    @FXML
    private StackPane middleListPanelPlaceholder;

    @FXML
    private StackPane doctorListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();
        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        browserPanel = new BrowserPanel(logic.selectedPatientProperty());
        patientInfoPanel = new PatientInfoPanel(logic.selectedPatientProperty());
        medHistBrowserPanel = new MedHistBrowserPanel(logic.selectedMedHistProperty());
        prescriptionBrowserPanel = new PrescriptionBrowserPanel(logic.selectedPrescriptionProperty());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        patientListPanel = new PatientListPanel(logic.getFilteredPatientList(), logic.selectedPatientProperty(),
                logic::setSelectedPatient);
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());

        doctorListPanel = new DoctorListPanel(logic.getFilteredDoctorList(), logic.selectedDoctorProperty(),
                logic::setSelectedDoctor);
        doctorListPanelPlaceholder.getChildren().add(doctorListPanel.getRoot());
        doctorBrowserPanel = new DoctorBrowserPanel(logic.selectedDoctorProperty());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getDocXFilePath(), logic.getDocX());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Show the full patient info
     */
    public void showPatientBrowser() {
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(patientInfoPanel.getRoot());
    }

    /**
     * Show the browser of doctor
     */
    public void showDoctorBrowser() {
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(doctorBrowserPanel.getRoot());
    }

    /**
     * Show the browser of medical history
     */
    public void showMedHistBrowser() {
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(medHistBrowserPanel.getRoot());
    }

    /**
     * Show the browser of prescription
     */
    public void showPrescriptionBrowser() {
        browserPlaceholder.getChildren().clear();
        browserPlaceholder.getChildren().add(prescriptionBrowserPanel.getRoot());
    }

    /**
     * Show the medical history panel
     */
    public void showMedHistPanel() {
        medHistListPanel = new MedHistListPanel(logic.getFilteredMedHistList(), logic.selectedMedHistProperty(),
                logic::setSelectedMedHist);
        middleListPanelPlaceholder.getChildren().clear();
        middleListPanelPlaceholder.getChildren().add(medHistListPanel.getRoot());
    }

    /**
     * Show the medical history panel
     */
    public void showPrescriptionPanel() {
        prescriptionListPanel = new PrescriptionListPanel(logic.getFilteredPrescriptionList(),
                logic.selectedPrescriptionProperty(),
                logic::setSelectedPrescription);
        middleListPanelPlaceholder.getChildren().clear();
        middleListPanelPlaceholder.getChildren().add(prescriptionListPanel.getRoot());
    }

    /**
     * Show the appointment panel
     */
    public void showAppointmentPanel() {
        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList(),
                logic.selectedAppointmentProperty(),
                logic::setSelectedAppointment);
        middleListPanelPlaceholder.getChildren().clear();
        middleListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }

    public MedHistListPanel getMedHistListPanel() {
        return medHistListPanel;
    }

    public DoctorListPanel getDoctorListPanel() {
        return doctorListPanel;
    }

    public PrescriptionListPanel getPrescriptionListPanel() {
        return prescriptionListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            switch (commandResult.getShowPanel()) {
            case MED_HIST_PANEL:
                showMedHistPanel();
                break;
            case APPOINTMENT_PANEL:
                showAppointmentPanel();
                break;
            case PRESC_PANEL:
                showPrescriptionPanel();
                break;

            default:
                break;
            }

            switch (commandResult.getShowBrowser()) {
            case MED_HIST_BROWSER:
                showMedHistBrowser();
                break;
            case PATIENT_BROWSER:
                showPatientBrowser();
                break;
            case DOCTOR_BROWSER:
                showDoctorBrowser();
                break;
            case PRESCRIPTION_BROWSER:
                showPrescriptionBrowser();
                break;
            default:
                break;
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
