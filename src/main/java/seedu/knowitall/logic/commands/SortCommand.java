package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.model.Model.COMPARATOR_ASC_SCORE_CARDS;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;

/**
 * Sorts flashcards based on score. Flashcards will be arranged in ascending order, from the lowest score to the
 * highest.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted flashcards with lowest score first";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.getState() != Model.State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        }

        requireNonNull(model);
        model.sortFilteredCard(COMPARATOR_ASC_SCORE_CARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
