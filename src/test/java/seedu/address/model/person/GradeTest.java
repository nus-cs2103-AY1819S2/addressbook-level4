package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }

    @Test
    public void isValidGrade() {
        // null grade
        Assert.assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // invalid grades
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only
        assertFalse(Grade.isValidGrade("91")); // not 2 decimal places
        assertFalse(Grade.isValidGrade("grade")); // non-numeric
        assertFalse(Grade.isValidGrade("9011p041")); // alphabets within digits
        assertFalse(Grade.isValidGrade("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Grade.isValidGrade("4.11")); // exact format with 2 decimal places
    }
}
