package seedu.finance.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.finance.commons.core.GuiSettings;
import seedu.finance.commons.core.LogsCenter;
import seedu.finance.logic.Logic;
import seedu.finance.logic.commands.CommandResult;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private String theme = "Dark";

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private RecordListPanel recordListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private Scene currentScene;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane recordListPanelPlaceholder;

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
        browserPanel = new BrowserPanel(logic.selectedRecordProperty(), logic.getBudget());
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        recordListPanel = new RecordListPanel(logic.getFilteredRecordList(), logic.selectedRecordProperty(),
                logic::setSelectedRecord);
        recordListPanelPlaceholder.getChildren().add(recordListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getFinanceTrackerFilePath(),
                logic.getFinanceTracker());
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

    public RecordListPanel getRecordListPanel() {
        return recordListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.finance.logic.Logic#execute(String)
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

            if (commandResult.isSwitchTheme()) {
                handleSwitchTheme(commandResult.getThemeToChange());
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    //================== Changing Theme ==================//
    //@author Jackimaru96

    /**
     * Method to handle switching the Finance Tracker to specified theme
     * @param themeToSwitch the theme to switch to
     */
    private void handleSwitchTheme(String themeToSwitch) {
        if (this.theme == themeToSwitch) {
            CommandResult r = new CommandResult("Theme is currently already set to "
                    + themeToSwitch + ". Please choose another theme to switch to.");
            return;
        }

        switch (themeToSwitch) {
            case "Dark":
                setDarkTheme();
                return;

            case "Light":
                setLightTheme();
                return;

            case "Blue":
                setBlueTheme();
                return;

            case "Pink":
                setPinkTheme();
                return;
        }

    }

    /**
     * Sets the Finance Tracker to light theme
     */
    @FXML
    private void setLightTheme() {
        currentScene = primaryStage.getScene();
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/LightTheme.css").toExternalForm());
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/Extensions.css").toExternalForm());

        this.theme = "Light";
        CommandResult r = new CommandResult("Light Theme is set");
        resultDisplay.setFeedbackToUser(r.getFeedbackToUser());
    }

    /**
     * Sets the FinanceTracker to dark theme
     */
    @FXML
    private void setDarkTheme() {
        currentScene = primaryStage.getScene();
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/DarkTheme.css").toExternalForm());
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/Extensions.css").toExternalForm());

        this.theme = "Dark";
        CommandResult r = new CommandResult("Dark Theme is set");
        resultDisplay.setFeedbackToUser(r.getFeedbackToUser());
    }

    /**
     * Sets the FinanceTracker to blue theme
     */
    @FXML
    private void setBlueTheme() {
        currentScene = primaryStage.getScene();
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/BlueTheme.css").toExternalForm());
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/Extensions.css").toExternalForm());

        this.theme = "Blue";
        CommandResult r = new CommandResult("Blue Theme is set");
        resultDisplay.setFeedbackToUser(r.getFeedbackToUser());
    }

    /**
     * Sets the FinanceTracker to pink theme
     */
    @FXML
    private void setPinkTheme() {
        currentScene = primaryStage.getScene();
        currentScene.getStylesheets().clear();
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/PinkTheme.css").toExternalForm());
        currentScene.getStylesheets().add(getClass()
                .getResource("/view/Extensions.css").toExternalForm());

        this.theme = "Pink";
        CommandResult r = new CommandResult("Pink Theme is set");
        resultDisplay.setFeedbackToUser(r.getFeedbackToUser());
    }
}
