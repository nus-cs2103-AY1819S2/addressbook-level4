package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.testutil.Assert;

public class SizeTest {

    @Test
    public void constructor_nullValue_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Size(null));
    }

    @Test
    public void constructor_emptyValue_throwsIllegalArgumentException() {
        String invalidSize = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(invalidSize));
    }

    @Test
    public void constructor_emptySpaceValue_throwsIllegalArgumentException() {
        String invalidSize = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(invalidSize));
    }

    @Test
    public void constructor_negativeValue_throwsIllegalArgumentException() {
        String invalidSize = CommandTestUtil.SIZE_INVALID_NEGATIVE;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(invalidSize));
    }

    @Test
    public void constructor_alphabetValue_throwsIllegalArgumentException() {
        String invalidSize = CommandTestUtil.SIZE_INVALID_ALPHABET;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Size(invalidSize));
    }

    @Test
    public void constructor_normalValue_expectNoException() {
        try {
            Size s = new Size(CommandTestUtil.SIZE_1_VALID);
        } catch (Exception e) {
            fail("Should not fail valid inputs.");
        }
    }

    @Test
    public void constructor_singleValue_expectNoException() {
        try {
            Size s = new Size("1");
        } catch (Exception e) {
            fail("Should not fail valid inputs.");
        }
    }

    @Test
    public void constructor_longValue_expectNoException() {
        try {
            Size s = new Size("12345678910111213141516171819202122232425");
        } catch (Exception e) {
            fail("Should not fail valid inputs.");
        }
    }

    @Test
    public void toString_matchInput_expectedTrue() {
        String expected = new StringBuilder()
                .append("Size: ")
                .append(CommandTestUtil.SIZE_1_VALID)
                .append("\n").toString();

        assertEquals(expected, new Size(CommandTestUtil.SIZE_1_VALID).toString());
    }

    @Test
    public void toString_doesNotMatchDifferentValue_expectedFalse() {
        String notExpected = new StringBuilder()
                .append("Size: ")
                .append(CommandTestUtil.SIZE_1_VALID)
                .append("\n").toString();

        assertNotEquals(notExpected, new Size(CommandTestUtil.SIZE_2_VALID).toString());
    }

    @Test
    public void equals_matchInput_expectedTrue() {
        Size expected = new Size(CommandTestUtil.SIZE_3_VALID);
        assertTrue(new Size(CommandTestUtil.SIZE_3_VALID).equals(
                new Size(Long.toString(Paths.get(CommandTestUtil.FILEPATH_3_VALID).toFile().length()))));
    }

    @Test
    public void equals_nullInput_expectedFalse() {
        assertFalse(new Size(CommandTestUtil.SIZE_3_VALID).equals(null));
    }

}
