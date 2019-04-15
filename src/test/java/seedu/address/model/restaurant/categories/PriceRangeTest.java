package seedu.address.model.restaurant.categories;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceRangeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PriceRange(null));
    }

    @Test
    public void constructor_invalidPriceRange_throwsIllegalArgumentException() {
        String invalidPriceRange = "$$$$$$";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PriceRange(invalidPriceRange));
    }

    @Test
    public void isValidPriceRange() {
        // null priceRange
        Assert.assertThrows(NullPointerException.class, () -> PriceRange.isValidPriceRange(null));

        // invalid priceRange
        assertFalse(PriceRange.isValidPriceRange("")); // empty string
        assertFalse(PriceRange.isValidPriceRange(" ")); // spaces
        assertFalse(PriceRange.isValidPriceRange("^")); // other characters other than $
        assertFalse(PriceRange.isValidPriceRange("expensive")); // alphabets
        assertFalse(PriceRange.isValidPriceRange("$$$$$$$$")); // longer than 5 $
        assertFalse(PriceRange.isValidPriceRange("$$$ $$$")); // spaces between not allowed

        // valid priceRange
        assertTrue(PriceRange.isValidPriceRange("$"));
        assertTrue(PriceRange.isValidPriceRange("$$"));
        assertTrue(PriceRange.isValidPriceRange("$$$"));
        assertTrue(PriceRange.isValidPriceRange("$$$$"));
        assertTrue(PriceRange.isValidPriceRange("$$$$$"));
    }
}
