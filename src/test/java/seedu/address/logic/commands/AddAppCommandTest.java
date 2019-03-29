package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;

public class AddAppCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Nric nric = new Nric("S9534568C");
    private LocalDate date = LocalDate.parse("2019-10-24");
    private String comment = "This is a comment";


    @Before
    public void init() {

        model.initQuickDocsSampleData();

        Nric nric = new Nric("S9534568C");
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");
        String comment = "This is a comment";
        Optional<Patient> patientToAdd = model.getPatientByNric(nric);
        Appointment toAdd = new Appointment(patientToAdd.get(), date, start, end, comment);

        model.addApp(toAdd);
    }

    @Test
    public void executeValidAddAppointment_success() throws Exception {
        Nric nric = new Nric("S9367777A");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");

        CommandResult commandResult = new AddAppCommand(nric, date, start, end, comment)
                .execute(model, commandHistory);
        Optional<Patient> patientToAdd = model.getPatientByNric(nric);
        Appointment toAdd = new Appointment(patientToAdd.get(), date, start, end, comment);

        StringBuilder sb = new StringBuilder();
        sb.append("Appointment added:\n")
                .append(toAdd.toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());

    }

    @Test
    public void executeAddAppointmentWithTimeConflict_failure() throws Exception {
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("16:30");
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_CONFLICTING_APP);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentNonOfficeHours_failure() throws Exception {
        LocalTime start = LocalTime.parse("08:00");
        LocalTime end = LocalTime.parse("10:00");
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_NON_OFFICE_HOURS);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentWithStartAfterEnd_failure() throws Exception {
        LocalTime start = LocalTime.parse("17:00");
        LocalTime end = LocalTime.parse("16:00");
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_START_AFTER_END);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentWithStartEqualsEnd_failure() throws Exception {
        LocalTime start = LocalTime.parse("17:00");
        LocalTime end = start;
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_START_EQUALS_END);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentPatientNotFound_failure() throws Exception {
        Nric nric = new Nric("S9534568I");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_PATIENT_NOT_FOUND);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Nric nricA = new Nric("S9534568C");
        Nric nricB = new Nric("S9367777A");
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        LocalTime end = LocalTime.parse("17:00");

        AddAppCommand addAppA = new AddAppCommand(nricA, date, start, end, comment);
        AddAppCommand addAppB = new AddAppCommand(nricB, date, start, end, comment);

        // same object -> returns true
        assertEquals(addAppA, addAppA);

        // same values -> returns true
        AddAppCommand addAppACopy = new AddAppCommand(nricA, date, start, end, comment);
        assertEquals(addAppA, addAppACopy);

        // different types -> returns false
        assertNotEquals(addAppA, 1);

        // null -> returns false
        assertNotEquals(addAppA, null);

        // different person -> returns false
        assertNotEquals(addAppA, addAppB);
    }
}
