package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.quiz.QuizModel;

/**
 * Display the total attempts, streak and progress of the current session
 */
public class QuizStatusCommand extends QuizCommand {
    public static final String COMMAND_WORD = "\\status";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Shows the total attempts, streak and progress for the current session.\n"
        + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_RESULT = "Total attempts: %1$s\n"
        + "Total correct answer: %2$s\n"
        + "Current progress: %3$s";

    @Override
    public CommandResult execute(QuizModel model, CommandHistory history) throws CommandException {
        return new CommandResult(String.format(MESSAGE_RESULT, model.getQuizTotalAttempts(),
            model.getQuizTotalCorrectQuestions(), model.getCurrentProgress()));
    }
}
