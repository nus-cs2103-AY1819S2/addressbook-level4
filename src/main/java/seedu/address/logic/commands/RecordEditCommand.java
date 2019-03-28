package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.description.Description;
import seedu.address.model.record.Record;

/**
 * Edits the details of an existing person in the address book.
 */
public class RecordEditCommand extends Command {

    public static final String COMMAND_WORD = "recordedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the record identified "
            + "by the index number used in the displayed record list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESC + "DETAILS \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESC + "Patient is now fine and has been discharged. ";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Record is successfully edited!";
    public static final String MESSAGE_NOT_EDITED = "Please provide the modified description.";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the patient's record list.";

    private final Index index;
    private final Description description;

    /**
     * @param index of the record in the filtered record list to edit
     * @param description details to edit the record with
     */
    public RecordEditCommand(Index index, Description description) {
        requireNonNull(index);
        requireNonNull(description);

        this.index = index;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = createEditedRecord(recordToEdit, description);

        if (!recordToEdit.equals(editedRecord) && model.hasRecord(editedRecord)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.setRecord(recordToEdit, editedRecord);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS));
    }

    /**
     * Creates and returns a {@code Record} with the details of {@code recordToEdit}
     * edited with {@code description}.
     */
    private static Record createEditedRecord(Record recordToEdit, Description description) {
        assert recordToEdit != null;

        return new Record(recordToEdit.getDoctorName().fullName, description.value,
                recordToEdit.getRecordDate().getRawFormat());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RecordEditCommand)) {
            return false;
        }

        // state check
        RecordEditCommand e = (RecordEditCommand) other;
        return index.equals(e.index)
                && this.description.equals(((RecordEditCommand) other).description);
    }

}
