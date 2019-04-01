package seedu.finance.logic.commands;

import static seedu.finance.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.finance.model.Model.PREDICATE_SHOW_ALL_RECORD;

import java.util.List;

import seedu.finance.commons.core.Messages;
import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.CommandHistory;
import seedu.finance.logic.commands.exceptions.CommandException;
import seedu.finance.model.Model;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Record;


/**
 * Changes the description of an existing expense entry in finance log.
 */
public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";
    public static final String COMMAND_ALIAS = "descr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the description of record identified "
            + "by the index number used in the last record listing. "
            + "Existing remarks will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "[DESCRIPTION]\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_DESCRIPTION + "Father's birthday present.";

    public static final String MESSAGE_ADD_DESCRIPTION_SUCCESS = "Added description to Record: %1$s";
    public static final String MESSAGE_REMOVE_DESCRIPTION_SUCCESS = "Removed description from Record: %1$s";

    private final Index index;
    private final Description description;

    /**
     * @param index Index of the record in the filtered records list to edit description
     * @param description description of the record to be updated to
     */
    public DescriptionCommand(Index index, Description description) {
        requireAllNonNull(index, description);

        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = new Record(recordToEdit.getName(), recordToEdit.getAmount(), recordToEdit.getDate(),
                this.description, recordToEdit.getCategory());

        model.setRecord(recordToEdit, editedRecord);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORD);
        model.commitFinanceTracker();

        return new CommandResult(makeSuccessMessage(editedRecord));
    }

    /**
     * Makes a command execution success message based on whether the description is added to or removed from
     * {@recordToEdit}.
     * @param recordToEdit
     */
    private String makeSuccessMessage(Record recordToEdit) {

        String message = !description.value.isEmpty() ? MESSAGE_ADD_DESCRIPTION_SUCCESS
                : MESSAGE_REMOVE_DESCRIPTION_SUCCESS;
        return String.format(message, recordToEdit);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DescriptionCommand)) {
            return false;
        }

        DescriptionCommand e = (DescriptionCommand) other;
        return index.equals(e.index) && description.equals(e.description);
    }
}
