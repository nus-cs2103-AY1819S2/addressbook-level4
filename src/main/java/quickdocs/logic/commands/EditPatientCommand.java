package quickdocs.logic.commands;

import java.util.ArrayList;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.logic.parser.EditPatientParser;
import quickdocs.model.Model;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.patient.PatientEditedFields;
import quickdocs.model.tag.Tag;

/**
 * Edits a current patient record's details
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
            + EditPatientParser.PREFIX_NAME + "NEW NAME "
            + EditPatientParser.PREFIX_NRIC + "NEW NRIC "
            + EditPatientParser.PREFIX_DOB + "NEW DATE OF BIRTH "
            + EditPatientParser.PREFIX_ADDRESS + "NEW ADDRESS "
            + EditPatientParser.PREFIX_EMAIL + "NEW EMAIL "
            + EditPatientParser.PREFIX_CONTACT + "NEW CONTACT "
            + EditPatientParser.PREFIX_GENDER + "NEW GENDER "
            + "[" + EditPatientParser.PREFIX_TAG + "NEW TAG]...\n"
            + "Note: You only need to add the parameters for the details you want to edit.\n"
            + "Example: " + COMMAND_WORD + " S9876543A "
            + EditPatientParser.PREFIX_NAME + "John Doe "
            + EditPatientParser.PREFIX_NRIC + "S9876542C "
            + EditPatientParser.PREFIX_ADDRESS + "311, Clementi Ave 2, #02-26 "
            + EditPatientParser.PREFIX_EMAIL + "johnd@example.com "
            + EditPatientParser.PREFIX_CONTACT + "92344321 "
            + EditPatientParser.PREFIX_GENDER + "M "
            + EditPatientParser.PREFIX_TAG + "highbloodpressure\n";

    private Nric patientToEdit;

    // edited fields will only capture the details to be edited
    private PatientEditedFields editedFields;


    public EditPatientCommand(Nric patientToEdit, PatientEditedFields editedFields) {
        this.patientToEdit = patientToEdit;
        this.editedFields = editedFields;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.isPatientListEmpty()) {
            throw new CommandException(NO_PATIENTS);
        }

        int index = model.getIndexByNric(patientToEdit);
        if (index == -1) {
            throw new CommandException(String.format(NO_PATIENT_FOUND, patientToEdit.toString()));
        }

        Patient patient = model.getPatientAtIndex(index);
        Patient editedPatient = createEditedPatient(patient, editedFields);

        // check if edited patient will have a conflicting nric with another patient record
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

    public Nric getPatientToEdit() {
        return patientToEdit;
    }

    public PatientEditedFields getEditedFields() {
        return editedFields;
    }

    /**
     * Using the editedFields, create a new patient object that will replace the existing one
     * currently in the PatientManager's patientList
     * If editedFields have a value present, the new patient object will set its field's value to the editedField's one
     * else its will use the original value instead
     *
     * @param patient the original patient object to be edited
     * @param editedFields the editedFields created after parsing the user's entered parameters
     * @return the patient object with edited values for the fields specified to be edited
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
                && patientToEdit.toString().equals(((EditPatientCommand) other).getPatientToEdit().toString())
                && editedFields.equals(((EditPatientCommand) other).getEditedFields()));
    }

}
