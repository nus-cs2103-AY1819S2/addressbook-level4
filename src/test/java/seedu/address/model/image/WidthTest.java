/* @@author Carrein */
package seedu.address.model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class WidthTest {

    private final Size width = new Size("10");
    private final Size dup = new Size("10");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Width(null));
    }

    @Test
    public void constructor_invalidWidth_throwsIllegalArgumentException() {
        String invalidWidth = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Width(invalidWidth));
    }

    @Test
    public void isValidWidth() {
        // null width
        Assert.assertThrows(NumberFormatException.class, () -> Width.isValidWidth(null));

        // invalid width
        Assert.assertThrows(NumberFormatException.class, () -> Width.isValidWidth("")); //empty width
        Assert.assertThrows(NumberFormatException.class, () -> Width.isValidWidth(" ")); //spaces width
        Assert.assertThrows(NumberFormatException.class, () -> Width.isValidWidth("NaN")); //not a number
        assertFalse(Width.isValidWidth("0")); // zero width
        assertFalse(Width.isValidWidth(Integer.toString(Integer.MIN_VALUE))); // negative value

        // valid width
        assertTrue(Width.isValidWidth("1")); // minimum positive value
        assertTrue(Width.isValidWidth(Integer.toString(Integer.MAX_VALUE))); // positive value
    }

    @Test
    public void equality() {
        assertTrue(width.equals(dup));
    }

    @Test
    public void stringify() {
        assertEquals(width.toString(), "10");
    }
}
