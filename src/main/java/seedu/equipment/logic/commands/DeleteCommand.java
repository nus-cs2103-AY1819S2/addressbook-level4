package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Equipment;

/**
 * Deletes an equipment identified using it's displayed index from the Equipment Manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete-e";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the equipment identified by the index number used in the displayed equipment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Equipment: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Equipment> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
        }

        Equipment equipmentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEquipment(equipmentToDelete);
        model.commitEquipmentManager();
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, equipmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
