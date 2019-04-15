package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidDay_throwsNumberFormatException() {
        String emptyString = ""; // empty string
        Assert.assertThrows(NumberFormatException.class, () -> new Day(emptyString));
        String spaces = " "; // spaces only
        Assert.assertThrows(NumberFormatException.class, () -> new Day(spaces));
        String nonNumeric = "%3"; // contain nonNumeric characters
        Assert.assertThrows(NumberFormatException.class, () -> new Day(nonNumeric));
        String alphabets = "pear"; // contain alphabets
        Assert.assertThrows(NumberFormatException.class, () -> new Day(alphabets));
    }

    @Test
    public void isValidDay() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid name
        assertFalse(Day.isValidDay("00")); // less than 1
        assertFalse(Day.isValidDay("32")); // more than 31
        assertFalse(Day.isValidDay("034")); // More than 2 digits

        // valid name
        assertTrue(Day.isValidDay("01")); // Double digit number 1
        assertTrue(Day.isValidDay("7")); // Single digit more than 1
        assertTrue(Day.isValidDay("31")); // Number 31
        assertTrue(Day.isValidDay("20")); // Double digits less than 31
    }
}
