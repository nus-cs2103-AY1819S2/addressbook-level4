package seedu.pdf.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.pdf.logic.commands.CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_3_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_INVALID_ALPHABET;
import static seedu.pdf.logic.commands.CommandTestUtil.SIZE_INVALID_NEGATIVE;
import static seedu.pdf.model.pdf.Size.PREFIX_BYTE;
import static seedu.pdf.model.pdf.Size.PREFIX_GIGABYTE;
import static seedu.pdf.model.pdf.Size.PREFIX_KILOBYTE;
import static seedu.pdf.model.pdf.Size.PREFIX_MEGABYTE;
import static seedu.pdf.model.pdf.Size.THRESHOLD_GIGABYTE;
import static seedu.pdf.model.pdf.Size.THRESHOLD_KILOBYTE;
import static seedu.pdf.model.pdf.Size.THRESHOLD_MEGABYTE;

import org.junit.Test;

import seedu.pdf.testutil.Assert;

public class SizeTest {

    private static final int CONVERSION_KILOBYTE = 10;
    private static final int CONVERSION_MEGABYTE = 20;
    private static final int CONVERSION_GIGABYTE = 30;

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
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }

        // single digit value -> expect no exceptions to be thrown.
        try {
            Size s = new Size("1");
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }

        // long value -> expect no exceptions to be thrown.
        try {
            Size s = new Size("12345678910111213141516171819202122232425");
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
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
                Integer.toString(THRESHOLD_KILOBYTE >> CONVERSION_KILOBYTE) + " " + PREFIX_KILOBYTE);

        // size bigger than kilobyte threshold -> expected PREFIX_KILOBYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_KILOBYTE + 1)).getReadableValue(),
                Integer.toString((THRESHOLD_KILOBYTE + 1) >> CONVERSION_KILOBYTE) + " " + PREFIX_KILOBYTE);

        // size equal to megabyte threshold -> expected PREFIX_MEGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_MEGABYTE)).getReadableValue(),
                Integer.toString(THRESHOLD_MEGABYTE >> CONVERSION_MEGABYTE) + " " + PREFIX_MEGABYTE);

        // size bigger than megabyte threshold -> expected PREFIX_MEGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_MEGABYTE + 1)).getReadableValue(),
                Integer.toString((THRESHOLD_MEGABYTE + 1) >> CONVERSION_MEGABYTE) + " " + PREFIX_MEGABYTE);

        // size equal to gigabyte threshold -> expected PREFIX_GIGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_GIGABYTE)).getReadableValue(),
                Integer.toString(THRESHOLD_GIGABYTE >> CONVERSION_GIGABYTE) + " " + PREFIX_GIGABYTE);

        // size bigger than gigabyte threshold -> expected PREFIX_GIGABYTE
        assertEquals(new Size(Integer.toString(THRESHOLD_GIGABYTE + 1)).getReadableValue(),
                Integer.toString((THRESHOLD_GIGABYTE + 1) >> CONVERSION_GIGABYTE) + " " + PREFIX_GIGABYTE);
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
