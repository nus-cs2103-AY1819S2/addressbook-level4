package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;

/**
 * Deletes a work list identified using it's displayed index from the Equipment Manager.
 */
public class DeleteWorkListCommand extends Command {

    public static final String COMMAND_WORD = "delete-w";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the work list identified by the index number used in the displayed WorkList list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_WORKLIST_SUCCESS = "Deleted WorkList: WorkListId ";
    public static final String MESSAGE_INVALID_WORKLIST_DISPLAYED_INDEX = "The work list index provided is invalid.";

    private final Index targetIndex;

    public DeleteWorkListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<WorkList> lastShownList = model.getFilteredWorkListList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_WORKLIST_DISPLAYED_INDEX);
        }

        WorkList workListToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteWorkList(workListToDelete);
        model.commitEquipmentManager();
        return new CommandResult(String.format(MESSAGE_DELETE_WORKLIST_SUCCESS, workListToDelete.getId().value));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWorkListCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteWorkListCommand) other).targetIndex)); // state check
    }
}
