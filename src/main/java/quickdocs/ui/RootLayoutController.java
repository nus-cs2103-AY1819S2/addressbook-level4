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

    private static int currentInputPointer = 0;
    private boolean suggestionOn = false;
    private boolean isMedicineAllowed = false;
    private ArrayList<String> suggestions;
    private ArrayList<String> medicineSuggestions;
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

    private HelpWindow helpWindow;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setLogicManager(Logic logicManager) {
        this.logicManager = logicManager;
        this.history = this.logicManager.getHistory();
        this.historySnapshot = new ListElementPointer(history);
        this.helpWindow = new HelpWindow();
    }

    /**
     * This method will pass the command into the parser whenever the user presses enter
     *
     * @param event Event associated with the user pressing enter to confirm a command
     */
    @FXML
    public void enterInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                inputFeedback.setText("");
                CommandResult result = logicManager.execute(userInput.getText());

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

                display.appendText(">>> " + userInput.getText() + "\n");
                display.appendText("---------------------------------------------------------------------------\n");
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
            return;
        }
        suggestionOn = isDirectoryFormat(userInput.getText());
        switch (event.getCode()) {
        case PAGE_UP:
            event.consume();
            if (!suggestionOn) {
                break;
            }
            suggestions = getDirectorySuggestions(userInput.getText());
            if (isMedicineAllowed) {
                suggestions.addAll(getMedicineSuggestions(userInput.getText()));
                suggestions.sort(Comparator.comparing(String::toLowerCase));
            }
            int pointer = getIndex(userInput.getText(), suggestions);
            if (pointer > 0) {
                if (pointer % 2 == 0) {
                    userInput.setText(processPath(userInput.getText(), suggestions.get(pointer / 2 - 1)));
                } else {
                    userInput.setText(processPath(userInput.getText(), suggestions.get(pointer / 2)));
                }
            }
            userInput.positionCaret(userInput.getText().length());
            break;
        case PAGE_DOWN:
            event.consume();
            if (!suggestionOn) {
                break;
            }
            suggestions = getDirectorySuggestions(userInput.getText());
            if (isMedicineAllowed) {
                suggestions.addAll(getMedicineSuggestions(userInput.getText()));
                suggestions.sort(Comparator.comparing(String::toLowerCase));
            }
            pointer = getIndex(userInput.getText(), suggestions);
            if (pointer < 2 * (suggestions.size() - 1)) {
                if (pointer % 2 == 0) {
                    userInput.setText(processPath(userInput.getText(), suggestions.get(pointer / 2 + 1)));
                } else {
                    userInput.setText(processPath(userInput.getText(), suggestions.get((pointer + 1) / 2)));
                }
            }
            userInput.positionCaret(userInput.getText().length());
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
            //inputFeedback.setText("space entered");
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
     *
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
     *
     * @param checkConsultation
     */
    private void endConsultation(String checkConsultation) {
        if ((checkConsultation.contains("Consultation")
                && (checkConsultation.contains("ended") || checkConsultation.contains("aborted")))) {
            currentSession.setText("");
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
