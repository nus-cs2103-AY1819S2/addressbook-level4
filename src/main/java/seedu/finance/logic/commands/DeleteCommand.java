package seedu.finance.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.finance.commons.core.Messages;
import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.record.Record;

/**
 * Deletes a record identified using its displayed index from the finance tracker.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";
    public static final String COMMAND_ALIAS2 = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the record identified by the index number used in the displayed record list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RECORD_SUCCESS = "Deleted record: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRecord(recordToDelete);
        model.commitFinanceTracker();
        return new CommandResult(String.format(MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete),
                true, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
