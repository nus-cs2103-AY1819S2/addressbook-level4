package quickdocs.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.testutil.TypicalReminders.REM_A;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

/**
 * Contains unit tests for {@code Reminder}.
 */
public class ReminderTest {
    @Test
    public void equals() {
        String title = REM_A.getTitle();
        String comment = REM_A.getComment();
        LocalDate date = REM_A.getDate();
        LocalTime start = REM_A.getStart();
        LocalTime end = REM_A.getEnd();

        // test equality of same referenced object -> returns true
        assertEquals(REM_A, REM_A);

        // test equality of different reminders with same values -> returns true
        Reminder remACopy = new Reminder(title, comment, date, start, end);
        assertEquals(REM_A, remACopy);

        // test equality of different types -> returns false
        assertNotEquals(REM_A, date);

        // test equality of two different reminder object with different title -> returns false
        Reminder remB = new Reminder(title + " different title", comment, date, start, end);
        assertNotEquals(REM_A, remB);

        // test equality of two different reminder object with different comment -> returns false
        remB = new Reminder(title, comment + " different comment", date, start, end);
        assertNotEquals(REM_A, remB);

        // test equality of two different reminder object with different date -> returns false
        remB = new Reminder(title, comment, date.minusDays(1), start, end);
        assertNotEquals(REM_A, remB);

        // test equality of two different reminder object with different start time -> returns false
        remB = new Reminder(title, comment, date, start.minusHours(1), end);
        assertNotEquals(REM_A, remB);

        // test equality of two different reminder object with different end time -> returns false
        remB = new Reminder(title, comment, date, start, end.plusHours(1));
        assertNotEquals(REM_A, remB);

        // test equality with null comment -> returns false
        remB = new Reminder(title, null, date, start, end);
        assertNotEquals(REM_A, remB);

        // test equality with null end time -> returns false
        remB = new Reminder(title, comment, date, start, null);
        assertNotEquals(REM_A, remB);
    }
}
