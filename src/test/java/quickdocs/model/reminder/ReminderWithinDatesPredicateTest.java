package quickdocs.model.reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import static quickdocs.testutil.TypicalReminders.REM_A;
import static quickdocs.testutil.TypicalReminders.REM_B;
import static quickdocs.testutil.TypicalReminders.REM_C;
import static quickdocs.testutil.TypicalReminders.REM_D;
import static quickdocs.testutil.TypicalReminders.REM_E;

import java.time.LocalDate;

import org.junit.Test;

/**
 * Contains unit tests for {@code ReminderWithinDatesPredicate}.
 */
public class ReminderWithinDatesPredicateTest {
    private LocalDate start = REM_E.getDate();
    private LocalDate end = REM_C.getDate();

    @Test
    public void predicateTest() {
        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(start, end);

        // these reminders have dates within the predicate's start and end dates
        assertTrue(predicate.test(REM_A));
        assertTrue(predicate.test(REM_B));
        assertTrue(predicate.test(REM_C));
        assertTrue(predicate.test(REM_E));

        // REM_D's date is not within the predicate's start and end dates
        assertFalse(predicate.test(REM_D));
    }

    @Test
    public void equals() {
        ReminderWithinDatesPredicate predicateA = new ReminderWithinDatesPredicate(start, end);

        // test equality of same object -> returns true
        assertEquals(predicateA, predicateA);

        // test equality of different predicates with same values -> returns true
        ReminderWithinDatesPredicate predicateACopy = new ReminderWithinDatesPredicate(start, end);
        assertEquals(predicateA, predicateACopy);

        // test equality with null -> returns false
        assertNotEquals(predicateA, null);

        // test equality of different types -> returns false
        assertNotEquals(predicateA, 1);

        // test equality of two different predicates with different start dates -> returns false
        ReminderWithinDatesPredicate predicateB = new ReminderWithinDatesPredicate(start.minusDays(1), end);
        assertNotEquals(predicateA, predicateB);

        // test equality of two different predicates with different end dates -> returns false
        predicateB = new ReminderWithinDatesPredicate(start, end.plusDays(1));
        assertNotEquals(predicateA, predicateB);
    }
}
