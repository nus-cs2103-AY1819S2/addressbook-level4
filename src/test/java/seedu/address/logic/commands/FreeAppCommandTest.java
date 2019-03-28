package seedu.address.logic.commands;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class FreeAppCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        model.initQuickDocsSampleData();
    }

    @Test
    public void executeFreeApp() throws Exception {
        LocalDate start = LocalDate.of(2019, 04, 01);
        LocalDate end = LocalDate.of(2019, 04, 30);
        CommandResult result = new FreeAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(FreeAppCommand.MESSAGE_SUCCESS, start, end)
                + model.freeApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void executeFreeAppAllFree() throws Exception {
        LocalDate start = LocalDate.of(2019, 04, 01);
        LocalDate end = LocalDate.of(2019, 04, 01);
        CommandResult result = new FreeAppCommand(start, end).execute(model, commandHistory);
        String expected = String.format(FreeAppCommand.MESSAGE_ALL_FREE, start, end)
                + model.freeApp(start, end);

        Assert.assertEquals(result.getFeedbackToUser(), expected);
    }

    @Test
    public void equals() {
        LocalDate start = LocalDate.parse("2019-10-23");
        LocalDate endA = LocalDate.parse("2019-10-30");
        LocalDate endB = LocalDate.parse("2019-10-31");
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
