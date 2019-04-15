package seedu.travel.model.place;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.travel.testutil.Assert;

public class NameTest {

    public static final String VALID_ALPHA_ONLY = "peter jack";
    public static final String VALID_NUM_ONLY = "12345";
    public static final String VALID_ALPHA_NUM = "peter the 2nd";
    public static final String VALID_ALPHA_CAPS = "Capital Tan";
    public static final String VALID_LONG = "David Roger Jackson Ray Jr 2nd";

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
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName(VALID_ALPHA_ONLY)); // alphabets only
        assertTrue(Name.isValidName(VALID_NUM_ONLY)); // numbers only
        assertTrue(Name.isValidName(VALID_ALPHA_NUM)); // alphanumeric characters
        assertTrue(Name.isValidName(VALID_ALPHA_CAPS)); // with capital letters
        assertTrue(Name.isValidName(VALID_LONG)); // long names
    }

    @Test
    public void hashCodeTest() {
        Name test1 = new Name(VALID_ALPHA_NUM);
        Name test2 = new Name(VALID_LONG);

        assertEquals(test1.hashCode(), test1.hashCode());
        assertNotEquals(test1.hashCode(), test2.hashCode());
    }
}
