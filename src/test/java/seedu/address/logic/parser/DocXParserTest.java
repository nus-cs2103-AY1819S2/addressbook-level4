package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VALID_START_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.logic.commands.doctor.DeleteDoctorCommand;
import seedu.address.logic.commands.doctor.DoctorMatchCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.logic.commands.doctor.ListDoctorCommand;
import seedu.address.logic.commands.doctor.SelectDoctorCommand;
import seedu.address.logic.commands.medicalhistory.AddMedHistCommand;
import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.logic.commands.patient.DeletePatientCommand;
import seedu.address.logic.commands.patient.EditPatientCommand;
import seedu.address.logic.commands.patient.EditPatientCommand.EditPatientDescriptor;
import seedu.address.logic.commands.patient.ListPatientCommand;
import seedu.address.logic.commands.patient.SearchPatientCommand;
import seedu.address.logic.commands.patient.SelectPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.FutureAppointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.doctor.DoctorContainsKeywordsPredicate;
import seedu.address.model.person.doctor.DoctorMatch;
import seedu.address.model.person.doctor.DoctorSpecialisationMatchesPredicate;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.person.patient.PatientNameContainsKeywordsPredicate;
import seedu.address.model.person.specialisation.Specialisation;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.DoctorUtil;
import seedu.address.testutil.EditDoctorDescriptorBuilder;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PatientUtil;

public class DocXParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DocXParser parser = new DocXParser();

    @Test
    public void parseCommand_addDoctor() throws Exception {
        Doctor doctor = new DoctorBuilder().build();
        AddDoctorCommand command = (AddDoctorCommand) parser.parseCommand(DoctorUtil.getAddDoctorCommand(doctor));
        assertEquals(new AddDoctorCommand(doctor), command);
    }

    @Test
    public void parseCommand_addAppointment() throws Exception {
        AddAppointmentCommand command = (AddAppointmentCommand) parser.parseCommand(
                AddAppointmentCommand.COMMAND_WORD + DESC_VALID_PATIENT_ID + DESC_VALID_DOCTOR_ID
                        + DESC_VALID_DATE_OF_APPT + DESC_VALID_START_TIME);
        System.out.println(DESC_VALID_DATE_OF_APPT);
        System.out.println(DESC_VALID_START_TIME);
        assertTrue(command instanceof AddAppointmentCommand);
        assertEquals(command, new AddAppointmentCommand(new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(VALID_START_TIME))));
    }

    @Test
    public void parseCommand_addMedHist() throws Exception {
        AddMedHistCommand command = (AddMedHistCommand) parser.parseCommand(
                AddMedHistCommand.COMMAND_WORD + " " + "pid/1 did/1 d/2018-05-05 sw/testWriteUp");
        assertTrue(command instanceof AddMedHistCommand);
        assertEquals(command, new AddMedHistCommand(
                new MedicalHistory(new PersonId("1"), new PersonId("1"),
                        new ValidDate("2018-05-05"), new WriteUp("testWriteUp"))));
    }

    @Test
    public void parseCommand_addPatient() throws Exception {
        Patient patient = new PatientBuilder().build();
        AddPatientCommand command = (AddPatientCommand) parser.parseCommand(PatientUtil.getAddPatientCommand(patient));
        assertEquals(new AddPatientCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deletePatient() throws Exception {
        DeletePatientCommand command = (DeletePatientCommand) parser.parseCommand(
                DeletePatientCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePatientCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteDoctor() throws Exception {
        DeleteDoctorCommand command = (DeleteDoctorCommand) parser.parseCommand(
                DeleteDoctorCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteDoctorCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editPatient() throws Exception {
        Patient patient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(patient).build();
        EditPatientCommand command = (EditPatientCommand) parser.parseCommand(EditPatientCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PatientUtil.getEditPatientDescriptorDetails(descriptor));
        assertEquals(new EditPatientCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_editDoctor() throws Exception {
        Doctor doctor = new DoctorBuilder().build();
        EditDoctorDescriptor descriptor = new EditDoctorDescriptorBuilder(doctor).build();
        EditDoctorCommand command = (EditDoctorCommand) parser.parseCommand(EditDoctorCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + DoctorUtil.getEditDoctorDescriptorDetails(descriptor));
        assertEquals(new EditDoctorCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findPatient() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchPatientCommand command = (SearchPatientCommand) parser.parseCommand(
                SearchPatientCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchPatientCommand(new PatientNameContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_listPatient() throws Exception {
        assertTrue(parser.parseCommand(ListPatientCommand.COMMAND_WORD) instanceof ListPatientCommand);
        assertTrue(parser.parseCommand(ListPatientCommand.COMMAND_WORD + " 3") instanceof ListPatientCommand);
    }

    @Test
    public void parseCommand_listDoctor() throws Exception {
        assertTrue(parser.parseCommand(ListDoctorCommand.COMMAND_WORD) instanceof ListDoctorCommand);
        assertTrue(parser.parseCommand(ListDoctorCommand.COMMAND_WORD + " 3") instanceof ListDoctorCommand);
        assertTrue(parser.parseCommand(ListDoctorCommand.COMMAND_WORD + " ong") instanceof ListDoctorCommand);

        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        ListDoctorCommand command = (ListDoctorCommand) parser.parseCommand(
                ListDoctorCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        DoctorContainsKeywordsPredicate predicate = new DoctorContainsKeywordsPredicate(
                keywords);
        assertEquals(new ListDoctorCommand(predicate), command);
    }

    @Test
    public void parseCommand_selectPatient() throws Exception {
        SelectPatientCommand command = (SelectPatientCommand) parser.parseCommand(
                SelectPatientCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectPatientCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_selectDoctor() throws Exception {
        SelectDoctorCommand command = (SelectDoctorCommand) parser.parseCommand(
                SelectDoctorCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectDoctorCommand(INDEX_FIRST_PERSON), command);
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
    public void parseCommand_doctorMatch() throws Exception {
        Specialisation specialisation = new Specialisation("acupuncture");
        AppointmentDate date = new AppointmentDate("2019-06-20");
        AppointmentTime time = new AppointmentTime("09:00");
        DoctorMatch dm = new DoctorMatch(specialisation, date, time);
        DoctorSpecialisationMatchesPredicate pred = new DoctorSpecialisationMatchesPredicate(dm);
        DoctorMatchCommand command = new DoctorMatchCommand(pred);

        assertEquals(new DoctorMatchCommand(
                        new DoctorSpecialisationMatchesPredicate(
                                new DoctorMatch(new Specialisation("acupuncture"),
                                        new AppointmentDate("2019-06-20"),
                                        new AppointmentTime("09:00")))),
                command);
    }
}
