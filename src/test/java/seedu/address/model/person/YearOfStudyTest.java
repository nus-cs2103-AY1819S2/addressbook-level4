package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class YearOfStudyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new YearOfStudy(null));
    }

    @Test
    public void constructor_invalidyearOfStudy_throwsIllegalArgumentException() {
        String invalidYearOfStudy = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new YearOfStudy(invalidYearOfStudy));
    }

    @Test
    public void isValidYearOfStudy() {
        // null yearOfStudy
        Assert.assertThrows(NullPointerException.class, () -> YearOfStudy.isValidYearOfStudy(null));

        // invalid yearOfStudy
        assertFalse(YearOfStudy.isValidYearOfStudy("")); // empty string
        assertFalse(YearOfStudy.isValidYearOfStudy(" ")); // spaces only
        assertFalse(YearOfStudy.isValidYearOfStudy("0")); // less than 1
        assertFalse(YearOfStudy.isValidYearOfStudy("year")); // non-numeric
        assertFalse(YearOfStudy.isValidYearOfStudy("7")); // more than 6
        assertFalse(YearOfStudy.isValidYearOfStudy("y1")); // alphanumeric

        // valid yearOfStudy
        assertTrue(YearOfStudy.isValidYearOfStudy("2"));
    }
}
