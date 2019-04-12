package quickdocs.ui;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import quickdocs.logic.Logic;
import quickdocs.logic.commands.AddDirectoryCommand;
import quickdocs.logic.commands.AddMedicineCommand;
import quickdocs.logic.commands.AlarmCommand;
import quickdocs.logic.commands.CommandResult;
import quickdocs.logic.commands.PurchaseMedicineCommand;
import quickdocs.logic.commands.SetPriceCommand;
import quickdocs.logic.commands.ViewStorageCommand;
import quickdocs.logic.parser.QuickDocsParser;

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

        suggestionOn = isDirectoryFormat(userInputField.getText());
        switch (event.getCode()) {
        case PAGE_UP:
            event.consume();
            if (!suggestionOn) {
                break;
            }
            suggestions = getDirectorySuggestions(userInputField.getText());
            if (isMedicineAllowed) {
                suggestions.addAll(getMedicineSuggestions(userInputField.getText()));
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
            suggestions = getDirectorySuggestions(userInputField.getText());
            if (isMedicineAllowed) {
                suggestions.addAll(getMedicineSuggestions(userInputField.getText()));
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
     * To judge whether suggestion mode should be turned on.
     *
     * @param rawArgs The user input
     * @return whether suggestion mode should be turned on
     */
    private boolean isDirectoryFormat(String rawArgs) {
        isMedicineAllowed = false;
        Matcher matcher = QuickDocsParser.BASIC_COMMAND_FORMAT.matcher(rawArgs);
        if (!matcher.matches()) {
            return false;
        }
        String commandWord = matcher.group("commandWord").trim();
        String arguments = matcher.group("arguments").trim();
        if (!arguments.contains("\\") || arguments.contains(" ")) {
            return false;
        }
        switch (commandWord) {
        case PurchaseMedicineCommand.COMMAND_WORD:
        case PurchaseMedicineCommand.COMMAND_ALIAS:
        case SetPriceCommand.COMMAND_WORD:
        case SetPriceCommand.COMMAND_ALIAS:
        case AlarmCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_ALIAS:
            isMedicineAllowed = true;
            return true;
        case AddMedicineCommand.COMMAND_WORD:
        case AddMedicineCommand.COMMAND_ALIAS:
        case AddDirectoryCommand.COMMAND_WORD:
        case AddDirectoryCommand.COMMAND_ALIAS:
            return true;
        default:
            return false;
        }
    }

    private ArrayList<String> getDirectorySuggestions(String rawPath) {
        Matcher matcher = QuickDocsParser.BASIC_COMMAND_FORMAT.matcher(rawPath);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return logicManager.getDirectorySuggestions(matcher.group("arguments"));
    }

    private ArrayList<String> getMedicineSuggestions(String rawPath) {
        Matcher matcher = QuickDocsParser.BASIC_COMMAND_FORMAT.matcher(rawPath);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return logicManager.getMedicineSuggestions(matcher.group("arguments"));
    }

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
                logicManager.selectedReminderProperty(), logicManager::setSelectedReminder);
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
