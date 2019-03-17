package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.quizmodel.QuizModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class QuizCommand {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code QuizModel} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(QuizModel model, CommandHistory history) throws CommandException;
}
