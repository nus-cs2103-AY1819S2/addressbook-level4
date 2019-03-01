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
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid phone numbers
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only
        assertFalse(Major.isValidMajor("91")); // numeric
        assertFalse(Major.isValidMajor("phone")); // not in list


        // valid phone numbers
        assertTrue(Major.isValidMajor("CS")); // short form
        assertTrue(Major.isValidMajor("Computer Science")); //formal form
    }
}
