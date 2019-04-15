package seedu.address.model.medicine;

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
        assertFalse(Name.isValidName("Paracetamol*")); // contains illegal non-alphanumeric characters
        assertFalse(Name.isValidName("-Ibuprofen")); // start with a valid symbol
        assertFalse(Name.isValidName("Long Medicine Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")); // long names


        // valid name
        assertTrue(Name.isValidName("Paracetamol")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("Benzoyl Peroxide 5%")); // alphanumeric characters and with '%'
        assertTrue(Name.isValidName("Co-codamol")); // With '-'
        assertTrue(Name.isValidName("Aspirin (5mg)")); // With '(' and ')'
    }
}
