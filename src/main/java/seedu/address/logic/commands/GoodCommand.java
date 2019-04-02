package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Good user feedback
 */
public class GoodCommand extends Command {

    public static final String COMMAND_WORD = "good";

    public static final String MESSAGE_GOOD = "Great!";
    public static final String MESSAGE_FAILURE_NOT_QUIZ_MODE = "Cannot give feedback outside quiz mode";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getQuizMode() == 0) {
            throw new CommandException(MESSAGE_FAILURE_NOT_QUIZ_MODE);
        }

        model.addGoodFeedback();

        if (model.getQuizFlashcards().isEmpty()) {
            return new ExitCommand().execute(model, history);
        }

        model.showNextQuizCard();
        return new CommandResult(MESSAGE_GOOD);
    }
}
