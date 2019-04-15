package seedu.equipment.model.equipment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.equipment.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String test = null;
        Assert.assertThrows(NullPointerException.class, () -> new Date(test));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "31-00-93";
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
        assertFalse(Date.isValidDate("32 February 2019"));
        assertFalse(Date.isValidDate("32 02 2019"));

        // valid date --> not what we want
        assertFalse(Date.isValidDate("peter jack")); // alphabets only
        assertFalse(Date.isValidDate("12345")); // numbers only
        assertFalse(Date.isValidDate("peter the 2nd")); // alphanumeric characters
        assertFalse(Date.isValidDate("Capital Tan")); // with capital letters
        assertFalse(Date.isValidDate("David Roger Jackson Ray Jr 2nd")); // long names
        assertFalse(Date.isValidDate("22 April 2019")); // long names
        assertFalse(Date.isValidDate("32-03-2019")); // does not exist

        // valid date --> in proper date format
        assertTrue(Date.isValidDate("22-01-2019"));
        assertTrue(Date.isValidDate("12-1-2009"));
    }
}
