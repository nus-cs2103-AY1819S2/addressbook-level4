package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.quiz.QuizCommand.requireQuizModel;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.Quiz;

/**
 * Force quits quiz session while it is still ongoing
 */
public class QuizQuitCommand implements Command {
    public static final String COMMAND_WORD = "\\quit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Force quits this session, "
        + "only progress of attempted questions will still be saved.\n"
        + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_COMPLETE = "You have tried %1$s question(s) in this quiz.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        QuizModel quizModel = requireQuizModel(model);

        List<List<Integer>> nonZeroAttemptsResult = nonZeroStreakResult(quizModel.end());

        if (nonZeroAttemptsResult.size() != 0) {
            quizModel.updateUserProfile(nonZeroAttemptsResult);
        }

        // set the display to blank for management mode display
        quizModel.setDisplayFormatter(null);

        return new CommandResult(String.format(MESSAGE_COMPLETE, nonZeroAttemptsResult.size()), true, false, false);
    }

    /**
     * Filters attempts with non zero streak from endResult.
     * @param endResult all attempts from quiz session {@link Quiz#end()}
     * @return attempts with streak more than 0
     */
    private List<List<Integer>> nonZeroStreakResult(List<List<Integer>> endResult) {
        List<List<Integer>> nonZeroStreakResult = new ArrayList<>();

        for (List<Integer> each: endResult) {
            if (each.get(2) != 0) {
                nonZeroStreakResult.add(each);
            }
        }

        return nonZeroStreakResult;
    }
}
