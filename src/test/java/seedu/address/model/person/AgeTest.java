package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null age
        Assert.assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid ages
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only

        // valid ages
        assertTrue(Age.isValidAge("55"));
        assertTrue(Age.isValidAge("2")); // one digit
        assertTrue(Age.isValidAge("123")); // three digits
    }
}
