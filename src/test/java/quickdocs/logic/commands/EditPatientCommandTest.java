package quickdocs.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.model.patient.PatientEditedFields;
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
import quickdocs.model.tag.Tag;

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
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                new EditPatientCommand(new Nric("S9123456D"), peft).execute(modelManager, history));
    }

    @Test
    public void invalidEdit() {

        // no matching nric
        PatientEditedFields peft = new PatientEditedFields();
        peft.setName(new Name("Peter Tay"));
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
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
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
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
