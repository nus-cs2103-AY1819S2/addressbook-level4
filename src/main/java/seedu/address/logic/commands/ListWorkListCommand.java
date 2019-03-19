package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKLISTS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all equipments in the equipment manager to the user.
 */
public class ListWorkListCommand extends Command {

    public static final String COMMAND_WORD = "list-w";

    public static final String MESSAGE_SUCCESS = "Listed all worklists";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredWorkListList(PREDICATE_SHOW_ALL_WORKLISTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
