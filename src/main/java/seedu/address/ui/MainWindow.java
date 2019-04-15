package seedu.address.ui;

import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_FLASHCARD_SUCCESS;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
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
import seedu.address.model.flashcard.Flashcard;

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
    private FlashcardListPanel flashcardListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private StackPane cardViewPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane flashcardListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane toolbar;

    @FXML
    private ImageView closeButton;

    @FXML
    private ImageView helpButton;

    @FXML
    private ImageView minimizeButton;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setupToolbar();

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    private void setupToolbar() {
        toolbar.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        toolbar.setOnMouseDragged(mouseEvent -> {
            getPrimaryStage().setX(mouseEvent.getScreenX() - xOffset);
            getPrimaryStage().setY(mouseEvent.getScreenY() - yOffset);
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        // setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
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
        CardViewPanel cardViewPanel = new CardViewPanel(logic.selectedFlashcardProperty(), logic.quizModeProperty());
        cardViewPlaceholder.getChildren().add(cardViewPanel.getRoot());

        flashcardListPanel = new FlashcardListPanel(logic.getFilteredFlashcardList(), logic.selectedFlashcardProperty(),
            this::setSelectedFlashcard);
        flashcardListPanelPlaceholder.getChildren().add(flashcardListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getCardCollectionFilePath(),
            logic.getCardCollection());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void setSelectedFlashcard(Flashcard flashcard) {
        ObservableList<Flashcard> filteredFlashcardList = logic.getFilteredFlashcardList();
        int index = filteredFlashcardList.indexOf(flashcard) + 1;
        String messageDisplay = String.format(MESSAGE_SELECT_FLASHCARD_SUCCESS, index,
            flashcard.getSuccessRate(), flashcard.getQuizSrsStatus());
        resultDisplay.setFeedbackToUser(messageDisplay);
        logic.setSelectedFlashcard(flashcard);
    }

    /**
     * Sets up the listeners needed.
     */
    void setupListeners() {
        logic.quizModeProperty().addListener(((observableValue, oldValue, newValue) -> onQuizModeChanged(newValue)));
        closeButton.setOnMouseClicked(mouseEvent -> handleExit());
        helpButton.setOnMouseClicked(mouseEvent -> handleHelp());
        minimizeButton.setOnMouseClicked(mouseEvent -> primaryStage.setIconified(true));
    }

    /**
     * Prepares view when quiz mode changes.
     *
     * @param newQuizMode the changed quiz mode
     */
    private void onQuizModeChanged(Integer newQuizMode) {
        if (newQuizMode == 0) {
            endQuizMode();
        } else {
            startQuizMode();
        }
    }

    /**
     * Starts a quiz mode.
     */
    private void startQuizMode() {
        flashcardListPanelPlaceholder.getChildren().clear();
        QuizPanel quizPanel = new QuizPanel(logic.getQuizFlashcards(),
            logic.quizGoodProperty(), logic.quizBadProperty(), logic.isQuizSrsProperty());
        flashcardListPanelPlaceholder.getChildren().add(quizPanel.getRoot());
    }

    private void endQuizMode() {
        flashcardListPanelPlaceholder.getChildren().clear();
        flashcardListPanelPlaceholder.getChildren().add(flashcardListPanel.getRoot());
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

    public FlashcardListPanel getFlashcardListPanel() {
        return flashcardListPanel;
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

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
