package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.predicate.UniqueFilterList;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainJobWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private String currScene;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel allApplicantsListPanel;
    private PersonListPanel kivListPanel;
    private PersonListPanel interviewedListPanel;
    private PersonListPanel selectedListPanel;
    private PersonListPanel allListPanel;
    private JobListPanel jobsListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane allApplicantsPlaceholder;

    @FXML
    private StackPane kivPlaceholder;

    @FXML
    private StackPane interviewedPlaceholder;

    @FXML
    private StackPane selectedPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private FlowPane allFilter;

    @FXML
    private FlowPane kivFilter;

    @FXML
    private FlowPane interviewFilter;

    @FXML
    private FlowPane shortlistFilter;

    @FXML
    private StackPane allPlaceholder;

    @FXML
    private StackPane jobsPlaceholder;


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
    private void fillInnerParts() {

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath(), logic.getAddressBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

    }

    /**
     * Fills up all the placeholders of this window when in display all jobs and applicants scene.
     */

    void fillAllJobsParts() {

        try {
            switchToAllJobsScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        allListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.selectedPersonProperty(),
                logic::setSelectedPerson);
        allPlaceholder.getChildren().add(allListPanel.getRoot());

        jobsListPanel = new JobListPanel(logic.getAllJobs(), logic.selectedJobProperty(),
                logic::setSelectedJob);
        jobsPlaceholder.getChildren().add(jobsListPanel.getRoot());

        fillInnerParts();
    }

    /**
     * Change scene to display all jobs and applicants
     */

    private void switchToAllJobsScene() throws IOException {
        currScene = "allJobs";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/displayAllScene.fxml"));
        loader.setController(this);
        primaryStage.setScene(loader.load());
    }

    /**
     * Change scene to display a specific job and its four lists
     */

    private void switchToDisplayJobScene() throws IOException {
        currScene = "displayJobs";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/displayJobScene.fxml"));
        loader.setController(this);
        primaryStage.setScene(loader.load());
    }

    /**
     * Refreshes the jobPersonListPanel
     */
    private void fillDisplayJobParts() {

        try {
            switchToDisplayJobScene();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        allApplicantsListPanel = new PersonListPanel(logic.getJobsList(0), logic.selectedPersonProperty(),
                logic::setSelectedAll);
        allApplicantsPlaceholder.getChildren().add(allApplicantsListPanel.getRoot());

        kivListPanel = new PersonListPanel(logic.getJobsList(1), logic.selectedPersonProperty(),
                logic::setSelectedKiv);
        kivPlaceholder.getChildren().add(kivListPanel.getRoot());

        interviewedListPanel = new PersonListPanel(logic.getJobsList(2), logic.selectedPersonProperty(),
                logic::setSelectedInterviewed);
        interviewedPlaceholder.getChildren().add(interviewedListPanel.getRoot());

        selectedListPanel = new PersonListPanel(logic.getJobsList(3), logic.selectedPersonProperty(),
                logic::setSelectedSelected);
        selectedPlaceholder.getChildren().add(selectedListPanel.getRoot());

        fillInnerParts();
    }

    /**
     * stores and updates the filtered parameters for each list
     */

    private void updateFilterTags(JobListName listName, UniqueFilterList list) {
        switch (listName) {
            case APPLICANT:
                allFilter.getChildren().clear();
                list.forEach(filter -> allFilter.getChildren().add(new Label(filter.getFilterName())));
                break;
            case KIV:
                kivFilter.getChildren().clear();
                list.forEach(filter -> kivFilter.getChildren().add(new Label(filter.getFilterName())));
                break;
            case INTERVIEW:
                interviewFilter.getChildren().clear();
                list.forEach(filter -> interviewFilter.getChildren().add(new Label(filter.getFilterName())));
                break;
            case SHORTLIST:
                shortlistFilter.getChildren().clear();
                list.forEach(filter -> shortlistFilter.getChildren().add(new Label(filter.getFilterName())));
                break;
            default:
                break;
        }
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

            if (commandResult.isSuccessfulAnalytics()) {
                AnalyticsWindow analytics = new AnalyticsWindow();
                analytics.show(commandResult.getAnalytics());
            }

            if (commandResult.isSuccessfulFilter()) {
                updateFilterTags(commandResult.getJobListName(), commandResult.getFilterList());
            }

            if (commandResult.isSuccessfulDisplayJob()) {
                fillDisplayJobParts();
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            }

            if (commandResult.isSuccessfulInterviews()) {
                InterviewsWindow interviews = new InterviewsWindow();
                interviews.show(commandResult.getInterviews());
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isList()) {
                fillAllJobsParts();
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
