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
 * Selects a record identified using its displayed index from the finance tracker.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";
    public static final String COMMAND_ALIAS2 = "sel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the record identified by the index number used in the displayed record list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_RECORD_SUCCESS = "Selected Record: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Record> filteredRecordList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= filteredRecordList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        model.setSelectedRecord(filteredRecordList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_RECORD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
