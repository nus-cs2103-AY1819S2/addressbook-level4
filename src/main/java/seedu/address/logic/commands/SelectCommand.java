package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.equipment.Equipment;

/**
 * Selects an equipment identified using it's displayed index from the equipment manager.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the equipment identified by the index number used in the displayed equipment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_EQUIPMENT_SUCCESS = "Selected Equipment: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Equipment> filteredEquipmentList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= filteredEquipmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
        }

        model.setSelectedPerson(filteredEquipmentList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_EQUIPMENT_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
