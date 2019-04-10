package quickdocs.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static quickdocs.testutil.TypicalAppointments.APP_A;
import static quickdocs.testutil.TypicalAppointments.APP_B;
import static quickdocs.testutil.TypicalAppointments.APP_E;
import static quickdocs.testutil.TypicalAppointments.getTypicalAppointmentsQuickDocs;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;

public class DeleteAppCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalAppointmentsQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void executeValidDeleteAppointment() throws Exception {
        LocalDate date = APP_A.getDate();
        LocalTime start = APP_A.getStart();

        CommandResult commandResult = new DeleteAppCommand(date, start).execute(model, commandHistory);

        StringBuilder sb = new StringBuilder();
        sb.append("Appointment deleted:\n")
                .append(APP_A.toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeInvalidDeleteAppointment_nonOfficeHours() throws Exception {
        // Start time outside of office hours
        LocalDate date = APP_E.getDate();
        LocalTime start = LocalTime.parse("08:00");
        DeleteAppCommand deleteAppCommand = new DeleteAppCommand(date, start);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteAppCommand.MESSAGE_NON_OFFICE_HOURS);
        deleteAppCommand.execute(model, commandHistory);
    }

    @Test
    public void executeInvalidDeleteAppointment_appNotFound() throws Exception {
        // No such appointment with this date and start time
        LocalDate date = APP_E.getDate();
        LocalTime start = APP_E.getStart();
        DeleteAppCommand deleteAppCommand = new DeleteAppCommand(date, start);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteAppCommand.MESSAGE_APPOINTMENT_NOT_FOUND);
        deleteAppCommand.execute(model, commandHistory);
    }

    @Test
    public void equals() {
        LocalDate dateA = APP_A.getDate();
        LocalTime startA = APP_A.getStart();
        LocalDate dateB = APP_B.getDate();
        LocalTime startB = APP_B.getStart();

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

        // different value -> returns false
        assertFalse(deleteAppA.equals(deleteAppB));
    }
}
