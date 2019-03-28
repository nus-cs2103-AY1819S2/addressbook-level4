package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.testutil.Assert;

public class DirectoryTest {

    @Test
    public void constructor_invalidDirectory_nullValue_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Directory(null));
    }

    @Test
    public void constructor_invalidDirectory_blankValue_throwsIllegalArgumentException() {
        String invalidDirectory = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Directory(invalidDirectory));
    }

    @Test
    public void constructor_invalidDirectory_blankSpaceValue_throwsIllegalArgumentException() {
        String invalidDirectory = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Directory(invalidDirectory));
    }

    @Test
    public void constructor_invalidDirectory_nonDirectoryValue_throwsIllegalArgumentException() {
        String invalidDirectory = CommandTestUtil.DIR_INVALID_NONEXISTENT;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Directory(invalidDirectory));
    }

    @Test
    public void constructor_validDirectory_normalDirectoryValue_throwsIllegalArgumentException() {
        try {
           Directory d = new Directory(CommandTestUtil.DIR_1_VALID);
           d = null;
        } catch (Exception e) {
            fail("Should not throw exception for an existent directory.");
        }
    }

    @Test
    public void equals_nullInput_expectedFail() {
        Directory d = new Directory(CommandTestUtil.DIR_1_VALID);
        assertFalse(d.equals(null));
    }

    @Test
    public void equals_differentInputs_expectedFail() {
        Directory d1 = new Directory(CommandTestUtil.DIR_1_VALID);
        Directory d2 = new Directory(CommandTestUtil.DIR_2_VALID);
        assertFalse(d1.equals(d2));
    }

    @Test
    public void equals_differentObjectSameValue_expectedPass() {
        Directory d1 = new Directory(CommandTestUtil.DIR_1_VALID);
        Directory d2 = new Directory(CommandTestUtil.DIR_1_VALID);
        assertTrue(d1.equals(d2));
    }

    @Test
    public void toString_differentValues_expectedFail() {
        String notExpected = new StringBuilder()
                .append("Directory: ")
                .append(CommandTestUtil.DIR_2_VALID)
                .append("\n").toString();

        Directory d1 = new Directory(CommandTestUtil.DIR_1_VALID);
        assertNotEquals(d1.toString(), notExpected);
    }

    @Test
    public void toString_sameValues_expectedPass() {
        String expected = new StringBuilder()
                .append("Directory: ")
                .append(CommandTestUtil.DIR_2_VALID)
                .append("\n").toString();

        Directory d1 = new Directory(CommandTestUtil.DIR_2_VALID);
        assertEquals(d1.toString(), expected);
    }


}
