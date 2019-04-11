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
import static seedu.address.model.pdf.Size.PREFIX_BYTE;
import static seedu.address.model.pdf.Size.PREFIX_GIGABYTE;
import static seedu.address.model.pdf.Size.PREFIX_KILOBYTE;
import static seedu.address.model.pdf.Size.PREFIX_MEGABYTE;
import static seedu.address.model.pdf.Size.THRESHOLD_GIGABYTE;
import static seedu.address.model.pdf.Size.THRESHOLD_KILOBYTE;
import static seedu.address.model.pdf.Size.THRESHOLD_MEGABYTE;

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
    public void getReadableValue() {

        // size smaller than kilobyte threshold -> expected PREFIX_BYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_KILOBYTE - 1)).getReadableValue(),
                Integer.toString(THRESHOLD_KILOBYTE - 1) + " " + PREFIX_BYTE);

        // size equal to kilobyte threshold -> expected PREFIX_KILOBYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_KILOBYTE)).getReadableValue(),
                Integer.toString(THRESHOLD_KILOBYTE / THRESHOLD_KILOBYTE) + " " + PREFIX_KILOBYTE);

        // size bigger than kilobyte threshold -> expected PREFIX_KILOBYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_KILOBYTE + 1)).getReadableValue(),
                Integer.toString((THRESHOLD_KILOBYTE + 1) / THRESHOLD_KILOBYTE) + " " + PREFIX_KILOBYTE);

        // size equal to megabyte threshold -> expected PREFIX_MEGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_MEGABYTE)).getReadableValue(),
                Integer.toString(THRESHOLD_MEGABYTE / THRESHOLD_MEGABYTE) + " " + PREFIX_MEGABYTE);

        // size bigger than megabyte threshold -> expected PREFIX_MEGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_MEGABYTE + 1)).getReadableValue(),
                Integer.toString((THRESHOLD_MEGABYTE + 1) / THRESHOLD_MEGABYTE) + " " + PREFIX_MEGABYTE);

        // size equal to gigabyte threshold -> expected PREFIX_GIGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_GIGABYTE)).getReadableValue(),
                Integer.toString(THRESHOLD_GIGABYTE / THRESHOLD_GIGABYTE) + " " + PREFIX_GIGABYTE);

        // size bigger than gigabyte threshold -> expected PREFIX_GIGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_GIGABYTE + 1)).getReadableValue(),
                Integer.toString((THRESHOLD_GIGABYTE + 1) / THRESHOLD_GIGABYTE) + " " + PREFIX_GIGABYTE);
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
