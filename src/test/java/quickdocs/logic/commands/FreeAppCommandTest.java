package quickdocs.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.testutil.TypicalAppointments.APP_A;
import static quickdocs.testutil.TypicalAppointments.APP_E;
import static quickdocs.testutil.TypicalAppointments.getTypicalAppointmentsQuickDocs;

import java.time.LocalDate;

import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code FreeAppCommand}.
 */
public class FreeAppCommandTest {
    private QuickDocs quickDocs = getTypicalAppointmentsQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeFreeApp() {
        LocalDate start = APP_A.getDate();
        LocalDate end = start.plusDays(30);
        CommandResult result = new FreeAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(FreeAppCommand.MESSAGE_SUCCESS, start, end)
                + model.freeApp(start, end) + "\n";

        assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeFreeAppNoFree() {
        // no free slots for given date
        model.addApp(APP_E);
        LocalDate start = APP_E.getDate();
        CommandResult result = new FreeAppCommand(start, start).execute(model, commandHistory);
        String expected = String.format(FreeAppCommand.MESSAGE_NO_FREE_SLOTS, start, start)
                + model.freeApp(start, start) + "\n";

        assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void equals() {
        LocalDate start = APP_A.getDate();
        LocalDate end = start.plusDays(10);
        FreeAppCommand freeAppA = new FreeAppCommand(start, end);

        // same object -> returns true
        assertEquals(freeAppA, freeAppA);

        // same values -> returns true
        FreeAppCommand freeAppCopy = new FreeAppCommand(start, end);
        assertEquals(freeAppA, freeAppCopy);

        // different types -> returns false
        assertNotEquals(freeAppA, 1);

        // null -> returns false
        assertNotEquals(freeAppA, null);

        // different start date -> returns false
        FreeAppCommand freeAppB = new FreeAppCommand(start.minusDays(1), end);
        assertNotEquals(freeAppA, freeAppB);

        // different end date -> returns false
        freeAppB = new FreeAppCommand(start, end.plusDays(1));
        assertNotEquals(freeAppA, freeAppB);
    }
}
