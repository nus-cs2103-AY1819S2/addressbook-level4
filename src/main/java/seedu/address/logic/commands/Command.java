package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public interface Command {
    String MESSAGE_EXPECTED_MGT_MODEL =
            "Expected ManagementModel but received QuizModel instead.";
    String MESSAGE_EXPECTED_QUIZ_MODEL =
            "Expected QuizModel but received ManagementModel instead.";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult execute(Model model, CommandHistory history) throws CommandException;
}
