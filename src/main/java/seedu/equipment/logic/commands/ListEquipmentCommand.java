package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;

/**
 * Lists all equipments in the equipment manager to the user.
 */
public class ListEquipmentCommand extends Command {

    public static final String COMMAND_WORD = "list-e";

    public static final String MESSAGE_SUCCESS = "Listed all equipment";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
