package seedu.address.model.flashcard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.flashcard.Proficiency.ACTIVE_IN_UNDER_A_MINUTE;
import static seedu.address.model.flashcard.Proficiency.INACTIVE_UNTIL_IN_DAYS;
import static seedu.address.model.flashcard.Proficiency.INACTIVE_UNTIL_IN_HOURS;
import static seedu.address.model.flashcard.Proficiency.INACTIVE_UNTIL_IN_MINUTES;
import static seedu.address.model.flashcard.Proficiency.NOW_ACTIVE;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import seedu.address.testutil.Assert;

public class ProficiencyTest {
    private long halfMinute = TimeUnit.SECONDS.toMillis(30);
    private Calendar now = Calendar.getInstance();
    private Calendar yesterday = Calendar.getInstance();
    private Calendar tomorrow = Calendar.getInstance();
    private Calendar twoDaysFromNow = Calendar.getInstance();

    @Before
    public void setUp() {
        tomorrow.add(Calendar.DATE, 1);

        twoDaysFromNow.add(Calendar.DATE, 2);

        yesterday.add(Calendar.DATE, -1);
    }

    @Test
    public void constructor_successLessThanAttempt_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Proficiency(yesterday, -32));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Proficiency(now, -1));
    }

    @Test
    public void isIncludedInCurrentQuiz() {
        assertFalse(new Proficiency(tomorrow, 31).isIncludedInCurrentQuiz());
        assertFalse(new Proficiency(tomorrow, 1).isIncludedInCurrentQuiz());

        assertTrue(new Proficiency().isIncludedInCurrentQuiz());
        assertTrue(new Proficiency(yesterday, 100).isIncludedInCurrentQuiz());
    }

    @Test
    public void quizAttempt() {
        Proficiency proficiency = new Proficiency(now, 1);
        Proficiency result = proficiency.quizAttempt(true);
        assertEquals(new Proficiency(twoDaysFromNow, 2), result);

        proficiency = new Proficiency(yesterday, 3);
        result = proficiency.quizAttempt(false);
        assertEquals(new Proficiency(), result);
    }


    @Test
    public void isValidProficiency() {
        // null proficiency
        Assert.assertThrows(NullPointerException.class, () -> Statistics.isValidStatistics(null));

        // blank proficiency
        assertFalse(Proficiency.isValidProficiency("")); // empty string
        assertFalse(Proficiency.isValidProficiency(" ")); // spaces only

        // missing parts
        assertFalse(Proficiency.isValidProficiency("inactive until 2 proficiency level"));
        assertFalse(Proficiency.isValidProficiency("inactive until 2 proficiency 3"));
        assertFalse(Proficiency.isValidProficiency("inactive until 2 level 3"));
        assertFalse(Proficiency.isValidProficiency("inactive until proficiency level 3"));
        assertFalse(Proficiency.isValidProficiency("inactive 2 proficiency level 3"));
        assertFalse(Proficiency.isValidProficiency("until 2 proficiency level 3"));

        // negative proficiency level
        assertFalse(Proficiency.isValidProficiency("inactive until 5 proficiency level -1"));

        // extra character(s)
        assertFalse(Proficiency.isValidProficiency("inactive until 2 proficiency level 3 "));
        assertFalse(Proficiency.isValidProficiency("inactive until 2 proficiency level 3  "));
        assertFalse(Proficiency.isValidProficiency("inactive until 2 proficiency level 3.  I am good at this."));

        // valid Proficiency
        assertTrue(Proficiency.isValidProficiency("inactive until 202 proficiency level 3"));
        assertTrue(Proficiency.isValidProficiency("inactive until 10230 proficiency level 100"));
        assertTrue(Proficiency.isValidProficiency("inactive until 202 proficiency level 30"));
        assertTrue(Proficiency.isValidProficiency("inactive until 12338 proficiency level 3392"));
    }

    @Test
    public void getQuizSrsStatus() {
        assertQuizStatus(-TimeUnit.MINUTES.toMillis(1), NOW_ACTIVE);

        assertQuizStatus(TimeUnit.SECONDS.toMillis(1), ACTIVE_IN_UNDER_A_MINUTE);

        assertQuizStatus(TimeUnit.MINUTES.toMillis(2), String.format(INACTIVE_UNTIL_IN_MINUTES, 2));
        assertQuizStatus(TimeUnit.MINUTES.toMillis(59), String.format(INACTIVE_UNTIL_IN_MINUTES, 59));

        assertQuizStatus(TimeUnit.HOURS.toMillis(1), String.format(INACTIVE_UNTIL_IN_HOURS, 1));
        assertQuizStatus(TimeUnit.HOURS.toMillis(23), String.format(INACTIVE_UNTIL_IN_HOURS, 23));

        assertQuizStatus(TimeUnit.DAYS.toMillis(1), String.format(INACTIVE_UNTIL_IN_DAYS, 1));
        assertQuizStatus(TimeUnit.DAYS.toMillis(20), String.format(INACTIVE_UNTIL_IN_DAYS, 20));

    }

    /**
     * Create a Proficiency that inactive until `millis` milli seconds from now. Test if the expected message is
     * printed.
     */
    private void assertQuizStatus(long millis, String expectedMessage) {
        millis += halfMinute;
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MILLISECOND, (int) millis);
        assertEquals(expectedMessage, new Proficiency(now, 0).getQuizSrsStatus());
    }
}
