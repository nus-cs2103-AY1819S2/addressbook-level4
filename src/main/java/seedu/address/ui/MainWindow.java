package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import seedu.address.model.patient.Teeth;
import seedu.address.model.patient.Patient;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    /**
     * The patient of records to be shown
     */
    private static Patient recordPatient = null;

    /**
     * Indicates if current mode is showing patient records
     */
    private static boolean goToMode = false;

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private RecordListPanel recordListPanel;
    private PersonListPanel personListPanel;
    private TaskListPanel taskListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatWindow statWindow;
    private TeethPanel teethPanel;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane teethPanelPlaceholder;

    @FXML
    private StackPane recordListPanelPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        statWindow = new StatWindow(new Stage(), this.logic);

        // Hidden panel by default.
        recordListPanelPlaceholder.setVisible(false);
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
        browserPanel = new BrowserPanel(logic.selectedPersonProperty());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        //teethPanel = new TeethPanel(logic.selectedPersonProperty());
        //teethPanelPlaceholder.getChildren().add(teethPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.selectedPersonProperty(),
                logic::setSelectedPerson);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath(), logic.getAddressBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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

    /**
     * Opens a stat window and closes the previous one if it's already opened
     */
    @FXML
    public void handleStat() {
        if (statWindow.isShowing()) {
            statWindow.close();
        }
        statWindow.populateData();
        statWindow.show();
    }

    /**
     * Opens the record panel and hides the patient list.
     */
    @FXML
    public void handleRecord() {
        populateRecords();
        personListPanelPlaceholder.setVisible(false);
        recordListPanelPlaceholder.setVisible(true);
        goToMode = true;
    }

    /**
     * Generates the record using the stored patient.
     */
    private void populateRecords() {
        if (MainWindow.getRecordPatient() != null) {
            recordListPanel = new RecordListPanel(logic.getFilteredRecordList());
            recordListPanelPlaceholder.getChildren().clear();
            recordListPanelPlaceholder.getChildren().add(recordListPanel.getRoot());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Returns records list to patient list.
     */
    @FXML
    private void handleBack() {
        backToPatientList();
    }

    /**
     * If at GoTo mode -> Goes back to patient list.
     * Else closes the application.
     */
    @FXML
    private void handleExit() {
        boolean confirmExit = true;
        if (!logic.checkNoCopy()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Copies will not be saved.\nConfirm exit?", ButtonType.YES, ButtonType.NO);
            alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
                confirmExit = false;
            }
        }
        if (confirmExit) {
            exit();
        }
    }

    /**
     * Close the application.
     */
    @FXML
    private void handleExit(boolean exitAnyway) {
        if (exitAnyway) {
            exit();
        }
    }

    /**
     * Returns to Patient list from Records list.
     */
    private void backToPatientList() {
        personListPanelPlaceholder.setVisible(true);
        recordListPanelPlaceholder.setVisible(false);
        MainWindow.setRecordPatient(null);
        goToMode = false;
    }

    /**
     * Exit the application
     */
    private void exit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
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

            if (commandResult.isShowStat()) {
                handleStat();
            }

            if (commandResult.isShowRecord()) {
                handleRecord();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isBack()) {
                handleBack();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Sets the patient who records are going to show.
     *
     * @param patient the patient who records will be shown.
     */
    public static void setRecordPatient(Patient patient) {
        MainWindow.recordPatient = patient;
    }

    public static Patient getRecordPatient() {
        return MainWindow.recordPatient;
    }

    public static boolean isGoToMode() {
        return goToMode;
    }
}
