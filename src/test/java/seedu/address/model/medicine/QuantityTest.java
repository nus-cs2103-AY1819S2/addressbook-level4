package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // invalid quantities
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("quantity")); // non-numeric
        assertFalse(Quantity.isValidQuantity("9011p041")); // alphabets within digits
        assertFalse(Quantity.isValidQuantity("9312 1534")); // spaces within digits
        assertFalse(Quantity.isValidQuantity("-19282")); // negative numbers
        assertFalse(Quantity.isValidQuantity("124293842033123")); // long quantities
        assertFalse(Quantity.isValidQuantity(Integer.toString(Quantity.MAX_QUANTITY + 1))); // max quantity exceeded

        // valid quantities
        assertTrue(Quantity.isValidQuantity("1")); // 1 number
        assertTrue(Quantity.isValidQuantity("911")); // exactly 3 numbers
        assertTrue(Quantity.isValidQuantity(Integer.toString(Quantity.MAX_QUANTITY)));
    }
}
