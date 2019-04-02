package seedu.knowitall.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.logic.commands.exceptions.CommandException;
import seedu.knowitall.model.Model;

/**
 * Lists all cards in the card folder to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all cards";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        if (model.getState() != Model.State.IN_FOLDER) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);

        }
        requireNonNull(model);
        model.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
