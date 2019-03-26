package seedu.equipment.model.equipment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.equipment.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null email
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank email
        assertFalse(Date.isValidDate("")); // empty string

        // invalid email
        assertFalse(Date.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(Date.isValidDate("22/09/2019")); // contains non-alphanumeric characters

        // valid email --> not whhat we auctally want
        assertTrue(Date.isValidDate("peter jack")); // alphabets only
        assertTrue(Date.isValidDate("12345")); // numbers only
        assertTrue(Date.isValidDate("peter the 2nd")); // alphanumeric characters
        assertTrue(Date.isValidDate("Capital Tan")); // with capital letters
        assertTrue(Date.isValidDate("David Roger Jackson Ray Jr 2nd")); // long names

        // valid email --> in proper date format
        assertTrue(Date.isValidDate("22 April 2019")); // no ambiguousness in dates
    }
}
