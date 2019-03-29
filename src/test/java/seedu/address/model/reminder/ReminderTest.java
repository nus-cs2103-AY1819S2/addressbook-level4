package seedu.address.model.reminder;

import static seedu.address.testutil.TypicalReminders.REM_A;
import static seedu.address.testutil.TypicalReminders.REM_B;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class ReminderTest {
    @Test
    public void equals() {
        String title = REM_A.getTitle();
        String comment = REM_A.getComment();
        LocalDate date = REM_A.getDate();
        LocalTime start = REM_A.getStart();
        LocalTime end = REM_A.getEnd();
        Reminder remB;

        // test equality of same referenced object
        Assert.assertTrue(REM_A.equals(REM_A));

        // test equality of different appointments with same values
        remB = new Reminder(title, comment, date, start, end);
        Assert.assertTrue(REM_A.equals(remB));

        // test equality of different types
        Assert.assertFalse(REM_A.equals(1));

        // test equality of two different reminder object
        Assert.assertFalse(REM_A.equals(REM_B));

        // test equality of two different reminder object with different date
        remB = new Reminder(title, comment, date.minusDays(1), start, end);

        Assert.assertFalse(REM_A.equals(remB));

        // test equality of two different reminder object with different start time
        remB = new Reminder(title, comment, date, start.minusHours(1), end);

        Assert.assertFalse(REM_A.equals(remB));

        // test equality of two different reminder object with different title
        remB = new Reminder(title + " append", comment, date, start, end);

        Assert.assertFalse(REM_A.equals(remB));
    }
}
