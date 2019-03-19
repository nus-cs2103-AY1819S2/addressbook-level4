package seedu.address.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("^")); // only non-alphanumeric characters
        assertFalse(Price.isValidPrice("abcd")); // only alphabetic characters
        assertFalse(Price.isValidPrice("abcd*")); // contains non-alphanumeric characters
        assertFalse(Price.isValidPrice("12345")); // no decimal digits
        assertFalse(Price.isValidPrice("12.345")); // > 2 decimal digits
        assertFalse(Price.isValidPrice("12.3")); // < 2 decimal digits

        // valid name
        assertTrue(Price.isValidPrice("0.34")); // cheap
        assertTrue(Price.isValidPrice("12.34")); // average
        assertTrue(Price.isValidPrice("123.45")); // expensive
        assertTrue(Name.isValidName("1234567890.23")); // very expensive
    }

}
