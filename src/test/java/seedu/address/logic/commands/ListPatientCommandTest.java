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
    public void noPatientsToList() {

        // empty patient list

        ListPatientCommand listcommand = new ListPatientCommand();
        modelManager = new ModelManager();
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand.execute(modelManager, history));
    }

    @Test
    public void noPatientFoundName() {
        ListPatientCommand listcommand = new ListPatientCommand("Ba", true);
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand.execute(modelManager, history));
    }

    @Test
    public void findPatientByIndex() {
        // invalid index
        ListPatientCommand listcommand = new ListPatientCommand(50);
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                listcommand.execute(modelManager, history));

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
    public void findPatientsStartingWithJ() {
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

            // list all patients starting with j
            Assert.assertEquals(listPatientCommand.execute(modelManager, history).getFeedbackToUser(),
                    sb.toString());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void findPatientsStartingWithJo() {
        try {
            // list a single patient that fulfills the search criteria
            Name name = new Name("John Tan");
            Nric nric = new Nric("S9123456A");
            Email email = new Email("jtan@gmail.com");
            Address address = new Address("1 Simei Road");
            Contact contact = new Contact("91111111");
            Gender gender = new Gender("M");
            Dob dob = new Dob("1991-01-01");
            ArrayList<Tag> tagList = new ArrayList<Tag>();
            tagList.add(new Tag("Diabetes"));
            Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

            ListPatientCommand listPatientCommand = new ListPatientCommand("Jo", true);
            StringBuilder sb = new StringBuilder();
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
    public void noPatientWithGivenNric() {
        ListPatientCommand listPatientCommand = new ListPatientCommand("S88", true);
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                listPatientCommand.execute(modelManager, history));
    }

    @Test
    public void nricListing() {
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

            Name name = new Name("John Tan");
            Nric nric = new Nric("S9123456A");
            Email email = new Email("jtan@gmail.com");
            Address address = new Address("1 Simei Road");
            Contact contact = new Contact("91111111");
            Gender gender = new Gender("M");
            Dob dob = new Dob("1991-01-01");
            ArrayList<Tag> tagList = new ArrayList<Tag>();
            tagList.add(new Tag("Diabetes"));
            Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

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
    public void noPatientsFoundWithTag() {
        Tag tag = new Tag("Gout");
        ListPatientCommand listPatientCommand = new ListPatientCommand(tag);
        seedu.address.testutil.Assert.assertThrows(CommandException.class, () ->
                listPatientCommand.execute(modelManager, history));
    }

    @Test
    public void tagListing() {

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
    public void listFiftyPatients() {

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
}
