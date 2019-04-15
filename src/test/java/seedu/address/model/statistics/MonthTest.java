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
    public void constructor_invalidMonth_throwsNumberFormatException() {
        String emptyString = ""; // empty string
        Assert.assertThrows(NumberFormatException.class, () -> new Month(emptyString));
        String spaces = " "; // spaces only
        Assert.assertThrows(NumberFormatException.class, () -> new Month(spaces));
        String nonNumeric = "%3"; // contain nonNumeric characters
        Assert.assertThrows(NumberFormatException.class, () -> new Month(nonNumeric));
        String alphabets = "pear"; // contain alphabets
        Assert.assertThrows(NumberFormatException.class, () -> new Month(alphabets));
    }

    @Test
    public void isValidMonth() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Month.isValidMonth(null));

        // invalid name
        assertFalse(Month.isValidMonth("00")); // less than 1
        assertFalse(Month.isValidMonth("13")); // more than 12
        assertFalse(Month.isValidMonth("012")); // More than 2 digits

        // valid name
        assertTrue(Month.isValidMonth("01")); // Double digit number 1
        assertTrue(Month.isValidMonth("1")); // Single digit more than 1
        assertTrue(Month.isValidMonth("12")); // Number 12
        assertTrue(Month.isValidMonth("10")); // Double digits less than 12
    }

    @Test
    public void equal() {

        // not equal
        assertFalse(new Month("12").equals(new Month("11")));
        assertFalse(new Month("7").equals(new Month("5")));
        assertFalse(new Month("06").equals(new Month("05")));

        // equal
        assertTrue(new Month("12").equals(new Month("12")));
        assertTrue(new Month("05").equals(new Month("5")));
    }
}
