package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalAppointments.APP_A;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.EVE;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientQuickDocs;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.QuickDocs;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;

public class AddAppCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalPatientQuickDocs();
    private Model model = new ModelManager(new AddressBook(), quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Nric nric = ALICE.getNric();
    private LocalDate date = APP_A.getDate();
    private LocalTime start = APP_A.getStart();
    private LocalTime end = APP_A.getEnd();
    private String comment = APP_A.getComment();

    @Test
    public void executeValidAddAppointment_success() throws Exception {
        CommandResult commandResult = new AddAppCommand(nric, date, start, end, comment)
                .execute(model, commandHistory);
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment added:\n")
                .append(APP_A.toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeAddAppointmentWithTimeConflict_failure() throws Exception {
        model.addApp(APP_A);
        AddAppCommand addAppCommand = new AddAppCommand(BOB.getNric(), date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_CONFLICTING_APP);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentNonOfficeHours_failure() throws Exception {
        LocalTime start = LocalTime.parse("08:00");
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_NON_OFFICE_HOURS);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentWithStartAfterEnd_failure() throws Exception {
        LocalTime start = LocalTime.parse("17:30");
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_START_AFTER_END);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentWithStartEqualsEnd_failure() throws Exception {
        LocalTime end = start;
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_START_EQUALS_END);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAddAppointmentPatientNotFound_failure() throws Exception {
        Nric nric = EVE.getNric();
        AddAppCommand addAppCommand = new AddAppCommand(nric, date, start, end, comment);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddAppCommand.MESSAGE_PATIENT_NOT_FOUND);
        addAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        Nric nricB = EVE.getNric();

        AddAppCommand addAppA = new AddAppCommand(nric, date, start, end, comment);
        AddAppCommand addAppB = new AddAppCommand(nricB, date, start, end, comment);

        // same object -> returns true
        assertEquals(addAppA, addAppA);

        // same values -> returns true
        AddAppCommand addAppACopy = new AddAppCommand(nric, date, start, end, comment);
        assertEquals(addAppA, addAppACopy);

        // different types -> returns false
        assertNotEquals(addAppA, 1);

        // null -> returns false
        assertNotEquals(addAppA, null);

        // different person -> returns false
        assertNotEquals(addAppA, addAppB);
    }
}
