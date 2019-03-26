package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
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

public class EditPatientCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();

    private Patient patient1;

    @Before
    public void init() {
        Name name = new Name("Bob Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("btan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);
    }

    /**
     * Check for invalid entries of editing command
     */
    @Test
    public void noPatient() {
        // empty patientlist
        modelManager = new ModelManager();
        PatientEditedFields peft = new PatientEditedFields();
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(new Nric("S9123456D"), peft).execute(modelManager, history));
    }

    @Test
    public void invalidEdit() {

        // no matching nric
        PatientEditedFields peft = new PatientEditedFields();
        peft.setName(new Name("Peter Tay"));
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(new Nric("S9123456D"), peft).execute(modelManager, history));

        // edit current patient's nric to another patient's nric
        Name name = new Name("Perry Ng");
        Nric nric = new Nric("S9523456B");
        Email email = new Email("png@gmail.com");
        Address address = new Address("2 Simei Road");
        Contact contact = new Contact("92222222");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1995-05-05");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);

        peft.setNric(new Nric("S9123456A"));
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(nric, peft).execute(modelManager, history));
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


}
