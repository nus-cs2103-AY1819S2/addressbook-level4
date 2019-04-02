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
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank date
        assertFalse(Date.isValidDate("")); // empty string

        // invalid date
        assertFalse(Date.isValidDate("^")); // only non-alphanumeric characters
        assertFalse(Date.isValidDate("22/09/2019")); // contains non-alphanumeric characters
        assertFalse(Date.isValidDate("2222/11/2019"));

        // valid date --> not whhat we auctally want
        assertFalse(Date.isValidDate("peter jack")); // alphabets only
        assertFalse(Date.isValidDate("12345")); // numbers only
        assertFalse(Date.isValidDate("peter the 2nd")); // alphanumeric characters
        assertFalse(Date.isValidDate("Capital Tan")); // with capital letters
        assertFalse(Date.isValidDate("David Roger Jackson Ray Jr 2nd")); // long names

        // valid date --> in proper date format
        assertTrue(Date.isValidDate("22 April 2019")); // no ambiguousness in dates
    }
}
