package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;

/**
 * Displays the total attempts, streak and progress of the current session.
 */
public class QuizStatusCommand extends QuizCommand {
    public static final String COMMAND_WORD = "\\status";
    public static final String MESSAGE_USAGE =
        COMMAND_WORD + ": Shows the total attempts, streak and progress for this quiz.\n"
        + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Total attempts: %1$s\n"
        + "Total correct answer: %2$s\n"
        + "Current progress: %3$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        QuizModel quizModel = requireQuizModel(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, quizModel.getQuizTotalAttempts(),
            quizModel.getQuizTotalCorrectQuestions(), quizModel.getCurrentProgress()));
    }
}
