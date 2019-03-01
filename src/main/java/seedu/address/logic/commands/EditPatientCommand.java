package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Contact;
import seedu.address.model.patient.Dob;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PatientEditedFields;
import seedu.address.model.tag.Tag;

/**
 * Logic will execute this command to edit a patient's fields
 * based on what is entered by user
 */
public class EditPatientCommand extends Command {

    public static final String COMMAND_WORD = "pedit";

    private int index;
    private PatientEditedFields editedFields;

    public EditPatientCommand(int index, PatientEditedFields editedFields) {
        this.index = index;
        this.editedFields = editedFields;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.isPatientListEmpty()) {
            throw new CommandException("No patients records found to edit");
        }

        if (!model.checkValidIndex(index)) {
            throw new CommandException("Invalid index for editing");
        }

        Patient patient = model.getPatientAtIndex(index);
        Patient editedPatient = createEditedPatient(patient, editedFields);

        if (model.checkDuplicatePatientAfterEdit(index, editedPatient)) {
            throw new CommandException("Edited NRIC will conflict with another existing entry");
        }

        model.replacePatient(index, editedPatient);

        StringBuilder sb = new StringBuilder();
        sb.append("Patient edited:\n");
        sb.append("==============================\n");
        sb.append(editedPatient.toString());

        return new CommandResult(sb.toString(), false, false);
    }

    public int getIndex() {
        return index;
    }

    public PatientEditedFields getEditedFields() {
        return editedFields;
    }

    /**
     * Create a new patient object with the updated fields
     */
    public Patient createEditedPatient(Patient patient, PatientEditedFields editedFields) {

        Name name = editedFields.getName().orElse(patient.getName());
        Nric nric = editedFields.getNric().orElse(patient.getNric());
        Email email = editedFields.getEmail().orElse(patient.getEmail());
        Address address = editedFields.getAddress().orElse(patient.getAddress());
        Contact contact = editedFields.getContact().orElse(patient.getContact());
        Gender gender = editedFields.getGender().orElse(patient.getGender());
        Dob dob = editedFields.getDob().orElse(patient.getDob());
        ArrayList<Tag> tagList = editedFields.getTagList().orElse(patient.getTagList());

        return new Patient(name, nric, email, address, contact, gender, dob, tagList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EditPatientCommand
                && index == (((EditPatientCommand) other).getIndex())
                && editedFields.equals(((EditPatientCommand) other).getEditedFields()));
    }

}
