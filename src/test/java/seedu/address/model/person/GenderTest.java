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
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("91")); // numeric
        assertFalse(Gender.isValidGender("cat")); // not in list
        assertFalse(Gender.isValidGender("imacat99")); // mix


        // valid genders
        assertTrue(Gender.isValidGender("M")); // short form
        assertTrue(Gender.isValidGender("Male")); //formal form
        assertTrue(Gender.isValidGender("F")); //short form
        assertTrue(Gender.isValidGender("Female")); //formal form
    }

}
