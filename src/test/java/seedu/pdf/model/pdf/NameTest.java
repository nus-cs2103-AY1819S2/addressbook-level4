package seedu.pdf.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.pdf.logic.commands.CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_INVALID_CHARACTERS;
import static seedu.pdf.logic.commands.CommandTestUtil.NAME_INVALID_EXTENSION;

import org.junit.Test;

import seedu.pdf.testutil.Assert;

public class NameTest {

    @Test
    public void constructor() {
        // null value -> expect NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));

        // blank Name -> throws IllegalArgumentException
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(""));

        // blank Space Name -> throws IllegalArgumentException
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(" "));

        // wrong Extension -> throws IllegalArgumentException
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(NAME_INVALID_EXTENSION));

        // invalid character -> throws IllegalArgumentException
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(NAME_INVALID_CHARACTERS));

        // normal name -> No Exceptions
        try {
            new Name(NAME_1_VALID);
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }

        // name with space -> No Exception
        try {
            new Name(NAME_2_VALID);
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }
    }

    @Test
    public void toStringTest() {
        // same value -> expect True
        assertEquals(new StringBuilder()
                .append("Name: ")
                .append(NAME_1_VALID)
                .append("\n")
                .toString(), new Name(NAME_1_VALID).toString());

        // different value -> expect false
        assertNotEquals(new StringBuilder()
                .append("Name: ")
                .append(NAME_1_VALID)
                .append("\n")
                .toString(), new Name(NAME_2_VALID).toString());
    }

    @Test
    public void equals() {
        // different values -> expected false
        assertFalse(new Name(NAME_1_VALID).equals(new Name(NAME_2_VALID)));

        // same object -> expected true
        assertTrue(new Name(NAME_1_VALID).equals(new Name(NAME_1_VALID)));

        // same values, different objects -> expected true
        assertTrue(new Name(NAME_1_VALID).equals(new Name(NAME_1_VALID)));
    }
}
