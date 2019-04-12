package seedu.equipment.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.exceptions.CommandException;
import seedu.equipment.model.Model;
import seedu.equipment.model.equipment.Name;

/**
 * Selects a client identified using it's displayed index from the equipment manager.
 */
public class SelectClientCommand extends Command {

    public static final String COMMAND_WORD = "select-c";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the client identified by the index number used in the displayed client details list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CLIENT_SUCCESS = "Selected Client: %1$s";

    private final Index targetIndex;

    public SelectClientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Name> filteredClientList = model.getFilteredClientList();

        if (targetIndex.getZeroBased() >= filteredClientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }
        model.setSelectedClient(filteredClientList.get(targetIndex.getZeroBased()));

        return new CommandResult(String.format(MESSAGE_SELECT_CLIENT_SUCCESS, targetIndex.getOneBased()),
                false, false, false, false, null, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectClientCommand // instanceof handles nulls
                && targetIndex.equals(((SelectClientCommand) other).targetIndex)); // state check
    }
}
