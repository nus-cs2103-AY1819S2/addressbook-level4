package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.DIR_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DIR_2_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DIR_INVALID_NONEXISTENT;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DirectoryTest {

    @Test
    public void constructor() {
        // null value -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> new Directory(null));

        // blank value -> throws IllegalArgumentException
        String invalidDirectory = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Directory(invalidDirectory));

        // blank space value -> throws IllegalArgumentException
        String invalidDirectory1 = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Directory(invalidDirectory1));

        // Non existent directory -> throws IllegalArgumentException
        String invalidDirectory2 = DIR_INVALID_NONEXISTENT;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Directory(invalidDirectory2));

        // Correct Directory -> should not throw Exceptions.
        try {
            new Directory(DIR_1_VALID);
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }
    }

    @Test
    public void equals() {
        // null value -> expected false
        assertFalse(new Directory(DIR_1_VALID).equals(null));

        // different directories -> expected true
        assertFalse(new Directory(DIR_1_VALID).equals(new Directory(DIR_2_VALID)));

        // same objects -> expected true
        Directory d = new Directory(DIR_1_VALID);
        assertTrue(d.equals(d));

        // same values, different object -> expected true
        assertTrue(new Directory(DIR_1_VALID).equals(new Directory(DIR_1_VALID)));

    }

    @Test
    public void toStringTest() {
        // different values -> expected false
        String notExpected = new StringBuilder()
                .append("Directory: ")
                .append(DIR_2_VALID)
                .append("\n").toString();
        assertNotEquals(new Directory(DIR_1_VALID).toString(), notExpected);

        // same values -> expected true
        String expected = new StringBuilder()
                .append("Directory: ")
                .append(DIR_2_VALID)
                .append("\n").toString();
        assertEquals(new Directory(DIR_2_VALID).toString(), expected);
    }
}
