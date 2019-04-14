package seedu.address.model.apparel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ColorTest {

    @Test
    public void constructor_invalidColor_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Color(invalidPhone));
    }

    @Test
    public void isValidColor() {
        // invalid color
        assertFalse(ColorValue.isValidColor("")); // empty string
        assertFalse(ColorValue.isValidColor(" ")); // spaces only
        assertFalse(ColorValue.isValidColor("911234")); // numbers
        assertFalse(ColorValue.isValidColor("9011p041")); // alphabets within digits
        assertFalse(ColorValue.isValidColor("9312 1534")); // spaces within digits

        // valid color
        assertTrue(ColorValue.isValidColor("White")); // normal text
    }
}
