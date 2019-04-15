package seedu.address.model.moduletaken;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class HourTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Hour(null));
    }

    @Test
    public void constructor_invalidHour_throwsIllegalArgumentException() {
        String invalidHour = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Hour(invalidHour));
    }

    @Test
    public void isValidHour() {
        // null hour
        Assert.assertThrows(NullPointerException.class, () -> Hour.isValidHour(null));

        // invalid hour
        assertFalse(Hour.isValidHour("")); // empty string
        assertFalse(Hour.isValidHour(" ")); // spaces only
        assertFalse(Hour.isValidHour("f")); // contains non numbers
        assertFalse(Hour.isValidHour("-4")); // negative numbers
        assertFalse(Hour.isValidHour("4.33")); // more than 1 decimal places
        assertFalse(Hour.isValidHour("4.3")); // not .5 or .0
        assertFalse(Hour.isValidHour("1000.33")); // more than 3 leading digits

        // valid hour
        assertTrue(Hour.isValidHour("0"));
        assertTrue(Hour.isValidHour("0.5"));
        assertTrue(Hour.isValidHour("2"));
        assertTrue(Hour.isValidHour("2.5"));
        assertTrue(Hour.isValidHour("999"));
        assertTrue(Hour.isValidHour("999.5"));
        assertTrue(Hour.isValidHour("00"));
        assertTrue(Hour.isValidHour("00.5"));
        assertTrue(Hour.isValidHour("20.0")); // .0
        assertTrue(Hour.isValidHour("0.0"));
    }
}
