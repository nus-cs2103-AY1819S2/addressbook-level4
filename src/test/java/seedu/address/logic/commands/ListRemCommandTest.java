package seedu.address.logic.commands;

import static seedu.address.model.util.SamplePatientsUtil.getSamplePatients;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;

public class ListRemCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), getSamplePatients());
    private CommandHistory commandHistory = new CommandHistory();

    private String titleA = "Refill Medicine ABC";
    private String titleB = "Refill Medicine ABC";
    private String titleC = "Refill Medicine ABC";
    private String comment = "This is a comment";
    private LocalDate dateA = LocalDate.parse("2019-05-22");
    private LocalDate dateB = LocalDate.parse("2019-06-23");
    private LocalDate dateC = LocalDate.parse("2019-07-24");
    private LocalTime start = LocalTime.parse("13:00");
    private LocalTime end = LocalTime.parse("20:00");



    private Reminder toAddA = new Reminder(titleA, comment, dateA, start, end);
    private Reminder toAddB = new Reminder(titleB, comment, dateB, start, end);
    private Reminder toAddC = new Reminder(titleC, comment, dateC, start, end);

    @Before
    public void init() {
        model.addRem(toAddA);
        model.addRem(toAddB);
        model.addRem(toAddC);
    }

    @Test
    public void executeListRem() {
        CommandResult result = new ListRemCommand().execute(model, commandHistory);
        StringBuilder expected = new StringBuilder();
        expected.append(ListRemCommand.MESSAGE_SUCCESS)
                .append(model.listRem());

        Assert.assertTrue(result.getFeedbackToUser().equals(expected.toString()));
    }

    @Test
    public void equals() {
        ListRemCommand listRem = new ListRemCommand();

        // same object -> returns true
        Assert.assertTrue(listRem.equals(listRem));

        // different types -> returns false
        Assert.assertFalse(listRem.equals(1));

        // null -> returns false
        Assert.assertFalse(listRem.equals(null));
    }
}
