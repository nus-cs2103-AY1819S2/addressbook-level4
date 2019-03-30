package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.quiz.Quiz;

/**
 * Force quits quiz while it is still ongoing
 */
public class QuizQuitCommand extends QuizCommand {
    public static final String COMMAND_WORD = "\\quit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Force quits this quiz, "
        + "only progress of attempted questions will be saved.\n"
        + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_SUCCESS = "You attempted %1$s question(s) and your progress has been saved.\n";

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

        return new CommandResult(String.format(MESSAGE_SUCCESS, nonZeroAttemptsResult.size()), true, false, false);
    }

    /**
     * Filters attempts with non zero streak from endResult.
     * @param endResult all attempts from quiz {@link Quiz#end()}
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
