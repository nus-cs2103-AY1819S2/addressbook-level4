package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;

/**
 * Lists all equipment in the equipment manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all equipments";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortByName();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
