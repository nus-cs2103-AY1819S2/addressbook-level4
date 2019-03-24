package seedu.address.logic.commands;

import static seedu.address.logic.parser.EditPatientParser.PREFIX_ADDRESS;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_CONTACT;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_DOB;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_EMAIL;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_GENDER;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_NAME;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_NRIC;
import static seedu.address.logic.parser.EditPatientParser.PREFIX_TAG;

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

    public static final String COMMAND_WORD = "editpat";
    public static final String COMMAND_ALIAS = "ep";
    public static final String NO_PATIENTS = "No patients records found to edit.\n";
    public static final String NO_PATIENT_FOUND = "No patient with NRIC: %s found.\n";
    public static final String CONFLICTING_NRIC = "Edited NRIC will conflict with another existing entry.\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a patient by first specifying the NRIC.\n"
            + "Parameters: "
            + "ORIGINAL NRIC "
            + PREFIX_NAME + "NEW NAME "
            + PREFIX_NRIC + "NEW NRIC "
            + PREFIX_DOB + "NEW DATE OF BIRTH "
            + PREFIX_ADDRESS + "NEW ADDRESS "
            + PREFIX_EMAIL + "NEW EMAIL "
            + PREFIX_CONTACT + "NEW CONTACT "
            + PREFIX_GENDER + "NEW GENDER "
            + "[" + PREFIX_TAG + "NEW TAG]...\n"
            + "Example: " + COMMAND_WORD + " S9876543A "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S9876542C "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-26 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_CONTACT + "92344321 "
            + PREFIX_GENDER + "M "
            + PREFIX_TAG + "highbloodpressure ";

    private Nric toEdit;
    private PatientEditedFields editedFields;

    public EditPatientCommand(Nric toEdit, PatientEditedFields editedFields) {
        this.toEdit = toEdit;
        this.editedFields = editedFields;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.isPatientListEmpty()) {
            throw new CommandException(NO_PATIENTS);
        }

        //if (!model.checkValidIndex(index)) {
        //    throw new CommandException("Invalid index for editing");
        //}

        int index = model.getIndexByNric(toEdit);
        if (index == -1) {
            throw new CommandException(String.format(NO_PATIENT_FOUND, toEdit.toString()));
        }

        Patient patient = model.getPatientAtIndex(index);
        Patient editedPatient = createEditedPatient(patient, editedFields);

        if (model.checkDuplicatePatientAfterEdit(index, editedPatient)) {
            throw new CommandException(CONFLICTING_NRIC);
        }

        model.replacePatient(index, editedPatient);

        StringBuilder sb = new StringBuilder();
        sb.append("Patient edited:\n");
        sb.append("==============================\n");
        sb.append(editedPatient.toString());

        return new CommandResult(sb.toString(), false, false);
    }

    public Nric getToEdit() {
        return toEdit;
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
                && toEdit.toString().equals(((EditPatientCommand) other).getToEdit().toString())
                && editedFields.equals(((EditPatientCommand) other).getEditedFields()));
    }

}
