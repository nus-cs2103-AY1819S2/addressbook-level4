package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.doctor.Year;
import seedu.address.testutil.Assert;

public class YearTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Year(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidYear = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Year(invalidYear));
    }

    @Test
    public void isValidYear() {
        // null years of experience
        Assert.assertThrows(NullPointerException.class, () -> Year.isValidYear(null));

        // invalid years of experience
        assertFalse(Year.isValidYear("")); // empty string
        assertFalse(Year.isValidYear(" ")); // spaces only
        assertFalse(Year.isValidYear("151"));

        // valid years of experience
        assertTrue(Year.isValidYear("0"));
        assertTrue(Year.isValidYear("55"));
        assertTrue(Year.isValidYear("2")); // one digit
        assertTrue(Year.isValidYear("100")); // three digits
    }
}
