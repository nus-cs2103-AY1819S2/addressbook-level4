package seedu.address.model.equipment;

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
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidSerialNumber(null));

        // invalid name
        assertFalse(Name.isValidSerialNumber("")); // empty string
        assertFalse(Name.isValidSerialNumber(" ")); // spaces only
        assertFalse(Name.isValidSerialNumber("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidSerialNumber("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidSerialNumber("peter jack")); // alphabets only
        assertTrue(Name.isValidSerialNumber("12345")); // numbers only
        assertTrue(Name.isValidSerialNumber("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidSerialNumber("Capital Tan")); // with capital letters
        assertTrue(Name.isValidSerialNumber("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
