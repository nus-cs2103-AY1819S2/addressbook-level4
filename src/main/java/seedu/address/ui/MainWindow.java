package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
import seedu.address.logic.parser.exceptions.InvalidCommandModeException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AppMode;

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
    //private BrowserPanel browserPanel;
    private PersonListPanel personListPanel;
    private ActivityDetailPanel activityDetailPanel;
    private PersonNotAttendingListPanel personNotInActivityListPanel;
    private ActivitiesAttendedByMemberPanel activitiesAttendedByMemberPanel;
    private ActivityListPanel activityListPanel;
    private MemberDetailPanel memberDetailPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane leftListPanelPlaceholder;
    @FXML
    private StackPane rightListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private Label modeLabel;

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
        memberDetailPanel = new MemberDetailPanel(logic.selectedPersonProperty(), logic);
        activityDetailPanel = new ActivityDetailPanel(logic.selectedActivityProperty(),
                logic.getAttendingOfSelectedActivity());
        browserPlaceholder.getChildren().add(memberDetailPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), logic.selectedPersonProperty(),
                logic::setSelectedPerson);
        activityListPanel = new ActivityListPanel(logic.getFilteredActivityList(), logic.selectedActivityProperty(),
                logic::setSelectedActivity);
        leftListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        personNotInActivityListPanel = new PersonNotAttendingListPanel(logic.getPersonNotInSelectedActivity());
        activitiesAttendedByMemberPanel = new ActivitiesAttendedByMemberPanel(logic.getActivitiesOfPerson());
        rightListPanelPlaceholder.getChildren().add(activitiesAttendedByMemberPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath(), logic.getAddressBook());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        setModeLabel(logic.getAddressBook().getCurrMode());
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
     * Change mode
     */
    @FXML
    private void handleModeHasChanged() {
        logic.callAllListFn();
        if (isModeChangeToMember()) {
            leftListPanelPlaceholder.getChildren().set(0, personListPanel.getRoot());
            browserPlaceholder.getChildren().set(0, memberDetailPanel.getRoot());
            rightListPanelPlaceholder.getChildren().set(0, activitiesAttendedByMemberPanel.getRoot());
            setModeLabel(AppMode.Modes.MEMBER);
        }
        if (isModeChangeToActivity()) {
            leftListPanelPlaceholder.getChildren().set(0, activityListPanel.getRoot());
            browserPlaceholder.getChildren().set(0, activityDetailPanel.getRoot());
            rightListPanelPlaceholder.getChildren().set(0, personNotInActivityListPanel.getRoot());
            setModeLabel(AppMode.Modes.ACTIVITY);
        }
    }

    @FXML
    private void setModeLabel(AppMode.Modes mode) {
        switch (mode) {
        case MEMBER:
            modeLabel.setText("Mode : MEMBER");
            modeLabel.getStyleClass().remove("labelMode-Activity");
            modeLabel.getStyleClass().add("labelMode-Member");
            break;
        case ACTIVITY:
            modeLabel.setText("Mode : ACTIVITY");
            modeLabel.getStyleClass().remove("labelMode-Member");
            modeLabel.getStyleClass().add("labelMode-Activity");
            break;
        default:
            break;
        }
    }

    private boolean isModeChangeToMember() {
        return logic.modeHasChange_isCurrModeMember();
    }

    private boolean isModeChangeToActivity() {
        return logic.modeHasChange_isCurrModeActivity();
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

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException,
            ParseException, InvalidCommandModeException {
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

            if (commandResult.isModeHasChanged()) {
                handleModeHasChanged();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (InvalidCommandModeException e) {
            logger.info("Invalid command mode: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
