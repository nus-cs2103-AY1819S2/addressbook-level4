package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class ReminderTest {
    private String titleA = "Refill Medicine ABC";
    private String titleB = "Refill Medicine XYZ";
    private String comment = "This is a comment";
    private LocalDate dateA = LocalDate.parse("2019-05-22");
    private LocalDate dateB = LocalDate.parse("2019-05-23");
    private LocalTime startA = LocalTime.parse("12:00");
    private LocalTime startB = LocalTime.parse("13:00");
    private LocalTime end = LocalTime.parse("16:00");

    @Test
    public void equals() {
        Reminder remA = new Reminder(titleA, comment, dateA, startA, end);
        Reminder remB = new Reminder(titleA, comment, dateA, startA, end);

        // test equality of same referenced object
        Assert.assertTrue(remA.equals(remB));

        // test equality of different types
        Assert.assertFalse(remA.equals(1));

        // test equality of two different reminder object with different date
        remB = new Reminder(titleA, comment, dateB, startA, end);

        Assert.assertFalse(remA.equals(remB));

        // test equality of two different reminder object with different start time
        remB = new Reminder(titleA, comment, dateA, startB, end);

        Assert.assertFalse(remA.equals(remB));

        // test equality of two different reminder object with different title
        remB = new Reminder(titleB, comment, dateA, startA, end);

        Assert.assertFalse(remA.equals(remB));
    }
}
