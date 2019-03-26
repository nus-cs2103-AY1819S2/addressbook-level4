package seedu.address.logic.commands;

import java.util.ArrayList;

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
import seedu.address.testutil.Assert;

public class DeletePatientCommandTest {

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
    public void noPatientFound() {
        DeletePatientCommand dc = new DeletePatientCommand(new Nric("S9123456B"));
        Assert.assertThrows(CommandException.class, ()-> dc.execute(modelManager, history));
    }

    @Test
    public void patientFound() {

        DeletePatientCommand dc = new DeletePatientCommand(new Nric("S9123456A"));

        try {
            CommandResult cr = dc.execute(modelManager, history);
            org.junit.Assert.assertEquals(cr.getFeedbackToUser(),
                    new CommandResult(String.format(DeletePatientCommand.PATIENT_DELETED, "S9123456A"))
                            .getFeedbackToUser());
        } catch (Exception e) {
            org.junit.Assert.fail();
        }

    }

    @Test
    public void equalsTest() {
        DeletePatientCommand dc = new DeletePatientCommand(new Nric("S9123456A"));
        org.junit.Assert.assertTrue(dc.equals(dc));

        org.junit.Assert.assertFalse(dc.equals(new Object()));

        DeletePatientCommand dc2 = new DeletePatientCommand(new Nric("S9123456A"));
        org.junit.Assert.assertTrue(dc.equals(dc2));
    }

}
