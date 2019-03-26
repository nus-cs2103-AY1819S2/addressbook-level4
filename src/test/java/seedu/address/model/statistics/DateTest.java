package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsNumberFormatException() {
        String emptyString = ""; // empty string
        Assert.assertThrows(NumberFormatException.class, () -> new Date(emptyString));
        String spaces = " "; // spaces only
        Assert.assertThrows(NumberFormatException.class, () -> new Date(spaces));
        String nonNumeric = "%3"; // contain nonNumeric characters
        Assert.assertThrows(NumberFormatException.class, () -> new Date(nonNumeric));
        String alphabets = "pear"; // contain alphabets
        Assert.assertThrows(NumberFormatException.class, () -> new Date(alphabets));
    }

    @Test
    public void isValidDate() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // valid name
        assertTrue(Date.isValidDate("28.02.2019", new Day("28"), new Month("02"), new Year("2019")));
        assertTrue(Date.isValidDate("29.02.2016", new Day("29"), new Month("02"), new Year("2016")));
        assertTrue(Date.isValidDate("31.01.2019", new Day("31"), new Month("01"), new Year("2019")));
        assertTrue(Date.isValidDate("30.11.2018", new Day("30"), new Month("11"), new Year("2018")));

        // invalid name
        assertFalse(Date.isValidDate("29.02.2019", new Day("29"), new Month("02"), new Year("2019"))); //non leap
        assertFalse(Date.isValidDate("30.02.2016", new Day("30"), new Month("02"), new Year("2016"))); //leap year
        assertFalse(Date.isValidDate("31.11.2018", new Day("31"), new Month("11"), new Year("2018")));
    }
}
