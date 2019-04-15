package seedu.address.model.threshold;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.testutil.Assert;

public class ThresholdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new Threshold(null, WarningPanelPredicateType.EXPIRY));
        Assert.assertThrows(NullPointerException.class, ()
            -> new Threshold(null, WarningPanelPredicateType.LOW_STOCK));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidThreshold = "";
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new Threshold(invalidThreshold, WarningPanelPredicateType.EXPIRY));
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new Threshold(invalidThreshold, WarningPanelPredicateType.LOW_STOCK));
    }

    @Test
    public void isValidThreshold_false() {
        // empty string
        assertFalse(Threshold.isValidThreshold("", WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold("", WarningPanelPredicateType.LOW_STOCK));

        // spaces only
        assertFalse(Threshold.isValidThreshold(" ", WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold(" ", WarningPanelPredicateType.LOW_STOCK));

        // non-numeric
        assertFalse(Threshold.isValidThreshold("threshold", WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold("threshold", WarningPanelPredicateType.LOW_STOCK));

        // alphabets within digits
        assertFalse(Threshold.isValidThreshold("9011p041", WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold("9011p041", WarningPanelPredicateType.LOW_STOCK));

        // spaces within digits
        assertFalse(Threshold.isValidThreshold("9312 1534", WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold("9312 1534", WarningPanelPredicateType.LOW_STOCK));

        // negative numbers
        assertFalse(Threshold.isValidThreshold("-19282", WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold("-19282", WarningPanelPredicateType.LOW_STOCK));

        // less than min
        assertFalse(Threshold.isValidThreshold(Integer.toString(Threshold.MIN_THRESHOLD - 1),
                WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold(Integer.toString(Threshold.MIN_THRESHOLD - 1),
                WarningPanelPredicateType.LOW_STOCK));

        // more than max
        assertFalse(Threshold.isValidThreshold(Integer.toString(Threshold.MAX_EXPIRY_THRESHOLD + 1),
                WarningPanelPredicateType.EXPIRY));
        assertFalse(Threshold.isValidThreshold(Integer.toString(Threshold.MAX_QUANTITY_THRESHOLD + 1),
                WarningPanelPredicateType.LOW_STOCK));

    }

    @Test
    public void isValidThreshold_true() {
        // 1 number
        assertTrue(Threshold.isValidThreshold("1", WarningPanelPredicateType.EXPIRY));
        assertTrue(Threshold.isValidThreshold("1", WarningPanelPredicateType.LOW_STOCK));

        // exactly 3 numbers
        assertTrue(Threshold.isValidThreshold("911", WarningPanelPredicateType.EXPIRY));
        assertTrue(Threshold.isValidThreshold("911", WarningPanelPredicateType.LOW_STOCK));

        // max threshold
        assertTrue(Threshold.isValidThreshold(Integer.toString(Threshold.MAX_EXPIRY_THRESHOLD),
                WarningPanelPredicateType.EXPIRY));
        assertTrue(Threshold.isValidThreshold(Integer.toString(Threshold.MAX_QUANTITY_THRESHOLD),
                WarningPanelPredicateType.LOW_STOCK));
    }
}
