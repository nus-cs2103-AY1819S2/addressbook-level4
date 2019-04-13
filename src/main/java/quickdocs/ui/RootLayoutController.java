package quickdocs.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import quickdocs.logic.Logic;
import quickdocs.logic.commands.CommandResult;

/**
 * This class handles user interaction with the root layout
 */
public class RootLayoutController {

    private boolean suggestionOn = false;
    private boolean isMedicineAllowed = false;
    private ArrayList<String> suggestions;
    private Logic logicManager;
    private ReminderListPanel reminderListPanel;
    private List<String> history;
    private ListElementPointer historySnapshot;

    @FXML
    private Stage primaryStage;

    @FXML
    private TextArea resultDisplay;

    @FXML
    private TextField userInputField;

    @FXML
    private TextArea inputFeedbackArea;

    @FXML
    private StackPane reminderList;

    @FXML
    private Label currentSessionLabel;

    private HelpWindow helpWindow;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Initialize the root layout controller with the logic manager, history and help window
     * so they can be used when UI is interacted with
     *
     * @param logicManager initialised at MainApp
     */
    public void initialiseRootLayout(Logic logicManager) {
        this.logicManager = logicManager;
        this.history = this.logicManager.getHistory();
        this.historySnapshot = new ListElementPointer(history);
        this.helpWindow = new HelpWindow();
    }

    public TextArea getDisplay() {
        return resultDisplay;
    }

    /**
     * Captures the user's entered command (when the user presses enter) and passes it to the logic
     * logic will handle the parsing and execution and returns the result
     * which will then be displayed on the resultDisplay control in the ui
     *
     * @param event Event associated with the user pressing enter to confirm a command
     */
    @FXML
    public void enterInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                inputFeedbackArea.setText("");
                CommandResult result = logicManager.execute(userInputField.getText());

                // handling exit
                if (result.isExit()) {
                    //primaryStage.close();
                    handleExit();
                }

                // handling help
                if (result.isShowHelp()) {
                    handleHelp();
                }

                // consultation session handling
                indicateConsultation(result.getFeedbackToUser());
                endConsultation(result.getFeedbackToUser());

                resultDisplay.appendText(">>> " + userInputField.getText() + "\n");
                resultDisplay.appendText(
                        "---------------------------------------------------------------------------\n");
                resultDisplay.appendText(result.getFeedbackToUser());
                resultDisplay.appendText("\n");

                // move resultDisplay to the end to show result of last entered command
                resultDisplay.selectPositionCaret(resultDisplay.getText().length());

                // history handling
                initHistory();
                historySnapshot.next();
                userInputField.setText("");

                fillReminderList();
            } catch (NumberFormatException nfe) {
                inputFeedbackArea.setText("Index entered is beyond valid range");
            } catch (Exception e) {
                inputFeedbackArea.setText(e.getMessage());
            }
            return;
        }

        /**
         * The following code handles both the command history and the suggestions of medicine filepath
         *
         * previously entered commands are navigable using the up and down directional keys
         * suggestions are navigable using the Page-Up and Page-Down keys
         */
        suggestionOn = logicManager.isDirectoryFormat(userInputField.getText());
        if (suggestionOn) {
            isMedicineAllowed = logicManager.isMedicineAllowed(userInputField.getText());
        }

        switch (event.getCode()) {
        case PAGE_UP:
            event.consume();
            if (!suggestionOn) {
                break;
            }

            suggestions = logicManager.getDirectorySuggestions(userInputField.getText());
            if (isMedicineAllowed) {
                suggestions.addAll(logicManager.getMedicineSuggestions(userInputField.getText()));
                suggestions.sort(Comparator.comparing(String::toLowerCase));
            }
            int pointer = getIndex(userInputField.getText(), suggestions);
            if (pointer > 0) {
                if (pointer % 2 == 0) {
                    userInputField.setText(processPath(userInputField.getText(), suggestions.get(pointer / 2 - 1)));
                } else {
                    userInputField.setText(processPath(userInputField.getText(), suggestions.get(pointer / 2)));
                }
            }
            userInputField.positionCaret(userInputField.getText().length());
            break;
        case PAGE_DOWN:
            event.consume();
            if (!suggestionOn) {
                break;
            }
            suggestions = logicManager.getDirectorySuggestions(userInputField.getText());
            if (isMedicineAllowed) {
                suggestions.addAll(logicManager.getMedicineSuggestions(userInputField.getText()));
                suggestions.sort(Comparator.comparing(String::toLowerCase));
            }
            pointer = getIndex(userInputField.getText(), suggestions);
            if (pointer < 2 * (suggestions.size() - 1)) {
                if (pointer % 2 == 0) {
                    userInputField.setText(processPath(userInputField.getText(), suggestions.get(pointer / 2 + 1)));
                } else {
                    userInputField.setText(processPath(userInputField.getText(), suggestions.get((pointer + 1) / 2)));
                }
            }
            userInputField.positionCaret(userInputField.getText().length());
            break;
        case UP:
            event.consume();
            navigateToPreviousInput();
            break;
        case DOWN:
            event.consume();
            navigateToNextInput();
            break;
        default:
            break;
        }
    }

    /**
     * Get the position of current user input in the list of suggestions
     * @param input current user input, starting from the next character from the last \ character
     * @param suggestions list of suggestions
     * @return A even number x if input equals suggestions[x/2];
     *          an odd number, if input is larger than suggestions[(x-1)/2] but smaller than suggestions[(x+1)/2]
     */
    private int getIndex(String input, ArrayList<String> suggestions) {
        String currentName = input.substring(input.lastIndexOf("\\") + 1).trim();
        if (currentName.equals("")) {
            return -1;
        }
        int current = -1;
        Comparator<String> comparator = Comparator.comparing(String::toLowerCase);
        while (true) {
            if (current == (suggestions.size() - 1) * 2 + 1) {
                break;
            }
            if (comparator.compare(suggestions.get((current + 1) / 2), currentName) > 0) {
                break;
            }
            if (comparator.compare(suggestions.get((current + 1) / 2), currentName) == 0) {
                current++;
                break;
            }
            current += 2;
        }
        return current;
    }

    private String processPath(String input, String idealName) {
        String remain = input.substring(0, input.lastIndexOf("\\") + 1);
        return remain + idealName;
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
            //inputFeedbackArea.setText("space entered");
        }
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillReminderList() {
        reminderListPanel = new ReminderListPanel(logicManager.getFilteredReminderList(),
                logicManager.selectedReminderProperty(), logicManager::setSelectedReminder, this.getDisplay());
        reminderList.getChildren().add(reminderListPanel.getRoot());
    }

    public TextArea getResultDisplay() {
        return this.resultDisplay;
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
        userInputField.setText(text);
        userInputField.positionCaret(userInputField.getText().length());
    }

    /**
     * First, check whether the command result is from the consultation command
     * if it is, make label resultDisplay the ongoing session
     *
     * @param checkConsultation can be any command result from the various commands
     */
    private void indicateConsultation(String checkConsultation) {
        if (checkConsultation.contains("Consultation")
                && checkConsultation.contains("started")) {
            int colonPos = checkConsultation.indexOf(":");
            String nric = checkConsultation.substring(colonPos + 2, colonPos + 11);
            currentSessionLabel.setText("Consultation ongoing for: " + nric);
        }
    }

    /**
     * If command result indicates that consultation has ended
     * make label disappear
     *
     * @param checkConsultation string result of command executed
     */
    private void endConsultation(String checkConsultation) {
        if ((checkConsultation.contains("Consultation")
                && (checkConsultation.contains("ended") || checkConsultation.contains("aborted")))) {
            currentSessionLabel.setText("");
        }
    }

    public void handleExit() {
        primaryStage.close();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }
}
