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
    public void constructor_invalidYear_throwsNumberFormatException() {
        String emptyString = ""; // empty string
        Assert.assertThrows(NumberFormatException.class, () -> new Year(emptyString));
        String spaces = "   "; // spaces only
        Assert.assertThrows(NumberFormatException.class, () -> new Year(spaces));
        String nonNumeric = "%876"; // contain nonNumeric characters
        Assert.assertThrows(NumberFormatException.class, () -> new Year(nonNumeric));
        String alphabets = "pear"; // contain alphabets
        Assert.assertThrows(NumberFormatException.class, () -> new Year(alphabets));
    }

    @Test
    public void isValidYear() {
        // null year
        Assert.assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid year
        assertFalse(Year.isValidYear("0")); // less than 1
        assertFalse(Year.isValidYear("01234")); // more than 4 digits
        assertFalse(Year.isValidYear("2020")); // More than current year

        // valid year
        assertTrue(Year.isValidYear("1")); // 1 digit, years of the past
        assertTrue(Year.isValidYear("10")); // 2 digits, years of the past
        assertTrue(Year.isValidYear("907")); // 3 digits, years of the past
        assertTrue(Year.isValidYear("1998")); // 4 digits, years of the past
        assertTrue(Year.isValidYear("2019")); // Current Year
    }

    @Test
    public void isLeapYear() {
        // null year
        Assert.assertThrows(NullPointerException.class, () -> Year.isLeapYear(null));

        // invallid leap year
        assertFalse(Year.isLeapYear("2019"));
        assertFalse(Year.isLeapYear("2018"));
        assertFalse(Year.isLeapYear("2017"));

        // valid leap year
        assertTrue(Year.isLeapYear("2016"));
        assertTrue(Year.isLeapYear("2020"));
        assertTrue(Year.isLeapYear("2012"));
    }

    @Test
    public void equal() {

        // not equal
        assertFalse(new Year("2019").equals(new Year("2018")));
        assertFalse(new Year("139").equals(new Year("199")));
        assertFalse(new Year("76").equals(new Year("16")));
        assertFalse(new Year("6").equals(new Year("1")));

        // equal
        assertTrue(new Year("2019").equals(new Year("2019")));
        assertTrue(new Year("201").equals(new Year("201")));
        assertTrue(new Year("20").equals(new Year("20")));
        assertTrue(new Year("2").equals(new Year("2")));
        assertTrue(new Year("0020").equals(new Year("20")));
        assertTrue(new Year("02").equals(new Year("2")));
    }
}
