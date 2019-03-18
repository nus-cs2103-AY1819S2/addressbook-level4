package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidYear_throwsIllegalArgumentException() {
        String invalidYear = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void isValidYear() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid name
        assertFalse(Year.isValidYear("")); // empty string
        assertFalse(Year.isValidYear(" ")); // spaces only
        assertFalse(Year.isValidYear("^")); // only numeric characters
        assertFalse(Year.isValidYear("pear")); // contains non-numeric characters
        assertFalse(Year.isValidYear("00")); // less than 1
        assertFalse(Year.isValidYear("01234")); // more than 4 digits
        assertFalse(Year.isValidYear("2020")); // More than current year

        // valid name
        assertTrue(Year.isValidYear("907")); // less than 3 digits and the current year
        assertTrue(Year.isValidYear("1998")); // Years in the past
        assertTrue(Year.isValidYear("2019")); // Current Year
    }
}
