package seedu.address.model.flashcard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatisticsTest {

    @Test
    public void constructor_successLessThanAttempt_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Statistics(10, 9));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Statistics(1, 0));
    }

    @Test
    public void getSuccessRate() {
        double epsilon = 1e-9;
        assertEquals((double) 17 / 31, new Statistics(17, 31).getSuccessRate(), epsilon);
        assertEquals((double) 11 / 50, new Statistics(11, 50).getSuccessRate(), epsilon);

        assertEquals(0, new Statistics(0, 0).getSuccessRate(), epsilon);
        assertEquals(0, new Statistics().getSuccessRate(), epsilon);
    }

    @Test
    public void quizAttempt() {
        Statistics stats = new Statistics(1, 3);

        Statistics result = stats.quizAttempt(true);
        assertEquals(new Statistics(2, 4), result);
        assertEquals(new Statistics(1, 3), stats);

        result = stats.quizAttempt(false);
        assertEquals(new Statistics(1, 4), result);
        assertEquals(new Statistics(1, 3), stats);
    }

    @Test
    public void merge() {
        Statistics stats1 = new Statistics(11, 30);
        Statistics stats2 = new Statistics(101, 301);
        Statistics stats3 = new Statistics();

        assertEquals(new Statistics(101 + 11, 301 + 30), stats1.merge(stats2));
        assertEquals(new Statistics(101, 301), stats2.merge(stats3));
        assertEquals(new Statistics(11, 30), stats1.merge(stats3));
    }

    @Test
    public void isValidStatistics() {
        // null statistics
        Assert.assertThrows(NullPointerException.class, () -> Statistics.isValidStatistics(null));

        // blank statistics
        assertFalse(Statistics.isValidStatistics("")); // empty string
        assertFalse(Statistics.isValidStatistics(" ")); // spaces only

        // missing parts
        assertFalse(Statistics.isValidStatistics("2 success out 3 attempts."));
        assertFalse(Statistics.isValidStatistics("2 success of 3 attempts."));
        assertFalse(Statistics.isValidStatistics("2 out of 3 attempt."));
        assertFalse(Statistics.isValidStatistics("out of 3 attempts."));
        assertFalse(Statistics.isValidStatistics("out of attempts."));
        assertFalse(Statistics.isValidStatistics("2 success out of 3 attempt."));
        assertFalse(Statistics.isValidStatistics("2 success out of 3 attempts"));
        assertFalse(Statistics.isValidStatistics("2 success out of 3 "));

        // success greater than attempt
        assertFalse(Statistics.isValidStatistics("5 success out of 3 attempts."));

        // extra character(s)
        assertFalse(Statistics.isValidStatistics("2 success out of 3 attempts. "));
        assertFalse(Statistics.isValidStatistics("2 success out of 3 attempts.   "));
        assertFalse(Statistics.isValidStatistics("3 success out of 3 attempts. I am good at this."));

        // valid Statistics
        assertTrue(Statistics.isValidStatistics("2 out of 3 attempts."));
        assertTrue(Statistics.isValidStatistics("100 out of 100 attempts."));
        assertTrue(Statistics.isValidStatistics("20 out of 30 attempts."));
        assertTrue(Statistics.isValidStatistics("1238 out of 3392 attempts."));
    }
}
