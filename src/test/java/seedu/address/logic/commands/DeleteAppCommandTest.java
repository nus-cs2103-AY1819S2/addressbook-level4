package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

public class DeleteAppCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        model.initQuickDocsSampleData();
    }

    @Test
    public void executeValidDeleteAppointment() throws Exception {
        LocalDate date = LocalDate.parse("2019-03-15");
        LocalTime start = LocalTime.parse("09:00");
        model.initQuickDocsSampleData();

        CommandResult commandResult = new DeleteAppCommand(date, start).execute(model, commandHistory);
        Optional<Appointment> toDelete = model.getAppointment(date, start);

        StringBuilder sb = new StringBuilder();
        sb.append("Appointment deleted:\n")
                .append(toDelete.get().toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());
    }
    @Test
    public void executeInvalidDeleteAppointment() throws Exception {
        //No such appointment with this date and start time
        LocalDate date = LocalDate.parse("2019-10-23");
        LocalTime start = LocalTime.parse("16:00");
        model.initQuickDocsSampleData();
        DeleteAppCommand deleteAppCommand = new DeleteAppCommand(date, start);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteAppCommand.MESSAGE_APPOINTMENT_NOT_FOUND);
        deleteAppCommand.execute(model, commandHistory);
    }
    @Test
    public void equals() {
        LocalDate dateA = LocalDate.parse("2019-03-15");
        LocalTime startA = LocalTime.parse("09:00");
        LocalDate dateB = LocalDate.parse("2019-04-16");
        LocalTime startB = LocalTime.parse("10:00");
        model.initQuickDocsSampleData();

        DeleteAppCommand deleteAppA = new DeleteAppCommand(dateA, startA);
        DeleteAppCommand deleteAppB = new DeleteAppCommand(dateB, startB);

        // same object -> returns true
        assertTrue(deleteAppA.equals(deleteAppA));

        // same values -> returns true
        DeleteAppCommand deleteAppACopy = new DeleteAppCommand(dateA, startA);
        assertTrue(deleteAppA.equals(deleteAppACopy));

        // different types -> returns false
        assertFalse(deleteAppA.equals(1));

        // null -> returns false
        assertFalse(deleteAppA.equals(null));

        // different person -> returns false
        assertFalse(deleteAppA.equals(deleteAppB));
    }
}
