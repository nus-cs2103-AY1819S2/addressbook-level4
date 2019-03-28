package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_blankName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_blankSpaceName_throwsIllegalArgumentException() {
        String invalidName = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_wrongExtension_throwsIllegalArgumentException() {
        String invalidName = CommandTestUtil.NAME_INVALID_EXTENSION;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_invalidCharacter_throwsIllegalArgumentException() {
        String invalidName = CommandTestUtil.NAME_INVALID_CHARACTERS;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void constructor_normalName_expectNoException() {
        try {
            Name expected = new Name(CommandTestUtil.NAME_1_VALID);
            expected = null;
        } catch (Exception e) {
            fail("No exception should be thrown with a valid name.");
        }
    }

    @Test
    public void constructor_nameWithSpace_expectNoException() {
        try {
            Name expected = new Name(CommandTestUtil.NAME_2_VALID);
            expected = null;
        } catch (Exception e) {
            fail("No exception should be thrown with a valid name containing a space.");
        }
    }

    @Test
    public void toString_sameValue_expectedPass() {
        String expected = new StringBuilder()
                .append("Name: ")
                .append(CommandTestUtil.NAME_1_VALID)
                .append("\n")
                .toString();

        assertEquals(expected, new Name(CommandTestUtil.NAME_1_VALID).toString());
    }

    @Test
    public void toString_differentValue_expectedPass() {
        String expected = new StringBuilder()
                .append("Name: ")
                .append(CommandTestUtil.NAME_1_VALID)
                .append("\n")
                .toString();

        assertNotEquals(expected, new Name(CommandTestUtil.NAME_2_VALID).toString());
    }

    @Test
    public void equals_differentValue_expectedFail() {
        Name n1 = new Name(CommandTestUtil.NAME_1_VALID);
        Name n2 = new Name(CommandTestUtil.NAME_2_VALID);
        assertFalse(n1.equals(n2));
    }

    @Test
    public void equals_sameValueDifferentObject_expectedPass() {
        Name n1 = new Name(CommandTestUtil.NAME_1_VALID);
        Name n2 = new Name(CommandTestUtil.NAME_1_VALID);
        assertTrue(n1.equals(n2));
    }
}
