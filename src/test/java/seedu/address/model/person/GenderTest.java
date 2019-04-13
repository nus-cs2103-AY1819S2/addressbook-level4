package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null Gender
        Assert.assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid Gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("male")); // m is not uppercase
        assertFalse(Gender.isValidGender("female")); // f is not uppercase
        assertFalse(Gender.isValidGender("12345678")); // all digits

        // valid Gender
        assertTrue(Gender.isValidGender("Male"));
    }

}
