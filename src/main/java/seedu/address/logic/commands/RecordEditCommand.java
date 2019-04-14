package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.description.Description;
import seedu.address.model.record.Procedure;
import seedu.address.model.record.Record;

/**
 * Edits the details of an existing person in the address book.
 */
public class RecordEditCommand extends Command {

    public static final String COMMAND_WORD = "recordedit";
    public static final String COMMAND_WORD2 = "redit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Edits the details of the record identified "
            + "by the index number used in the displayed record list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESC + "DETAILS \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESC + "Patient is now fine and has been discharged. ";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Record is successfully edited!";
    public static final String MESSAGE_NOT_EDITED = "Please provide the modified description or procedure.";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the patient's record list.";

    private final Index index;
    private final EditRecordDescriptor editRecordDescriptor;

    /**
     * @param index of the record in the filtered record list to edit
     * @param editRecordDescriptor details to edit the record with
     */
    public RecordEditCommand(Index index, EditRecordDescriptor editRecordDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecordDescriptor);

        this.index = index;
        this.editRecordDescriptor = editRecordDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = createEditedRecord(recordToEdit, editRecordDescriptor);

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
    private static Record createEditedRecord(Record recordToEdit, EditRecordDescriptor editRecordDescriptor) {
        assert recordToEdit != null;

        Procedure updatedProcedure = editRecordDescriptor.getProcedure().orElse(recordToEdit.getProcedure());
        Description updatedDescription = editRecordDescriptor.getDescription().orElse(recordToEdit.getDescription());

        return new Record(recordToEdit.getDoctorName().fullName, updatedDescription.toString(),
                recordToEdit.getRecordDate().getRawFormat(), updatedProcedure.toString());
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
                && this.editRecordDescriptor.equals(((RecordEditCommand) other).editRecordDescriptor);
    }

    /**
     * Stores the details to edit the record with. Each non-empty field value will replace the
     * corresponding field value of the record.
     */
    public static class EditRecordDescriptor {
        private Procedure procedure;
        private Description description;

        public EditRecordDescriptor() {}

        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setDescription(toCopy.description);
            setProcedure(toCopy.procedure);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEditted() {
            return CollectionUtil.isAnyNonNull(procedure, description);
        }

        public void setProcedure(Procedure procedure) {
            this.procedure = procedure;
        }

        public Optional<Procedure> getProcedure() {
            return Optional.ofNullable(procedure);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditRecordDescriptor)) {
                return false;
            }
            EditRecordDescriptor e = (EditRecordDescriptor) other;

            return getDescription().equals(e.getDescription())
                && getProcedure().equals(e.getProcedure());
        }
    }



}
