package quickdocs.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static quickdocs.commons.util.StringUtil.fromPathToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import quickdocs.logic.commands.AbortConsultationCommand;
import quickdocs.logic.commands.AddAppCommand;
import quickdocs.logic.commands.AddMedicineCommand;
import quickdocs.logic.commands.AddPatientCommand;
import quickdocs.logic.commands.AddRemCommand;
import quickdocs.logic.commands.ConsultationCommand;
import quickdocs.logic.commands.DeleteAppCommand;
import quickdocs.logic.commands.DeleteRemCommand;
import quickdocs.logic.commands.DiagnosePatientCommand;
import quickdocs.logic.commands.EditPatientCommand;
import quickdocs.logic.commands.EndConsultationCommand;
import quickdocs.logic.commands.ExitCommand;
import quickdocs.logic.commands.FreeAppCommand;
import quickdocs.logic.commands.HelpCommand;
import quickdocs.logic.commands.HistoryCommand;
import quickdocs.logic.commands.ListAppCommand;
import quickdocs.logic.commands.ListConsultationCommand;
import quickdocs.logic.commands.ListPatientCommand;
import quickdocs.logic.commands.ListRemCommand;
import quickdocs.logic.commands.PrescriptionCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Symptom;
import quickdocs.model.patient.Address;
import quickdocs.model.patient.Contact;
import quickdocs.model.patient.Dob;
import quickdocs.model.patient.Email;
import quickdocs.model.patient.Gender;
import quickdocs.model.patient.Name;
import quickdocs.model.patient.Nric;
import quickdocs.model.patient.Patient;
import quickdocs.model.patient.PatientEditedFields;
import quickdocs.model.reminder.Reminder;
import quickdocs.model.tag.Tag;
import quickdocs.testutil.TypicalIndexes;

public class QuickDocsParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final QuickDocsParser parser = new QuickDocsParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommand_addPatient() throws Exception {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");
        Contact contact = new Contact("91111111");
        Gender gender = new Gender("M");
        Dob dob = new Dob("1991-01-01");
        ArrayList<Tag> tagList = new ArrayList<Tag>();
        Patient patient1 = new Patient(name, nric, email, address, contact, gender, dob, tagList);

        String userInput = AddPatientCommand.COMMAND_WORD + " n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress() + " "
                + "c/" + contact.getContact() + " "
                + "g/" + gender.getGender() + " "
                + "d/" + dob.getDob();

        AddPatientCommand command = (AddPatientCommand) parser.parseCommand(userInput);

        assertEquals(new AddPatientCommand(patient1), command);

        // alias test
        userInput = AddPatientCommand.COMMAND_ALIAS + " n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress() + " "
                + "c/" + contact.getContact() + " "
                + "g/" + gender.getGender() + " "
                + "d/" + dob.getDob();
        command = (AddPatientCommand) parser.parseCommand(userInput);
        assertEquals(new AddPatientCommand(patient1), command);
    }

    @Test
    public void parseCommand_editPatient() throws Exception {
        Name name = new Name("Peter Tan");
        Nric nric = new Nric("S9123456A");
        Email email = new Email("ptan@gmail.com");
        Address address = new Address("1 Simei Road");

        String userInput = EditPatientCommand.COMMAND_WORD + " S9123456A " + "n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress();

        PatientEditedFields pef = new PatientEditedFields();
        pef.setName(name);
        pef.setNric(nric);
        pef.setEmail(email);
        pef.setAddress(address);

        EditPatientCommand expectedCommand = new EditPatientCommand(nric, pef);

        EditPatientCommand command = (EditPatientCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);

        //alias test
        userInput = EditPatientCommand.COMMAND_ALIAS + " S9123456A " + "n/" + name.getName() + " "
                + "r/" + nric.getNric() + " "
                + "e/" + email.getEmail() + " "
                + "a/" + address.getAddress();
        command = (EditPatientCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_listPatient() throws Exception {
        String userInput = ListPatientCommand.COMMAND_WORD + " r/S92";
        ListPatientCommand command1 = new ListPatientCommand("S92", false);
        assertEquals(command1, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_WORD + " n/pe";
        ListPatientCommand command2 = new ListPatientCommand("pe", true);
        assertEquals(command2, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_WORD + " t/diabetes";
        ListPatientCommand command3 = new ListPatientCommand(new Tag("diabetes"));
        assertEquals(command3, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_WORD + " 1";
        ListPatientCommand command4 = new ListPatientCommand(1);
        assertEquals(command4, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_WORD;
        ListPatientCommand command5 = new ListPatientCommand();
        assertEquals(command5, parser.parseCommand(userInput));

        // alias tests
        userInput = ListPatientCommand.COMMAND_ALIAS + " r/S92";
        ListPatientCommand command6 = new ListPatientCommand("S92", false);
        assertEquals(command1, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_ALIAS + " n/pe";
        ListPatientCommand command7 = new ListPatientCommand("pe", true);
        assertEquals(command2, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_ALIAS + " t/diabetes";
        ListPatientCommand command8 = new ListPatientCommand(new Tag("diabetes"));
        assertEquals(command3, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_ALIAS + " 1";
        ListPatientCommand command9 = new ListPatientCommand(1);
        assertEquals(command4, parser.parseCommand(userInput));

        userInput = ListPatientCommand.COMMAND_ALIAS;
        ListPatientCommand command10 = new ListPatientCommand();
        assertEquals(command5, parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_consultationcommand() throws Exception {
        String userInput = ConsultationCommand.COMMAND_WORD + " r/S9123456A";
        ConsultationCommand command = new ConsultationCommand("S9123456A");
        assertEquals(command, parser.parseCommand(userInput));

        // alias test
        userInput = ConsultationCommand.COMMAND_ALIAS + " r/S9123456A";
        ConsultationCommand command2 = new ConsultationCommand("S9123456A");
        assertEquals(command, parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_diagnosepatientcommand() throws Exception {
        String userInput = DiagnosePatientCommand.COMMAND_WORD + " a/migrane s/constant headache s/blurred vision";
        Assessment assessment = new Assessment("migrane");
        ArrayList<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));
        symptoms.add(new Symptom("blurred vision"));
        DiagnosePatientCommand command = new DiagnosePatientCommand(new Diagnosis(assessment, symptoms));
        assertEquals(command, parser.parseCommand(userInput));

        //alias test
        userInput = DiagnosePatientCommand.COMMAND_ALIAS + " a/migrane s/constant headache s/blurred vision";
        assessment = new Assessment("migrane");
        symptoms = new ArrayList<>();
        symptoms.add(new Symptom("constant headache"));
        symptoms.add(new Symptom("blurred vision"));
        command = new DiagnosePatientCommand(new Diagnosis(assessment, symptoms));
        assertEquals(command, parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_prescriptioncommand() throws Exception {
        String userInput = "prescribe m/antibiotics q/1";
        ArrayList<String> meds = new ArrayList<>();
        meds.add("antibiotics");
        ArrayList<Integer> qtys = new ArrayList<>();
        qtys.add(1);
        PrescriptionCommand command = new PrescriptionCommand(meds, qtys);
        assertEquals(command, parser.parseCommand(userInput));

    }

    @Test
    public void parseCommand_endconsultation() throws Exception {
        String userInput = "endconsult";
        org.junit.Assert.assertTrue(parser.parseCommand(userInput) instanceof EndConsultationCommand);
    }

    @Test
    public void parseCommand_listconsultation() throws Exception {
        String userInput = "listconsult r/S1234567A";
        ListConsultationCommand command = new ListConsultationCommand("S1234567A");
        assertEquals(command, parser.parseCommand(userInput));
    }

    @Test
    public void abort_consultatiton() throws Exception {
        String userInput = "abort";
        org.junit.Assert.assertTrue(parser.parseCommand(userInput) instanceof AbortConsultationCommand);
    }

    @Test
    public void parseCommand_addAppointment() throws Exception {
        String nricString = "S9234568C";
        String dateString = "2019-10-23";
        String startString = "16:00";
        String endString = "17:00";

        Nric nric = new Nric(nricString);
        LocalDate date = LocalDate.parse(dateString);
        LocalTime start = LocalTime.parse(startString);
        LocalTime end = LocalTime.parse(endString);
        String comment = "This is a test comment";

        String userInput = AddAppCommand.COMMAND_WORD
                + " r/" + nricString
                + " d/" + dateString
                + " s/" + startString
                + " e/" + endString
                + " c/" + comment;
        AddAppCommand command = (AddAppCommand) parser.parseCommand(userInput);
        assertEquals(new AddAppCommand(nric, date, start, end, comment), command);

        // alias test
        userInput = userInput.replace(AddAppCommand.COMMAND_WORD, AddAppCommand.COMMAND_ALIAS);
        command = (AddAppCommand) parser.parseCommand(userInput);
        assertEquals(new AddAppCommand(nric, date, start, end, comment), command);
    }

    @Test
    public void parseCommand_listApp() throws Exception {
        String formatString = "day";
        String dateString = "2019-03-15";
        String nricString = "S9234568C";

        LocalDate date = LocalDate.parse(dateString);
        Nric nric = new Nric(nricString);

        // test for listing appointments by format and date
        String userInput = ListAppCommand.COMMAND_WORD
                + " f/" + formatString
                + " d/" + dateString;
        ListAppCommand command = (ListAppCommand) parser.parseCommand(userInput);
        assertEquals(new ListAppCommand(date, date), command);
        assertTrue(parser.parseCommand(ListAppCommand.COMMAND_WORD) instanceof ListAppCommand);

        // alias test
        userInput = userInput.replace(ListAppCommand.COMMAND_WORD, ListAppCommand.COMMAND_ALIAS);
        command = (ListAppCommand) parser.parseCommand(userInput);
        assertEquals(new ListAppCommand(date, date), command);
        assertTrue(parser.parseCommand(ListAppCommand.COMMAND_ALIAS) instanceof ListAppCommand);

        // test for listing appointments by patient's NRIC
        userInput = ListAppCommand.COMMAND_WORD
                + " r/" + nricString;
        command = (ListAppCommand) parser.parseCommand(userInput);
        assertEquals(new ListAppCommand(nric), command);

        // alias test
        userInput = userInput.replace(ListAppCommand.COMMAND_WORD, ListAppCommand.COMMAND_ALIAS);
        command = (ListAppCommand) parser.parseCommand(userInput);
        assertEquals(new ListAppCommand(nric), command);
    }

    @Test
    public void parseCommand_freeApp() throws Exception {
        String formatString = "day";
        String dateString = "2019-03-15";
        LocalDate date = LocalDate.parse(dateString);

        String userInput = FreeAppCommand.COMMAND_WORD
                + " f/" + formatString
                + " d/" + dateString;
        FreeAppCommand command = (FreeAppCommand) parser.parseCommand(userInput);
        assertEquals(new FreeAppCommand(date, date), command);
        assertTrue(parser.parseCommand(FreeAppCommand.COMMAND_WORD) instanceof FreeAppCommand);

        // alias test
        userInput = userInput.replace(FreeAppCommand.COMMAND_WORD, FreeAppCommand.COMMAND_ALIAS);
        command = (FreeAppCommand) parser.parseCommand(userInput);
        assertEquals(new FreeAppCommand(date, date), command);
        assertTrue(parser.parseCommand(FreeAppCommand.COMMAND_ALIAS) instanceof FreeAppCommand);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        String dateString = "2019-03-15";
        String startString = "09:00";

        LocalDate date = LocalDate.parse(dateString);
        LocalTime start = LocalTime.parse(startString);

        String userInput = DeleteAppCommand.COMMAND_WORD
                + " d/" + dateString
                + " s/" + startString;
        DeleteAppCommand command = (DeleteAppCommand) parser.parseCommand(userInput);
        assertEquals(new DeleteAppCommand(date, start), command);

        // alias test
        userInput = userInput.replace(DeleteAppCommand.COMMAND_WORD, DeleteAppCommand.COMMAND_ALIAS);
        command = (DeleteAppCommand) parser.parseCommand(userInput);
        assertEquals(new DeleteAppCommand(date, start), command);
    }

    @Test
    public void parseCommand_addReminder() throws Exception {
        String title = "Refill Medicine ABC";
        String dateString = "2019-05-22";
        String startString = "13:00";
        String endString = "14:00";
        String comment = "This is a test comment";

        LocalDate date = LocalDate.parse(dateString);
        LocalTime start = LocalTime.parse(startString);
        LocalTime end = LocalTime.parse(endString);
        Reminder toAdd = new Reminder(title, comment, date, start, end);

        String userInput = AddRemCommand.COMMAND_WORD
                + " t/" + title
                + " d/" + dateString
                + " s/" + startString
                + " e/" + endString
                + " c/" + comment;
        AddRemCommand command = (AddRemCommand) parser.parseCommand(userInput);
        assertEquals(new AddRemCommand(toAdd), command);

        // alias test
        userInput = userInput.replace(AddRemCommand.COMMAND_WORD, AddRemCommand.COMMAND_ALIAS);
        command = (AddRemCommand) parser.parseCommand(userInput);
        assertEquals(new AddRemCommand(toAdd), command);
    }

    @Test
    public void parseCommand_deleteRem() throws Exception {
        DeleteRemCommand command = (DeleteRemCommand) parser.parseCommand(
                DeleteRemCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new DeleteRemCommand(TypicalIndexes.INDEX_FIRST_REMINDER), command);

        // alias test
        command = (DeleteRemCommand) parser.parseCommand(
                DeleteRemCommand.COMMAND_ALIAS + " " + TypicalIndexes.INDEX_FIRST_REMINDER.getOneBased());
        assertEquals(new DeleteRemCommand(TypicalIndexes.INDEX_FIRST_REMINDER), command);
    }

    @Test
    public void parseCommand_listReminder() throws Exception {
        String formatString = "day";
        String dateString = "2019-03-15";
        LocalDate date = LocalDate.parse(dateString);

        String userInput = ListRemCommand.COMMAND_WORD
                + " f/" + formatString
                + " d/" + dateString;
        ListRemCommand command = (ListRemCommand) parser.parseCommand(userInput);
        assertEquals(new ListRemCommand(date, date), command);
        assertTrue(parser.parseCommand(ListRemCommand.COMMAND_WORD) instanceof ListRemCommand);

        // alias test
        userInput = userInput.replace(ListRemCommand.COMMAND_WORD, ListRemCommand.COMMAND_ALIAS);
        command = (ListRemCommand) parser.parseCommand(userInput);
        assertEquals(new ListRemCommand(date, date), command);
        assertTrue(parser.parseCommand(ListRemCommand.COMMAND_ALIAS) instanceof ListRemCommand);
    }

    @Test
    public void parseCommand_addMedicine() throws Exception {
        String[] path = new String[] {"root", "TCM", "Herbs"};
        String rawPath = fromPathToString(path);
        String name = "panaddol";
        StringBuilder sb = new StringBuilder();
        sb.append(AddMedicineCommand.COMMAND_WORD + " " + rawPath + " ");
        sb.append(name + " p/30 ");
        int quantity = 60;
        sb.append("q/" + quantity);
        assertTrue(new AddMedicineCommand(path, name, Optional.of(quantity), Optional.of(new BigDecimal(30)))
                .equals(parser.parseCommand(sb.toString())));
    }
}
