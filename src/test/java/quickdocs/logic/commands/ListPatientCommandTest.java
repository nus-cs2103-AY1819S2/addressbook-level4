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

public class ListPatientCommandTest {

    private ModelManager modelManager = new ModelManager();
    private final CommandHistory history = new CommandHistory();
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

    @Before
    public void init() {
        Name name = new Name("John Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("jtan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        tagList.add(new Tag("Diabetes"));
        patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        Name name2 = new Name("Jack Tan");
        Nric nric2 = new Nric("S9142356B");
        ArrayList<Tag> tagList2 = new ArrayList<Tag>();
        tagList2.add(new Tag("Highbloodpressure"));
        patient2 = new Patient(name2, nric2, email, address, contact, gender, dob, tagList2);

        Name name3 = new Name("Jeremy Toh");
        Nric nric3 = new Nric("S9132456C");
        ArrayList<Tag> tagList3 = new ArrayList<Tag>();
        tagList3.add(new Tag("Highbloodpressure"));
        tagList3.add(new Tag("Diabetes"));
        patient3 = new Patient(name3, nric3, email, address, contact, gender, dob, tagList3);

        modelManager.addPatient(patient1);
        modelManager.addPatient(patient2);
        modelManager.addPatient(patient3);
    }

    @Test
    public void listPatient_noPatients_throwsCommandException() {
        // empty patient list
        ListPatientCommand listcommand = new ListPatientCommand();
        modelManager = new ModelManager();
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand.execute(modelManager, history));
    }

    @Test
    public void listPatient_noMatchingName_throwsCommandException() {
        ListPatientCommand listcommand = new ListPatientCommand("Ba", false);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand.execute(modelManager, history));

        ListPatientCommand listcommand2 = new ListPatientCommand("@@@", false);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand2.execute(modelManager, history));

        // no patients have "ang" in their names
        ListPatientCommand listPatientCommand = new ListPatientCommand("ang", true);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listPatientCommand.execute(modelManager, history));
    }

    @Test
    public void listPatient_nameSequenceMatch_success() {
        try {
            ListPatientCommand listPatientCommand = new ListPatientCommand("J", true);

            StringBuilder sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(1 + ") " + "John Tan"
                    + " " + "S9123456A"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(2 + ") " + "Jack Tan"
                    + " " + "S9142356B"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(3 + ") " + "Jeremy Toh"
                    + " " + "S9132456C"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append("\n");

            // list all patients containing j
            Assert.assertEquals(listPatientCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());

            // list patients whose name contains jo
            listPatientCommand = new ListPatientCommand("Jo", true);
            sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(patient1.toString());

            Assert.assertEquals(listPatientCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void listPatient_invalidIndex_throwsCommandException() {
        // invalid index
        ListPatientCommand listcommand = new ListPatientCommand(50);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand.execute(modelManager, history));
    }

    @Test
    public void listPatient_validIndex_throwsCommandException() {
        try {
            ListPatientCommand listcommand2 = new ListPatientCommand(1);
            CommandResult cr = listcommand2.execute(modelManager, history);
            StringBuilder sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(patient1.toString());
            Assert.assertEquals(sb.toString(), cr.getFeedbackToUser());
        } catch (Exception ce) {
            Assert.fail();
        }
    }

    @Test
    public void listPatient_nricNotPresent_throwsCommandException() {
        ListPatientCommand listPatientCommand = new ListPatientCommand("S88", false);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listPatientCommand.execute(modelManager, history));
    }

    @Test
    public void listPatient_nricPresent_throwsCommandException() {
        try {
            ListPatientCommand listPatientCommand = new ListPatientCommand("S91", false);

            StringBuilder sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(1 + ") " + "John Tan"
                    + " " + "S9123456A"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(2 + ") " + "Jack Tan"
                    + " " + "S9142356B"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(3 + ") " + "Jeremy Toh"
                    + " " + "S9132456C"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append("\n");

            // list all patients whose nric starts with S91
            Assert.assertEquals(listPatientCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());

            // list a single patient that fulfills the search criteria

            listPatientCommand = new ListPatientCommand("S912", false);
            sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(patient1.toString());

            Assert.assertEquals(listPatientCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());

        } catch (CommandException ce) {
            Assert.fail();
        }
    }

    @Test
    public void listPatient_noPatientWithTag_throwsCommandException() {
        Tag tag = new Tag("Gout");
        ListPatientCommand listPatientCommand = new ListPatientCommand(tag);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listPatientCommand.execute(modelManager, history));
    }

    @Test
    public void listPatient_incompleteTag_throwsCommandException() {
        //incomplete tags
        Tag tag2 = new Tag("Diab");
        ListPatientCommand listPatientCommand2 = new ListPatientCommand(tag2);
        quickdocs.testutil.Assert.assertThrows(CommandException.class, () ->
                listPatientCommand2.execute(modelManager, history));
    }

    @Test
    public void listPatient_validAndPresentTag_throwsCommandException() {

        try {
            Tag tag = new Tag("Diabetes");
            ListPatientCommand listPatientCommand = new ListPatientCommand(tag);

            StringBuilder sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(1 + ") " + "John Tan"
                    + " " + "S9123456A"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(3 + ") " + "Jeremy Toh"
                    + " " + "S9132456C"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append("\n");
            Assert.assertEquals(listPatientCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());
        } catch (CommandException ce) {
            Assert.fail();
        }
    }

    @Test
    public void listPatient_noArgs_success() {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Listing patients:\n");
            sb.append("==============================\n");
            sb.append(1 + ") " + "John Tan"
                    + " " + "S9123456A"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(2 + ") " + "Jack Tan"
                    + " " + "S9142356B"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append(3 + ") " + "Jeremy Toh"
                    + " " + "S9132456C"
                    + " " + "M"
                    + " " + "1991-01-01"
                    + "\n");
            sb.append("\n");

            ListPatientCommand listPatientCommand = new ListPatientCommand();
            CommandResult cr = listPatientCommand.execute(modelManager, history);
            Assert.assertEquals(cr.getFeedbackToUser(), sb.toString());

        } catch (CommandException ce) {
            Assert.fail();
        }
    }

    @Test
    public void equals() {
        ListPatientCommand lpc = new ListPatientCommand();
        Assert.assertEquals(lpc, lpc);

        Assert.assertNotEquals(lpc, 1);

        // default case
        ListPatientCommand lpc2 = new ListPatientCommand();
        Assert.assertEquals(lpc, lpc2);

        // index check
        lpc = new ListPatientCommand(1);
        lpc2 = new ListPatientCommand(1);
        Assert.assertEquals(lpc, lpc2);
        lpc2 = new ListPatientCommand(2);
        Assert.assertNotEquals(lpc, lpc2);

        // name check
        lpc = new ListPatientCommand("name1", true);
        lpc2 = new ListPatientCommand("name1", true);
        Assert.assertEquals(lpc, lpc2);
        lpc2 = new ListPatientCommand("name2", true);
        Assert.assertNotEquals(lpc, lpc2);

        // nric check
        lpc = new ListPatientCommand("1", false);
        lpc2 = new ListPatientCommand("1", false);
        Assert.assertEquals(lpc, lpc2);
        lpc2 = new ListPatientCommand("2", false);
        Assert.assertNotEquals(lpc, lpc2);

        // tag check
        lpc = new ListPatientCommand(new Tag("a"));
        lpc2 = new ListPatientCommand(new Tag("a"));
        Assert.assertEquals(lpc, lpc2);
        lpc2 = new ListPatientCommand(new Tag("b"));
        Assert.assertNotEquals(lpc, lpc2);
    }
}
