package quickdocs.logic.commands;

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
import quickdocs.model.tag.Tag;

public class AddPatientCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();

    private Patient patient1;

    @Before
    public void init() {
        // simulate existing patient
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
    public void addPatient_validAddPatient_success() {
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
    public void addPatient_duplicateNric_throwsCommandException() {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                new AddPatientCommand(patient1).execute(modelManager, history));
    }

    @Test
    public void equals() {

        AddPatientCommand apc = new AddPatientCommand(patient1);
        Assert.assertEquals(apc, apc);

        //non command instance check
        Assert.assertFalse(apc.equals(1));

        //different object with same field values
        AddPatientCommand apc2 = new AddPatientCommand(patient1);
        Assert.assertEquals(apc, apc2);

        //different object, different field values
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456B");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient2 = new Patient(name, nric, email, address, contact, gender, dob, tagList);
        AddPatientCommand apc3 = new AddPatientCommand(patient2);
        Assert.assertFalse(apc.equals(apc3));
    }
}
