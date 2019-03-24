package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertFalse(DateVisited.isCorrectDateFormat("10/10/0000")); // not correct format date

        // invalid future date visited added
        assertFalse(DateVisited.isValidDateVisited("")); // empty string
        assertFalse(DateVisited.isValidDateVisited(" ")); // spaces only
        assertFalse(DateVisited.isValidDateVisited("  ")); // spaces only
        assertFalse(DateVisited.isValidDateVisited("10/10/2999")); // future date

        // correct date visited format
        assertTrue(DateVisited.isCorrectDateFormat("10/10/2001"));
        assertTrue(DateVisited.isCorrectDateFormat("01/01/2015"));

        // valid date visited
        assertTrue(DateVisited.isValidDateVisited("10/10/2009"));
        assertTrue(DateVisited.isValidDateVisited("01/01/2018"));
    }
}
