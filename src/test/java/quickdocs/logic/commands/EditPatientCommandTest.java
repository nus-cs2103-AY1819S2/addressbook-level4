package quickdocs.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.ModelManager;
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

public class EditPatientCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();

    private Patient patient1;

    @Before
    public void init() {

        createPatientWithStrings("Bob Tan", "S9123456A", "btan@gmail.com",
                "1 Simei Road", "91111111", "M", "1991-01-01");

        patient1 = createPatientWithStrings("Bob Tan", "S9123456A", "btan@gmail.com",
                "1 Simei Road", "91111111", "M", "1991-01-01");
        modelManager.addPatient(patient1);
    }

    @Test
    public void editPatient_noPatientsPresent_throwCommandException() {
        // empty patientlist
        modelManager = new ModelManager();
        PatientEditedFields peft = new PatientEditedFields();
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(new Nric("S9123456D"), peft).execute(modelManager, history));
    }

    @Test
    public void editPatient_noPatientMatch_throwCommandException() {
        // no matching nric
        PatientEditedFields peft = new PatientEditedFields();
        peft.setName(new Name("Peter Tay"));
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(new Nric("S9123456D"), peft).execute(modelManager, history));
    }

    @Test
    public void editPatient_duplicateNricAfterEdit_throwCommandException() {

        PatientEditedFields peft = new PatientEditedFields();
        // add another patient to test
        Patient otherPatient = createPatientWithStrings("Perry Ng", "S9523456B", "png@gmail.com",
                "2 Simei Road", "92222222", "M", "1995-05-05");
        modelManager.addPatient(otherPatient);

        // edit current patient's nric to another patient's nric
        peft.setNric(new Nric("S9123456A"));
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(otherPatient.getNric(), peft).execute(modelManager, history));
    }

    @Test
    public void editPatient_validEdit_success() {
        Patient otherPatient = createPatientWithStrings("Perry Ng", "S9523456B", "png@gmail.com",
                "2 Simei Road", "92222222", "M", "1995-05-05");
        modelManager.addPatient(otherPatient);

        PatientEditedFields editedFields = new PatientEditedFields();
        editedFields.setName(new Name("Bob Toh"));
        editedFields.setNric(new Nric("S9123456C"));

        Patient patientAfterEdit = new Patient(new Name("Bob Toh"), new Nric("S9123456C"), patient1.getEmail(),
                patient1.getAddress(), patient1.getContact(), patient1.getGender(), patient1.getDob(),
                patient1.getTagList());

        try {
            CommandResult commandResult = new EditPatientCommand(patient1.getNric(), editedFields)
                    .execute(modelManager, history);

            StringBuilder sb = new StringBuilder();
            sb.append("Patient edited:\n");
            sb.append("==============================\n");
            sb.append(patientAfterEdit.toString());

            assertEquals(sb.toString(), commandResult.getFeedbackToUser());
        } catch (CommandException ce) {
            Assert.fail();
        }
    }


    @Test
    public void executeValidEditPatient() {
        Name name = new Name("Bob Toh");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("btan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        PatientEditedFields editedFields = new PatientEditedFields();
        editedFields.setName(new Name("Bob Toh"));

        try {
            CommandResult commandResult = new EditPatientCommand(patient1.getNric(), editedFields)
                    .execute(modelManager, history);

            StringBuilder sb = new StringBuilder();
            sb.append("Patient edited:\n");
            sb.append("==============================\n");
            sb.append(patient1.toString());

            assertEquals(sb.toString(), commandResult.getFeedbackToUser());
        } catch (CommandException ce) {
            Assert.fail();
        }
    }

    @Test
    public void equals() {
        PatientEditedFields peft = new PatientEditedFields();
        peft.setNric(new Nric("S9123456Z"));
        EditPatientCommand epc = new EditPatientCommand(patient1.getNric(), peft);

        // same object
        Assert.assertEquals(epc, epc);

        // object compared with is different type
        Assert.assertFalse(epc.equals(1));

        // same field details
        Nric otherNric = patient1.getNric();
        PatientEditedFields peft2 = new PatientEditedFields();
        peft2.setNric(new Nric("S9123456Z"));
        EditPatientCommand epc2 = new EditPatientCommand(otherNric, peft2);
        Assert.assertEquals(epc, epc2);

        // different field details
        PatientEditedFields peft3 = new PatientEditedFields();
        peft3.setNric(new Nric("S9123456D"));
        EditPatientCommand epc3 = new EditPatientCommand(otherNric, peft3);
        Assert.assertFalse(epc.equals(epc3));
    }

    /**
     * Create a patient object with just the strings for the fields
     *
     * @return patient object with fields intialized using their string values
     */
    public Patient createPatientWithStrings(String name, String nric, String email, String address,
                                            String contact, String gender, String dob) {
        Name patName = new Name(name);
        Nric patNric = new Nric(nric);
        Email patEmail = new Email(email);
        Address patAddress = new Address(address);
        Contact patContact = new Contact(contact);
        Gender patGender = new Gender(gender);
        Dob patDob = new Dob(dob);
        ArrayList<Tag> tagList = new ArrayList<Tag>();

        return new Patient(patName, patNric, patEmail, patAddress, patContact, patGender, patDob, tagList);
    }

}
