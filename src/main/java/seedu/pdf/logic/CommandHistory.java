package seedu.pdf.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pdf.logic.commands.DecryptCommand;
import seedu.pdf.logic.commands.EncryptCommand;

/**
 * Stores the history of commands executed.
 */
public class CommandHistory {
    private final ObservableList<String> userInputHistory = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableUserInputHistory =
            FXCollections.unmodifiableObservableList(userInputHistory);

    public CommandHistory() {}

    public CommandHistory(CommandHistory commandHistory) {
        userInputHistory.addAll(commandHistory.userInputHistory);
    }

    /**
     * Appends {@code userInput} to the list of user input entered.
     */
    public void add(String userInput) {
        requireNonNull(userInput);
        if (!userInput.contains(EncryptCommand.COMMAND_WORD) && !userInput.contains(DecryptCommand.COMMAND_WORD)) {
            userInputHistory.add(userInput);
        }
    }

    /**
     * Returns an unmodifiable view of {@code userInputHistory}.
     */
    public ObservableList<String> getHistory() {
        return unmodifiableUserInputHistory;
    }

    /**
     * Returns the last command entered {@code userInputHistory}.
     */
    public String getLastCommand() {
        return unmodifiableUserInputHistory.get(unmodifiableUserInputHistory.size());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CommandHistory)) {
            return false;
        }

        // state check
        CommandHistory other = (CommandHistory) obj;
        return userInputHistory.equals(other.userInputHistory);
    }

    @Override
    public int hashCode() {
        return userInputHistory.hashCode();
    }
}
