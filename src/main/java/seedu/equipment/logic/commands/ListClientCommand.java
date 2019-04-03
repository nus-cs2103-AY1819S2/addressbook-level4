package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_EQUIPMENT;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;

/**
 * Lists all clients in the equipment manager to the user.
 */
public class ListClientCommand extends Command {

    public static final String COMMAND_WORD = "list-c";

    public static final String MESSAGE_SUCCESS = "Listed all clients";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_EQUIPMENT);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
