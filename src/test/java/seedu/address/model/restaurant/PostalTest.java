package seedu.address.model.restaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PostalTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Postal(null));
    }

    @Test
    public void constructor_invalidPostal_throwsIllegalArgumentException() {
        String postal = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Postal(postal));
    }

    @Test
    public void isValidPostal() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Postal.isValidPostal(null));

        // invalid name
        assertFalse(Postal.isValidPostal("")); // empty string
        assertFalse(Postal.isValidPostal(" ")); // spaces only
        assertFalse(Postal.isValidPostal("1293fa")); // contains non numeric characters
        assertFalse(Postal.isValidPostal("1234567")); //contains more than 6 numbers
        assertFalse(Postal.isValidPostal("12567")); //contains less than 6 numbers
        // valid name
        assertTrue(Postal.isValidPostal("123456")); // numbers only
        assertTrue(Postal.isValidPostal("999999")); // edge case
        assertTrue(Postal.isValidPostal("000000")); // edge case

    }
}
