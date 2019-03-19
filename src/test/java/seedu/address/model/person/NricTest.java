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
        // null Nric
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid Nric
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("9142345")); // no 'S' at the start and no alphabet at end
        assertFalse(Nric.isValidNric("grade")); // non-numeric
        assertFalse(Nric.isValidNric("9011p041")); // alphabets within digits
        assertFalse(Nric.isValidNric("9312 1534")); // spaces within digits
        assertFalse(Nric.isValidNric("S923H")); // incorrect number of digits

        // valid Nric
        assertTrue(Nric.isValidNric("S1234451Y")); // exact format
    }
}
