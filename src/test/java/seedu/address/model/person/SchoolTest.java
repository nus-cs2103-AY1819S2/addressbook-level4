package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SchoolTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new School(null));
    }

    @Test
    public void constructor_invalid_throwsIllegalArgumentException() {
        String invalidSchool = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new School(invalidSchool));
    }

    @Test
    public void isValidSchool() {
        // null school
        Assert.assertThrows(NullPointerException.class, () -> School.isValidSchool(null));

        // invalid school
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid school
        assertTrue(Address.isValidAddress("NUS"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("National University of Singapore")); // long School
    }

}
