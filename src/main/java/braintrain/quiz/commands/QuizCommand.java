package braintrain.quiz.commands;

import braintrain.logic.CommandHistory;
import braintrain.logic.commands.CommandResult;
import braintrain.logic.commands.exceptions.CommandException;
import braintrain.quiz.QuizModel;

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
