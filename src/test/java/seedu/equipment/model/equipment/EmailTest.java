package seedu.equipment.model.equipment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.equipment.testutil.Assert;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string

        // invalid email
        assertFalse(Email.isValidEmail("^")); // only non-alphanumeric characters
        assertFalse(Email.isValidEmail("22/09/2019")); // contains non-alphanumeric characters

        // valid email --> not whhat we auctally want
        assertTrue(Email.isValidEmail("peter jack")); // alphabets only
        assertTrue(Email.isValidEmail("12345")); // numbers only
        assertTrue(Email.isValidEmail("peter the 2nd")); // alphanumeric characters
        assertTrue(Email.isValidEmail("Capital Tan")); // with capital letters
        assertTrue(Email.isValidEmail("David Roger Jackson Ray Jr 2nd")); // long names

        // valid email --> in proper date format
        assertTrue(Email.isValidEmail("22 April 2019")); // no ambiguousness in dates
    }
}
