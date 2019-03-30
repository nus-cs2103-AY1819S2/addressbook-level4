package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalAppointments.APP_A;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentsQuickDocs;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.QuickDocs;
import seedu.address.model.UserPrefs;

public class FreeAppCommandTest {
    private QuickDocs quickDocs = getTypicalAppointmentsQuickDocs();
    private Model model = new ModelManager(new AddressBook(), quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeFreeApp() {
        LocalDate start = APP_A.getDate();
        LocalDate end = start.plusDays(30);
        CommandResult result = new FreeAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(FreeAppCommand.MESSAGE_SUCCESS, start, end)
                + model.freeApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeFreeAppAllFree() {
        LocalDate start = APP_A.getDate().minusDays(1);
        CommandResult result = new FreeAppCommand(start, start).execute(model, commandHistory);
        String expected = String.format(FreeAppCommand.MESSAGE_ALL_FREE, start, start)
                + model.freeApp(start, start);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void equals() {
        LocalDate start = APP_A.getDate();
        LocalDate endA = start.plusDays(10);
        LocalDate endB = start.plusDays(20);
        FreeAppCommand freeAppA = new FreeAppCommand(start, endA);
        FreeAppCommand freeAppB = new FreeAppCommand(start, endB);

        // same object -> returns true
        Assert.assertEquals(freeAppA, freeAppA);

        // same values -> returns true
        FreeAppCommand freeAppCopy = new FreeAppCommand(start, endA);
        Assert.assertEquals(freeAppA, freeAppCopy);

        // different types -> returns false
        Assert.assertNotEquals(freeAppA, 1);

        // null -> returns false
        Assert.assertNotEquals(freeAppA, null);

        // different attributes -> returns false
        Assert.assertNotEquals(freeAppA, freeAppB);
    }
}
