package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Shows the back face of a flashcard in quiz mode.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SHOW = "Back face shown";
    public static final String MESSAGE_FAILURE_NOT_QUIZ_MODE = "Cannot show card outside quiz mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getQuizMode() == QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_FAILURE_NOT_QUIZ_MODE);
        }
        model.setQuizMode(QuizState.QUIZ_MODE_BOTH);
        return new CommandResult(MESSAGE_SHOW);
    }
}
