package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MonthTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Month(null));
    }

    @Test
    public void constructor_invalidMonth_throwsIllegalArgumentException() {
        String invalidMonth = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Month(invalidMonth));
    }

    @Test
    public void isValidMonth() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Month.isValidMonth(null));

        // invalid name
        assertFalse(Month.isValidMonth("")); // empty string
        assertFalse(Month.isValidMonth(" ")); // spaces only
        assertFalse(Month.isValidMonth("^")); // only numeric characters
        assertFalse(Month.isValidMonth("pe")); // contains non-numeric characters
        assertFalse(Month.isValidMonth("00")); // less than 1
        assertFalse(Month.isValidMonth("13")); // more than 12
        assertFalse(Month.isValidMonth("012")); // More than 2 digits

        // valid name
        assertTrue(Month.isValidMonth("01")); // Double digit number 1
        assertTrue(Month.isValidMonth("7")); // Single digit more than 1
        assertTrue(Month.isValidMonth("12")); // Number 12
        assertTrue(Month.isValidMonth("10")); // Double digits less than 12
    }
}
