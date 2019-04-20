package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;

/**
 * Force quits quiz while it is still not yet completed.
 */
public class QuizQuitCommand extends QuizCommand {
    public static final String COMMAND_WORD = "\\quit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Force quits this quiz, "
        + "only progress of attempted questions will be saved.\n"
        + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_SUCCESS = "You attempted %1$s question(s) and your progress has been saved.\n"
        + "Press Enter to exit quiz mode.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        QuizModel quizModel = requireQuizModel(model);

        List<List<Integer>> nonZeroAttemptsResult = quizModel.end();

        if (nonZeroAttemptsResult.size() != 0) {
            quizModel.updateUserProfile(nonZeroAttemptsResult);
        }
        quizModel.setResultDisplay(true);

        return new CommandResult(String.format(MESSAGE_SUCCESS, nonZeroAttemptsResult.size()), true, false, false);
    }
}
