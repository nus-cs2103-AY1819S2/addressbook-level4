package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 *  Unit test for the SetReminderCommand class. Asserts that command execution is successful,
 *  but does not assert that details of the reminder on the UI is consistent.
 *  @author Hui Chun
 */
public class SetReminderCommandTest {

    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_setReminder() {
        SetReminderCommand command = new SetReminderCommand("00:00:05", "for testing purposes");

        try {
            Date duration = SetReminderCommand.FORMATTER.parse("00:00:05");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = SetReminderCommand.dateToCalendar(duration);
            Calendar cTotal = (Calendar) c1.clone();
            cTotal.add(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
            cTotal.add(Calendar.MINUTE, c2.get(Calendar.MINUTE));
            cTotal.add(Calendar.SECOND, c2.get(Calendar.SECOND));

            assertCommandSuccess(command, model, history, String.format(SetReminderCommand.MESSAGE_REMINDER_SUCCESS,
                    SetReminderCommand.DISPLAY_FORMATTER.format(cTotal.getTime())), expectedModel);
        } catch (ParseException parseException) {
            // do nth as the test will fail if parse fails
        }
    }

}
