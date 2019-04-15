package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.QuizState;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardCollection;
import seedu.address.model.Model;

/**
 * Clears the card collection.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Card collection has been cleared!";
    private static final String MESSAGE_IN_QUIZ = "Cannot clear in quiz mode.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getQuizMode() != QuizState.NOT_QUIZ_MODE) {
            throw new CommandException(MESSAGE_IN_QUIZ);
        }
        model.setCardCollection(new CardCollection());
        model.commitCardCollection(ClearCommand.COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
