package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.util.SamplePatientsUtil.getSamplePatients;

import java.time.LocalDate;
import java.time.LocalTime;

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
import seedu.address.model.reminder.Reminder;

public class AddRemCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getSamplePatients());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void init() {
        String title = "Refill Medicine ABC";
        LocalDate date = LocalDate.parse("2019-05-22");
        LocalTime start = LocalTime.parse("13:00");
        LocalTime end = LocalTime.parse("14:00");
        String comment = "This is a comment";
        Reminder toAdd = new Reminder(title, comment, date, start, end);

        model.addRem(toAdd);
    }

    @Test
    public void executeValidAddReminder() throws Exception {
        String title = "Refill Medicine XYZ";
        LocalDate date = LocalDate.parse("2019-05-22");
        LocalTime start = LocalTime.parse("13:00");
        LocalTime end = LocalTime.parse("14:00");
        String comment = "This is a comment";
        Reminder toAdd = new Reminder(title, comment, date, start, end);

        CommandResult commandResult = new AddRemCommand(toAdd).execute(model, commandHistory);

        StringBuilder sb = new StringBuilder();
        sb.append("Reminder added:\n")
                .append(toAdd.toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());

    }

    @Test
    public void executeDuplicateAddReminder() throws Exception {
        String title = "Refill Medicine ABC";
        LocalDate date = LocalDate.parse("2019-05-22");
        LocalTime start = LocalTime.parse("13:00");
        LocalTime end = LocalTime.parse("14:00");
        String comment = "This is a comment";
        Reminder toAdd = new Reminder(title, comment, date, start, end);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddRemCommand.MESSAGE_DUPLICATE_REM);
        new AddRemCommand(toAdd).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        String titleA = "Refill Medicine ABC";
        String titleB = "Refill Medicine XYZ";
        LocalDate date = LocalDate.parse("2019-05-22");
        LocalTime start = LocalTime.parse("13:00");
        LocalTime end = LocalTime.parse("14:00");
        String comment = "This is a comment";

        AddRemCommand addRemA = new AddRemCommand(new Reminder(titleA, comment, date, start, end));
        AddRemCommand addRemB = new AddRemCommand(new Reminder(titleB, comment, date, start, end));

        // same object -> returns true
        assertTrue(addRemA.equals(addRemA));

        // same values -> returns true
        AddRemCommand addRemACopy = new AddRemCommand(new Reminder(titleA, comment, date, start, end));
        assertTrue(addRemA.equals(addRemACopy));

        // different types -> returns false
        assertFalse(addRemA.equals(1));

        // null -> returns false
        assertFalse(addRemA.equals(null));

        // different reminder -> returns false
        assertFalse(addRemA.equals(addRemB));
    }
}
