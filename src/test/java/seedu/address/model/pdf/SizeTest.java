package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_2_VALID;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_3_VALID;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_INVALID_ALPHABET;
import static seedu.address.logic.commands.CommandTestUtil.SIZE_INVALID_NEGATIVE;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SizeTest {

    @Test
    public void constructor() {

        // null value -> expect NullPointerException to be thrown.
        Assert.assertThrows(NullPointerException.class, () -> new Size(null));

        // no value -> expect IllegalArgumentException to be thrown.
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(""));

        // blank space -> expect IllegalArgumentException to be thrown.
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(" "));

        // negative value -> expect IllegalArgumentException to be thrown.
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(SIZE_INVALID_NEGATIVE));

        // alphabet value -> expect IllegalArgumentException to be thrown.
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(SIZE_INVALID_ALPHABET));

        // legitimate value -> expect no exceptions to be thrown.
        try {
            Size s = new Size(SIZE_1_VALID);
        } catch (Exception e) {
            fail("Should not fail valid inputs.");
        }

        // single digit value -> expect no exceptions to be thrown.
        try {
            Size s = new Size("1");
        } catch (Exception e) {
            fail("Should not fail valid inputs.");
        }

        // long value -> expect no exceptions to be thrown.
        try {
            Size s = new Size("12345678910111213141516171819202122232425");
        } catch (Exception e) {
            fail("Should not fail valid inputs.");
        }
    }

    @Test
    public void toStringTest() {

        // same value -> expected match
        String expected = new StringBuilder()
                .append("Size: ")
                .append(SIZE_1_VALID)
                .append("\n").toString();

        assertEquals(expected, new Size(SIZE_1_VALID).toString());

        // different value -> expected non-match
        String notExpected = new StringBuilder()
                .append("Size: ")
                .append(SIZE_1_VALID)
                .append("\n").toString();

        assertNotEquals(notExpected, new Size(SIZE_2_VALID).toString());

    }

    @Test
    public void equals() {

        // same value same object -> expected true
        Size size = new Size(SIZE_3_VALID);
        assertTrue(size.equals(size));

        // same value different object -> expected true
        assertTrue(size.equals(new Size(SIZE_3_VALID)));

        // different value -> expected false
        assertFalse(size.equals(new Size(SIZE_2_VALID)));

        // null value -> expected false
        assertFalse(size.equals(null));
    }

}
