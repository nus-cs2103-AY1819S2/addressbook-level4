package seedu.address.logic.commands;

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
import seedu.address.model.tag.Tag;

public class AddPatientCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();

    @Before
    public void init() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);
    }

    @Test
    public void executeValidAddPatient() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456B");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        try {
            CommandResult commandResult = new AddPatientCommand(patient1).execute(modelManager, history);

            StringBuilder sb = new StringBuilder();
            sb.append("Patient Added:\n");
            sb.append("==============================\n");
            sb.append(patient1.toString());

            Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());
        } catch (CommandException ce) {
            Assert.fail();
        }
    }

    @Test
    public void executeDuplicateAddPatient() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                new AddPatientCommand(patient1).execute(modelManager, history));


        /*
        try {
            CommandResult commandResult = new AddPatientCommand(patient1).execute(modelManager, history);
        } catch (CommandException ce) {
            Assert.assertEquals(ce.getMessage(), "Patient with same NRIC already exist");
        }
        */
    }
}
