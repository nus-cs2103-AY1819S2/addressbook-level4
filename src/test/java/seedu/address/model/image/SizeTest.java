/* @@author Carrein */
package seedu.address.model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SizeTest {

    private final Size size = new Size("10");
    private final Size dup = new Size("10");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Size(null));
    }

    @Test
    public void constructor_invalidSize_throwsIllegalArgumentException() {
        String invalidSize = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Size(invalidSize));
    }

    @Test
    public void isValidSize() {
        // null width
        Assert.assertThrows(NumberFormatException.class, () -> Size.isValidSize(null));

        // invalid width
        Assert.assertThrows(NumberFormatException.class, () -> Size.isValidSize("")); //empty size
        Assert.assertThrows(NumberFormatException.class, () -> Size.isValidSize(" ")); //spaces size
        Assert.assertThrows(NumberFormatException.class, () -> Size.isValidSize("NaN")); //not a number
        assertFalse(Size.isValidSize("0")); // zero size
        assertFalse(Size.isValidSize(Integer.toString(Integer.MIN_VALUE))); // negative value
        assertTrue(Size.isValidSize(Integer.toString(Integer.MAX_VALUE))); // value over 100mb

        // valid size
        assertTrue(Size.isValidSize("1")); // minimum positive size
        assertTrue(Size.isValidSize("100000000")); // maximum positive size
    }

    @Test
    public void equality() {
        assertTrue(size.equals(dup));
    }

    @Test
    public void stringify() {
        assertEquals(size.toString(), "10");
    }
}
