package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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

        // invalid nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("12345678")); // only numeric characters
        assertFalse(Nric.isValidNric("S123456Z")); // only 6 numbers
        assertFalse(Nric.isValidNric("S1234567")); // No ending alphabet
        assertFalse(Nric.isValidNric("1234567Z")); // No S/T prefix
        assertFalse(Nric.isValidNric("S12345678Z")); // Longer than 9 characters
        assertFalse(Nric.isValidNric("A1234567Z")); // Prefix not S/T

        // valid nric
        assertTrue(Nric.isValidNric("S9876543Z")); // NRIC starting with S
        assertTrue(Nric.isValidNric("T1234567A")); // NRIC starting with T
    }

    @Test
    public void equals() {
        Nric nric = new Nric("S1234567A");

        // same object
        assertEquals(nric, nric);

        // same values
        assertEquals(nric, new Nric("S1234567A"));

        // different values
        assertNotEquals(nric, new Nric("S9876543F"));

        // different type
        assertNotEquals(nric, 5);

        // null
        assertNotEquals(nric, null);
    }

    @Test
    public void contains() {
        Nric nric = new Nric("S1234567A");

        // same string
        assertTrue(nric.contains("S1234567A"));

        // case insensitive, same string
        assertTrue(nric.contains("s1234567a"));

        // case insensitive, substring
        assertTrue(nric.contains("s12345"));

        // different substring
        assertFalse(nric.contains("t12830"));
    }
}
