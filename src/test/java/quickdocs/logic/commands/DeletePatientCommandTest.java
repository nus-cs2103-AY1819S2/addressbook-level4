package quickdocs.logic.commands;

import java.util.ArrayList;

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
import quickdocs.model.tag.Tag;
import quickdocs.testutil.Assert;

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
    public void deletePatient_noPatientFound_throwsCommandException() {
        DeletePatientCommand dc = new DeletePatientCommand(new Nric("S9123456B"));
        Assert.assertThrows(CommandException.class, ()-> dc.execute(modelManager, history));
    }

    @Test
    public void deletePatient_patientFound_success() {

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
        org.junit.Assert.assertEquals(dc, dc);

        org.junit.Assert.assertNotEquals(dc, "abc");

        DeletePatientCommand dc2 = new DeletePatientCommand(new Nric("S9123456A"));
        org.junit.Assert.assertEquals(dc, dc2);

        dc2 = new DeletePatientCommand(new Nric("S9123456B"));
        org.junit.Assert.assertNotEquals(dc, dc2);
    }

}
