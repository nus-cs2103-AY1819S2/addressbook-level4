package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Record;

/**
 * Selects a record identified using it's displayed index from the address book.
 */
public class RecordSelectCommand extends Command {

    public static final String COMMAND_WORD = "recordselect";
    public static final String COMMAND_WORD2 = "rselect";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Selects the record identified by the index number used in the displayed record list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final String MESSAGE_SELECT_RECORD_SUCCESS = "Selected Record: %1$s";

    private final Index targetIndex;

    public RecordSelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Record> filteredRecordList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= filteredRecordList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.setSelectedRecord(filteredRecordList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_RECORD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordSelectCommand // instanceof handles nulls
                && targetIndex.equals(((RecordSelectCommand) other).targetIndex)); // state check
    }
}
