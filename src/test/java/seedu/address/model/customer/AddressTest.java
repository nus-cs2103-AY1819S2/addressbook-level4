package seedu.hms.model.customer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.testutil.Assert;

public class hmsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new hms(null));
    }

    @Test
    public void constructor_invalidhms_throwsIllegalArgumentException() {
        String invalidhms = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new hms(invalidhms));
    }

    @Test
    public void isValidhms() {
        // null hms
        Assert.assertThrows(NullPointerException.class, () -> hms.isValidhms(null));

        // invalid hmses
        assertFalse(hms.isValidhms("")); // empty string
        assertFalse(hms.isValidhms(" ")); // spaces only

        // valid hmses
        assertTrue(hms.isValidhms("Blk 456, Den Road, #01-355"));
        assertTrue(hms.isValidhms("-")); // one character
        assertTrue(hms.isValidhms("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long hms
    }
}
