package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.threshold.Threshold;
import seedu.address.testutil.Assert;

public class ThresholdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Threshold(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidThreshold = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Threshold(invalidThreshold));
    }

    @Test
    public void isValidThreshold() {
        // invalid thresholds
        assertFalse(Threshold.isValidThreshold("")); // empty string
        assertFalse(Threshold.isValidThreshold(" ")); // spaces only
        assertFalse(Threshold.isValidThreshold("threshold")); // non-numeric
        assertFalse(Threshold.isValidThreshold("9011p041")); // alphabets within digits
        assertFalse(Threshold.isValidThreshold("9312 1534")); // spaces within digits
        assertFalse(Threshold.isValidThreshold("-19282")); // negative numbers
        assertFalse(Threshold.isValidThreshold("124293842033123")); // long quantities
        assertFalse(Threshold.isValidThreshold(Integer.toString(Threshold.MIN_THRESHOLD - 1))); // less than min

        // valid thresholds
        assertTrue(Threshold.isValidThreshold("1")); // 1 number
        assertTrue(Threshold.isValidThreshold("911")); // exactly 3 numbers
        assertTrue(Threshold.isValidThreshold("1000000000"));

    }
}
