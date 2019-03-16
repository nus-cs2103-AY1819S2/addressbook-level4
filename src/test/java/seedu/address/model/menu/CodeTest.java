package seedu.address.model.menu;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Code(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidCode));
    }

    @Test
    public void isValidName() {
        // null code
        Assert.assertThrows(NullPointerException.class, () -> Code.isValidCode(null));

        // invalid code
        assertFalse(Code.isValidCode("")); // empty string
        assertFalse(Code.isValidCode(" ")); // spaces only
        assertFalse(Code.isValidCode("^")); // only non-alphanumeric characters
        assertFalse(Code.isValidCode("crepes*")); // contains non-alphanumeric characters
        assertFalse(Code.isValidCode("abcde")); // only alphabetic characters
        assertFalse(Code.isValidCode("1234")); // only numeric characters
        assertFalse(Code.isValidCode("abcde1234")); // > 1 alphabet, > 2 numeric characters
        assertFalse(Code.isValidCode("ab1")); // > 1 alphabet, < 2 numeric characters
        assertFalse(Code.isValidCode("w09")); // non-capital alphabet

        // valid code
        assertTrue(Name.isValidName("A12")); // of specified format
    }

}
