package seedu.address.model.battleship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrientationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Orientation(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Orientation(invalidAddress));
    }

    @Test
    public void isValidOrientation() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Orientation.isValidOrientation(null));

        // invalid addresses
        assertFalse(Orientation.isValidOrientation("")); // empty string
        assertFalse(Orientation.isValidOrientation(" ")); // spaces only
        assertFalse(Orientation.isValidOrientation("*9")); // symbols
        assertFalse(Orientation.isValidOrientation("vert")); // incorrect abbreviation
        assertFalse(Orientation.isValidOrientation("g")); // incorrect symbol

        // valid addresses
        assertTrue(Orientation.isValidOrientation("vertical")); // starting coordinate
        assertTrue(Orientation.isValidOrientation("horizontal")); // in between coordinate
        assertTrue(Orientation.isValidOrientation("v")); // extreme boundary
        assertTrue(Orientation.isValidOrientation("h")); // extreme boundary
    }

    @Test
    public void isHorizontalTest() {
        assertTrue(new Orientation("h").isHorizontal());
        assertTrue(new Orientation("horizontal").isHorizontal());
    }

    @Test
    public void isVerticalTest() {
        assertTrue(new Orientation("v").isVertical());
        assertTrue(new Orientation("vertical").isVertical());
    }

    @Test
    public void testString() {
        assertEquals(new Orientation("vertical").toString(), "vertical");
        assertEquals(new Orientation("horizontal").toString(), "horizontal");

        assertEquals(new Orientation("v").toString(), "vertical");
        assertEquals(new Orientation("h").toString(), "horizontal");
    }

    @Test
    public void testEquality() {
        assertEquals(new Orientation("v"), new Orientation("vertical"));
        assertNotEquals(new Orientation("v"), new Orientation("horizontal"));
    }
}
