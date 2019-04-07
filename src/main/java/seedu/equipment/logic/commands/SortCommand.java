package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Equipment;


/**
 * Lists all equipment in the equipment manager to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sort the list either by name, date, serial number or phone\n"
            + "Parameters: sort [FIELD] \n"
            + "Example 1: " + COMMAND_WORD + " name\n"
            + "Example 2: " + COMMAND_WORD + " date\n"
            + "Example 3: " + COMMAND_WORD + " serial\n"
            + "Example 4: " + COMMAND_WORD + " phone\n";

    public static final String MESSAGE_SUCCESS = "List is sorted.";

    private final Comparator<Equipment> comparator;

    public SortCommand(Comparator<Equipment> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortFilteredEquipmentList(comparator);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EQUIPMENT);
        model.commitEquipmentManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public Comparator<Equipment> getComparator() {
        return comparator;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles null
                && comparator.equals(((SortCommand) other).getComparator()));
    }
}
