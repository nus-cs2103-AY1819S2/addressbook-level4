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
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid genders
        assertFalse(Gender.isValidGender("hi")); // text other than "f" or "m"
        assertFalse(Gender.isValidGender("femal")); // text other than "f" or "m"
        assertFalse(Gender.isValidGender("happy")); // text other than "f" or "m"
        assertFalse(Gender.isValidGender("p")); // text other than "f" or "m"
        assertFalse(Gender.isValidGender(" ")); // spaces only

        // valid genders
        assertTrue(Gender.isValidGender("F"));
        assertTrue(Gender.isValidGender("M"));
    }
}
