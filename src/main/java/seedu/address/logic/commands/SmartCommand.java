package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A smart command. This command will execute a show command when in quiz mode and back face is not yet shown,
 * and execute a good command when in quiz mode and back face is already shown.
 */
public class SmartCommand extends Command {

    public static final String COMMAND_WORD = "";

    private static final String MESSAGE_FAILURE = "";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getQuizMode() == QuizState.QUIZ_MODE_FRONT) {
            return new ShowCommand().execute(model, history);
        } else if (model.getQuizMode() == QuizState.QUIZ_MODE_BOTH) {
            return new GoodCommand().execute(model, history);
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
