package seedu.address.logic.commands.prescription;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Prescription;

/**
 * Edits the description of an existing prescription in the docX.
 */
public class EditPrescriptionCommand extends Command {
    public static final String COMMAND_WORD = "edit-presc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the description of the prescription identified "
            + "by the index number used in the displayed prescription list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "EDITED-DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "For curing fever ";


    public static final String MESSAGE_EDIT_PRESCRIPTION_SUCCESS =
            "Edited description of the prescription specified: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Description to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PRESCRIPTION =
            "This prescription already exists in the docX.";

    private final Index index;
    private final EditPrescriptionDescriptor editPrescriptionDescriptor;

    /**
     * @param index                 of the prescription in the filtered prescription list to edit
     * @param editPrescriptionDescriptor details to edit the prescription with
     */
    public EditPrescriptionCommand(Index index, EditPrescriptionCommand.EditPrescriptionDescriptor
            editPrescriptionDescriptor) {
        requireNonNull(index);
        requireNonNull(editPrescriptionDescriptor);

        this.index = index;
        this.editPrescriptionDescriptor = new
                EditPrescriptionCommand.EditPrescriptionDescriptor(editPrescriptionDescriptor);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Prescription> lastShownList = model.getFilteredPrescriptionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRESCRIPTION_DISPLAYED_INDEX);
        }

        Prescription prescriptionToEdit = lastShownList.get(index.getZeroBased());
        Prescription editedPrescription = createEditedPrescription(prescriptionToEdit, editPrescriptionDescriptor);
        editedPrescription.setPatient(prescriptionToEdit.getPatient());
        editedPrescription.setDoctor(prescriptionToEdit.getDoctor());

        if (!prescriptionToEdit.isSamePrescription(editedPrescription)
                && model.hasPrescription(editedPrescription)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRESCRIPTION);
        }

        model.setPrescription(prescriptionToEdit, editedPrescription);
        //model.updateFilteredMedHistList(PREDICATE_SHOW_ALL_MEDHISTS);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_EDIT_PRESCRIPTION_SUCCESS, editedPrescription));
    }

    /**
     * Creates and returns a {@code Patient} with the details of {@code patientToEdit}
     * edited with {@code editPatientDescriptor}.
     */
    private static Prescription createEditedPrescription(Prescription prescriptionToEdit,
             EditPrescriptionCommand.EditPrescriptionDescriptor editPrescriptionDescriptor) {
        assert prescriptionToEdit != null;

        Description updatedDescription = editPrescriptionDescriptor.getDescription();

        return new Prescription(prescriptionToEdit.getPatientId(), prescriptionToEdit.getDoctorId(),
                prescriptionToEdit.getDate(), prescriptionToEdit.getMedicine(), updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPrescriptionCommand)) {
            return false;
        }

        // state check
        EditPrescriptionCommand e = (EditPrescriptionCommand) other;
        return index.equals(e.index)
                && editPrescriptionDescriptor.equals(e.editPrescriptionDescriptor);
    }

    /**
     * Stores the write up to edit the medical history with. Non-empty write up value will replace the
     * write up of medical history.
     */
    public static class EditPrescriptionDescriptor {
        private Description description;

        public EditPrescriptionDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPrescriptionDescriptor(EditPrescriptionCommand.EditPrescriptionDescriptor toCopy) {
            setDescription(toCopy.description);
        }

        /**
         * Returns true if writeUp is edited.
         */
        public boolean isDescriptionEdited() {
            return description != null;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Description getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPrescriptionCommand.EditPrescriptionDescriptor)) {
                return false;
            }

            // state check
            EditPrescriptionCommand.EditPrescriptionDescriptor e =
                    (EditPrescriptionCommand.EditPrescriptionDescriptor) other;

            return getDescription().equals(e.getDescription());
        }
    }
}
