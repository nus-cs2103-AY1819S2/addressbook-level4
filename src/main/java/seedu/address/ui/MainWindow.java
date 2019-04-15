package seedu.address.ui;

import static seedu.address.commons.core.Messages.MESSAGE_LESSON_COMMANDS;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private LogicManager.Mode mode;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private MainPanel mainPanel;
    private QuizResultPanel quizResultPanel;
    private HelpWindow helpWindow;
    private LessonListPanel lessonListPanel;
    private FlashcardPanel flashcardPanel;
    private StatusBarFooter statusBarFooter;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private SplitPane splitPane;

    @FXML
    private VBox sidePanel;

    @FXML
    private StackPane lessonListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane mainPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Set default mode
        mode = LogicManager.Mode.MANAGEMENT;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setTheme();

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
        lessonListPanel = new LessonListPanel(logic.getLessons());
        lessonListPanelPlaceholder.getChildren().add(lessonListPanel.getRoot());

        splitPane.lookupAll(".split-pane-divider")
                .forEach(div -> div.setMouseTransparent(true));

        mainPanel = new MainPanel();
        mainPanelPlaceholder.getChildren().add(mainPanel.getRoot());

        flashcardPanel = new FlashcardPanel();
        mainPanelPlaceholder.getChildren().add(flashcardPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplay.setFeedbackToUser(MESSAGE_LESSON_COMMANDS);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
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
     * Changes the Ui setting between management and quiz mode
     */
    private void handleModeSwitching() {
        if (mode.equals(LogicManager.Mode.MANAGEMENT)) {
            splitPane.setDividerPosition(0, 0.1);
            sidePanel.setMinWidth(340);
            sidePanel.setPrefWidth(340);

            mainPanelPlaceholder.getChildren().clear();
            mainPanelPlaceholder.getChildren().add(flashcardPanel.getRoot());
            resultDisplay.setFeedbackToUser(MESSAGE_LESSON_COMMANDS);
            statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
        } else {
            splitPane.setDividerPosition(0, 0);
            sidePanel.setMinWidth(0);
            sidePanel.setPrefWidth(0);
            mainPanelPlaceholder.getChildren().clear();
            mainPanelPlaceholder.getChildren().add(mainPanel.getRoot());
            statusbarPlaceholder.getChildren().clear();
        }
    }

    /**
     * Sets the display for quiz mode.
     */
    private void handleQuiz() {
        if (logic.getQuizCardList() != null) {
            quizResultPanel = new QuizResultPanel();
            mainPanelPlaceholder.getChildren().clear();
            mainPanelPlaceholder.getChildren().add(quizResultPanel.getRoot());
            quizResultPanel.setFeedbackToUser(logic.getQuizCardList());
        } else {
            mainPanel.setFeedbackToUser(logic.getCurrentQuizCard(), logic.getTotalCorrectAndTotalAttempts());
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
     * Sets stylesheet according to the current theme from {@link UserPrefs} object.
     */
    private void setTheme() {
        primaryStage.getScene().getStylesheets().remove(1);
        primaryStage.getScene().getStylesheets().add(logic.getTheme());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            resultDisplay.setStyleToDefault();
            LogicManager.Mode currentMode = logic.getMode();
            setTheme();

            if (!currentMode.equals(mode)) {
                this.mode = currentMode;
                handleModeSwitching();
            }

            if (commandResult.isShowQuiz()) {
                handleQuiz();
            }

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (logic.getOpenedLesson() != null) {
                flashcardPanel.updateCardList(logic.getOpenedLesson());
                lessonListPanel.updateLessonList(logic.getOpenedLesson());
            } else {
                flashcardPanel.hideCardList();
                lessonListPanel.updateLessonList(logic.getLessons());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            resultDisplay.setStyleToIndicateCommandFailure();
            throw e;
        }
    }
}
