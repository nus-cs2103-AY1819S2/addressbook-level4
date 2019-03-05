package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NricTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Nric
                .isValidNric(null));

        // invalid name
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("12345678")); // only numeric characters
        assertFalse(Nric.isValidNric("S123456Z")); // only 6 numbers
        assertFalse(Nric.isValidNric("S1234567")); // No ending alphabet
        assertFalse(Nric.isValidNric("1234567Z")); // No S/T prefix
        assertFalse(Nric.isValidNric("S12345678Z")); // Longer than 9 characters
        assertFalse(Nric.isValidNric("A1234567Z")); // Prefix not S/T

        // valid name
        assertTrue(Nric.isValidNric("S9876543Z")); // NRIC starting with S
        assertTrue(Nric.isValidNric("T1234567A")); // NRIC starting with T
    }

    @Test
    public void equals() {

    }
}
