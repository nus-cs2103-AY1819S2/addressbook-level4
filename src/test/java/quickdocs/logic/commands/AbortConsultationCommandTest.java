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

public class AbortConsultationCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();
    private Patient patient1;

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
        patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        modelManager.addPatient(patient1);
    }

    @Test
    public void noConsultation() {
        AbortConsultationCommand command = new AbortConsultationCommand();
        Assert.assertThrows(CommandException.class, () -> command.execute(modelManager, history));
    }

    @Test
    public void abortConsultation() {
        modelManager.createConsultation(patient1);
        AbortConsultationCommand command = new AbortConsultationCommand();

        try {
            org.junit.Assert.assertEquals(command.execute(modelManager, history).getFeedbackToUser(),
                    AbortConsultationCommand.ABORT_CONSULT_FEEDBACK);

            org.junit.Assert.assertTrue(modelManager.getCurrentConsultation() == null);
        } catch (CommandException ce) {
            org.junit.Assert.fail();
        }


    }

}
