package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class InterviewScoresTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new InterviewScores(null));
    }

    @Test
    public void constructor_invalidInterviewScores_throwsIllegalArgumentException() {
        String invalidInterviewScores = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new InterviewScores(invalidInterviewScores));
    }

    @Test
    public void isValidInterviewScores() {
        // null interview scores
        Assert.assertThrows(NullPointerException.class, () -> InterviewScores.isValidInterviewScores(null));

        // invalid interview scores
        assertFalse(InterviewScores.isValidInterviewScores("")); // empty string
        assertFalse(InterviewScores.isValidInterviewScores(" ")); // spaces only
        assertFalse(InterviewScores.isValidInterviewScores("grade")); // non-numeric
        assertFalse(InterviewScores.isValidInterviewScores("9011p041")); // alphabets within digits
        assertFalse(InterviewScores.isValidInterviewScores("9312 1534")); // spaces within digits
        assertFalse(InterviewScores.isValidInterviewScores("5,3,10,3")); // incorrect number of sets of numbers

        // valid interview score
        assertTrue(InterviewScores.isValidInterviewScores("4,1,2,2,1")); // exact format
    }
}
