package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidMajor = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Major(invalidMajor));
    }

    @Test
    public void isValidMajor() {
        // null major
        Assert.assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid majors
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only
        assertFalse(Major.isValidMajor("^^")); // symbols
        assertFalse(Major.isValidMajor(" phone999")); // space before


        // valid majors
        assertTrue(Major.isValidMajor("CS")); // short form
        assertTrue(Major.isValidMajor("Computer Science")); //formal form
        assertTrue(Major.isValidMajor("ME")); //others
        assertTrue(Major.isValidMajor("MATH")); //others
    }
}
