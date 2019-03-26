package quickdocs;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.ui.ListElementPointer;
import seedu.address.ui.ReminderListPanel;

/**
 * This class handles user interaction with the root layout
 */
public class RootLayoutController {

    private static int currentInputPointer = 0;
    private Logic logicManager;
    private ReminderListPanel reminderListPanel;
    private List<String> history;
    private ListElementPointer historySnapshot;

    @FXML
    private Stage primaryStage;

    @FXML
    private TextArea display;

    @FXML
    private TextField userInput;

    @FXML
    private TextArea inputFeedback;

    @FXML
    private StackPane reminderList;

    @FXML
    private Label currentSession;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLogicManager(Logic logicManager) {
        this.logicManager = logicManager;
        this.history = this.logicManager.getHistory();
        this.historySnapshot = new ListElementPointer(history);
    }

    /**
     * This method will pass the command into the parser whenever the user presses enter
     *
     * @param event Event associated with the user pressing enter to confirm a command
     */
    @FXML
    public void enterInput(KeyEvent event) {

        switch (event.getCode()) {
        case UP:
            event.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            event.consume();
            navigateToNextInput();
            break;
        case ENTER:
            try {
                inputFeedback.setText("");
                CommandResult result = logicManager.execute(userInput.getText());

                // handling exit
                if (result.isExit()) {
                    primaryStage.close();
                }

                // consultation session handling
                indicateConsultation(result.getFeedbackToUser());
                endConsultation(result.getFeedbackToUser());

                display.appendText(">" + userInput.getText() + "\n");
                display.appendText(result.getFeedbackToUser());
                display.appendText("\n");


                // move display to the end to show result of last entered command
                display.selectPositionCaret(display.getText().length());

                // history handling
                initHistory();
                historySnapshot.next();
                userInput.setText("");

                fillReminderList();
            } catch (Exception e) {
                inputFeedback.setText(e.getMessage());
            }
            break;
        default:
            break;
        }
    }

    /**
     * This method will check if the parameters entered into the command text field is valid
     * by calling the various checkers across the module
     *
     * @param event Event associated with the user pressing space bar between parameters
     */
    @FXML
    public void checkInput(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            inputFeedback.setText("space entered");
        }
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillReminderList() {
        reminderListPanel = new ReminderListPanel(logicManager.getFilteredReminderList(),
                logicManager.selectedReminderProperty(), logicManager::setSelectedReminder);
        reminderList.getChildren().add(reminderListPanel.getRoot());
    }

    /**
     * This method allow other modules to tap on the display to show the output
     * of different commands
     *
     * @return the reference to the display textArea for other modules
     */
    public TextArea getDisplay() {
        return this.display;
    }


    // history handling
    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = new ListElementPointer(history);
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }


    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        userInput.setText(text);
        userInput.positionCaret(userInput.getText().length());
    }

    /**
     * First, check whether the command result is from the consultation command
     * if it is, make label display the ongoing session
     * @param checkConsultation can be any command result from the various commands
     */
    private void indicateConsultation(String checkConsultation) {
        if (checkConsultation.contains("Consultation")
                && checkConsultation.contains("started")) {
            int colonPos = checkConsultation.indexOf(":");
            String nric = checkConsultation.substring(colonPos + 2, colonPos + 11);
            currentSession.setText("Consultation ongoing for: " + nric);
        }
    }

    /**
     * If command result indicates that consultation has ended
     * make label disappear
     * @param checkConsultation
     */
    private void endConsultation(String checkConsultation) {
        if (checkConsultation.contains("Consultation")
                && checkConsultation.contains("ended")) {
            currentSession.setText("");
        }
    }
}
