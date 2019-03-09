package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.Grade;
import seedu.address.testutil.Assert;

public class ExpectedMinGradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Grade.valueOf(null));
    }

    @Test
    public void constructor_invalidExpectedMinGradeWithWhitespace_throwsIllegalArgumentException() {
        String invalidExpectedMinGradeWithWhitespace = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> Grade.valueOf(""));
    }

    @Test
    public void isValidExpectedMinGradeWithWhitespace() {
        // null grade
        Assert.assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // blank grade
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only

        // invalid parts
        assertFalse(Grade.isValidGrade("@"));
        assertFalse(Grade.isValidGrade("d"));
        assertFalse(Grade.isValidGrade("3"));
        assertFalse(Grade.isValidGrade("12"));
        assertFalse(Grade.isValidGrade("G3"));
        assertFalse(Grade.isValidGrade("AB"));
        assertFalse(Grade.isValidGrade("A++"));

        // valid email
        assertTrue(Grade.isValidGrade("F"));
        assertTrue(Grade.isValidGrade("D"));
        assertTrue(Grade.isValidGrade("C"));
        assertTrue(Grade.isValidGrade("B"));
        assertTrue(Grade.isValidGrade("A"));
    }
}
