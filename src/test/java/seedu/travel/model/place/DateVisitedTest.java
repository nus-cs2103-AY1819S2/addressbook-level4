package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import seedu.travel.testutil.Assert;

public class DateVisitedTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateVisited(null));
    }

    @Test
    public void constructor_invalidDateVisited_throwsIllegalArgumentException() {
        String invalidDateVisited = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateVisited(invalidDateVisited));
    }

    @Test
    public void isValidDateVisited() {
        // null date visited
        Assert.assertThrows(NullPointerException.class, () -> DateVisited.isValidDateVisited(null));
        Assert.assertThrows(NullPointerException.class, () -> DateVisited.doesDateExist(null));
        Assert.assertThrows(NullPointerException.class, () -> DateVisited.isCorrectDateFormat(null));

        // incorrect date visited format
        assertFalse(DateVisited.isCorrectDateFormat("")); // empty string
        assertFalse(DateVisited.isCorrectDateFormat(" ")); // spaces only
        assertFalse(DateVisited.isCorrectDateFormat("  ")); // spaces only
        assertFalse(DateVisited.isCorrectDateFormat("10")); // only 2 numbers
        assertFalse(DateVisited.isCorrectDateFormat("12/13")); // 4 numbers only
        assertFalse(DateVisited.isCorrectDateFormat("p")); // only 1 letters
        assertFalse(DateVisited.isCorrectDateFormat("10-10-2000")); // not correct format date
        assertFalse(DateVisited.isCorrectDateFormat("10/17/2000")); // not correct format date
        assertFalse(DateVisited.isCorrectDateFormat("99/08/2000")); // not correct format date


        // date that does not exist
        assertFalse(DateVisited.doesDateExist("29/02/2017")); // Not a leap year
        assertFalse(DateVisited.doesDateExist("30/02/2018")); // Not a leap year
        assertFalse(DateVisited.doesDateExist("31/02/2019")); // Not a leap year
        assertFalse(DateVisited.doesDateExist("30/02/2016")); // Leap year
        assertFalse(DateVisited.doesDateExist("31/02/2016")); // Leap year
        assertFalse(DateVisited.doesDateExist("31/04/2018"));
        assertFalse(DateVisited.doesDateExist("31/06/2018"));
        assertFalse(DateVisited.doesDateExist("31/09/2018"));
        assertFalse(DateVisited.doesDateExist("31/11/2018"));

        // invalid future date visited added
        assertFalse(DateVisited.isValidDateVisited("")); // empty string
        assertFalse(DateVisited.isValidDateVisited(" ")); // spaces only
        assertFalse(DateVisited.isValidDateVisited("  ")); // spaces only
        assertFalse(DateVisited.isValidDateVisited("10/10/2999")); // future date
        assertFalse(DateVisited.isValidDateVisited("10/10/2050")); // future date

        // correct date visited format
        assertTrue(DateVisited.isCorrectDateFormat("10/10/2001"));
        assertTrue(DateVisited.isCorrectDateFormat("01/01/2015"));

        // date that exists
        assertTrue(DateVisited.doesDateExist("29/02/2016")); // Leap year
        assertTrue(DateVisited.doesDateExist("28/02/2018"));
        assertTrue(DateVisited.doesDateExist("31/12/2018"));

        // valid date visited
        assertTrue(DateVisited.isValidDateVisited("10/10/2009"));
        assertTrue(DateVisited.isValidDateVisited("01/01/2018"));
    }

    @Test
    public void isValidYearVisited() {
        // null year visited
        Assert.assertThrows(NullPointerException.class, () -> DateVisited.isValidYear(null));

        // invalid year visited
        assertFalse(DateVisited.isValidYear("")); // empty string
        assertFalse(DateVisited.isValidYear(" ")); // spaces only
        assertFalse(DateVisited.isValidYear("invalidyear")); // non-integer input
        assertFalse(DateVisited.isValidYear("1819")); // date before 1900
        assertFalse(DateVisited.isValidYear(String.valueOf(LocalDateTime.now().getYear() + 1))); // future date

        // valid year visited
        assertTrue(DateVisited.isValidYear("1900")); // lower boundary case
        assertTrue(DateVisited.isValidYear("2001")); // middle case
        assertTrue(DateVisited.isValidYear(String.valueOf(LocalDateTime.now().getYear()))); // upper boundary case
    }
}
