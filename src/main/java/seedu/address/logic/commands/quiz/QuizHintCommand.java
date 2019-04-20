package seedu.address.logic.commands.quiz;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModel;

/**
 * Displays a list of hints of the current card.
 */
public class QuizHintCommand extends QuizCommand {
    public static final String COMMAND_WORD = "\\hint";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays hint(s) for this card.\n"
        + "Example: " + COMMAND_WORD + "\n";

    public static final String MESSAGE_SUCCESS = "Hints: %1$s\n";
    public static final String MESSAGE_SUCCESS_ONE = "Hint: %1$s\n";
    public static final String MESSAGE_EMPTY = "No hint for this card.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        QuizModel quizModel = requireQuizModel(model);

        List<String> hints = quizModel.getOpt();

        if (hints.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY);
        }

        if (hints.size() == 1) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_ONE, hints.toString()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, hints.toString()));
    }
}
