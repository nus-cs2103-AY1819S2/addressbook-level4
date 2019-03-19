package seedu.address.model.menu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("crepes*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("kimchi fried rice")); // alphabets only
        assertTrue(Name.isValidName("1800")); // numbers only
        assertTrue(Name.isValidName("3 olives")); // alphanumeric characters
        assertTrue(Name.isValidName("Shrimp Fried Rice")); // with capital letters
        assertTrue(Name.isValidName("Braised Leeks with Mozzarella and a Fried Egg")); // long names
    }

}
