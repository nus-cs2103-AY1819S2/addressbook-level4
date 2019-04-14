package seedu.address.logic.commands.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WRITEUP;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDHISTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.WriteUp;

/**
 * Edits the writeUp of an existing medical history in the docX.
 */
public class EditMedHistCommand extends Command {
    public static final String COMMAND_WORD = "edit-med-hist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the write up of the medical history identified "
            + "by the index number used in the displayed medical history list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_WRITEUP + "EDITED-SHORT-WRITE-UP \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_WRITEUP + "The patient has a fever in the morning "
            + "and come to me for the second time today because of higher fever ";

    public static final String MESSAGE_EDIT_MEDHIST_SUCCESS = "Edited Write Up of Medical History: %1$s";
    public static final String MESSAGE_NOT_EDITED = "WriteUp to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEDHIST = "This medical history already exists in the docX.";

    private final Index index;
    private final EditMedHistDescriptor editMedHistDescriptor;

    /**
     * @param index                 of the medical history in the filtered medical history list to edit
     * @param editMedHistDescriptor details to edit the medical history with
     */
    public EditMedHistCommand(Index index, EditMedHistCommand.EditMedHistDescriptor editMedHistDescriptor) {
        requireNonNull(index);
        requireNonNull(editMedHistDescriptor);

        this.index = index;
        this.editMedHistDescriptor = new EditMedHistCommand.EditMedHistDescriptor(editMedHistDescriptor);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<MedicalHistory> lastShownList = model.getFilteredMedHistList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDHIST_DISPLAYED_INDEX);
        }

        MedicalHistory medHistToEdit = lastShownList.get(index.getZeroBased());
        MedicalHistory editedMedHist = createEditedMedHist(medHistToEdit, editMedHistDescriptor);
        editedMedHist.setPatient(medHistToEdit.getPatient());
        editedMedHist.setDoctor(medHistToEdit.getDoctor());

        if (!medHistToEdit.isSameMedHist(editedMedHist) && model.hasMedHist(editedMedHist)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEDHIST);
        }

        model.setMedHist(medHistToEdit, editedMedHist);
        //model.updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_EDIT_MEDHIST_SUCCESS, editedMedHist));
    }

    /**
     * Creates and returns a {@code Medical History} with the details of {@code medHistToEdit}
     * edited with {@code editMedHistDescriptor}.
     */
    private static MedicalHistory createEditedMedHist(MedicalHistory medHistToEdit,
                                               EditMedHistCommand.EditMedHistDescriptor editMedHistDescriptor) {
        assert medHistToEdit != null;

        WriteUp updatedWriteUp = editMedHistDescriptor.getWriteUp();

        return new MedicalHistory(medHistToEdit.getPatientId(), medHistToEdit.getDoctorId(),
                medHistToEdit.getDate(), updatedWriteUp);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMedHistCommand)) {
            return false;
        }

        // state check
        EditMedHistCommand e = (EditMedHistCommand) other;
        return index.equals(e.index)
                && editMedHistDescriptor.equals(e.editMedHistDescriptor);
    }

    /**
     * Stores the write up to edit the medical history with. Non-empty write up value will replace the
     * write up of medical history.
     */
    public static class EditMedHistDescriptor {
        private WriteUp writeUp;

        public EditMedHistDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditMedHistDescriptor(EditMedHistCommand.EditMedHistDescriptor toCopy) {
            setWriteUp(toCopy.writeUp);
        }

        /**
         * Returns true if writeUp is edited.
         */
        public boolean isWriteUpEdited() {
            return writeUp != null;
        }

        public void setWriteUp(WriteUp writeUp) {
            this.writeUp = writeUp;
        }

        public WriteUp getWriteUp() {
            return writeUp;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMedHistCommand.EditMedHistDescriptor)) {
                return false;
            }

            // state check
            EditMedHistCommand.EditMedHistDescriptor e = (EditMedHistCommand.EditMedHistDescriptor) other;

            return getWriteUp().equals(e.getWriteUp());
        }
    }
}
