package quickdocs;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.ui.ReminderListPanel;

/**
 * This class handles user interaction with the root layout
 */
public class RootLayoutController {

    private static int currentInputPointer = 0;
    private Logic logicManager;
    private ReminderListPanel reminderListPanel;

    @FXML
    private TextArea display;

    @FXML
    private TextField userInput;

    @FXML
    private TextArea inputFeedback;

    @FXML
    private StackPane reminderList;

    public void setLogicManager(Logic logicManager) {
        this.logicManager = logicManager;
    }

    /**
     * This method will pass the command into the parser whenever the user presses enter
     * @param event Event associated with the user pressing enter to confirm a command
     */
    @FXML
    public void enterInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            try {
                inputFeedback.setText("");
                CommandResult result = logicManager.execute(userInput.getText());
                display.appendText(">" + userInput.getText() + "\n");
                display.appendText(result.getFeedbackToUser());
                display.appendText("\n");
                userInput.setText("");
                display.selectPositionCaret(display.getText().length());
            } catch (Exception e) {
                inputFeedback.setText(e.getMessage());
            }
        }
    }

    /**
     * This method will check if the parameters entered into the command text field is valid
     * by calling the various checkers across the module
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
     * @return the reference to the display textArea for other modules
     */
    public TextArea getDisplay() {
        return this.display;
    }
}
