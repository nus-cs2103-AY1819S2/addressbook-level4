/* @@author Carrein */
package seedu.address.model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class HeightTest {

    private final Size height = new Size("10");
    private final Size dup = new Size("10");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void constructor_invalidHeight_throwsIllegalArgumentException() {
        String invalidHeight = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Height(invalidHeight));
    }

    @Test
    public void isValidHeight() {
        // null height
        Assert.assertThrows(NumberFormatException.class, () -> Height.isValidHeight(null));

        // invalid height
        Assert.assertThrows(NumberFormatException.class, () -> Height.isValidHeight("")); //empty height
        Assert.assertThrows(NumberFormatException.class, () -> Height.isValidHeight(" ")); //spaces height
        Assert.assertThrows(NumberFormatException.class, () -> Height.isValidHeight("NaN")); //bot a number
        assertFalse(Height.isValidHeight("0")); // zero height
        assertFalse(Height.isValidHeight(Integer.toString(Integer.MIN_VALUE))); // negative value

        // valid height
        assertTrue(Height.isValidHeight("1")); // minimum positive value
        assertTrue(Height.isValidHeight(Integer.toString(Integer.MAX_VALUE))); // positive value
    }

    @Test
    public void equality() {
        assertTrue(height.equals(dup));
    }

    @Test
    public void stringify() {
        assertEquals(height.toString(), "10");
    }
}
