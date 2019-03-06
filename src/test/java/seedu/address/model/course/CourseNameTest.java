package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CourseNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseName(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidCourseName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseName(invalidCourseName));
    }

    @Test
    public void isValidCourseName() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> CourseName.isValidCourseName(null));

        // invalid addresses
        assertFalse(CourseName.isValidCourseName("")); // empty string
        assertFalse(CourseName.isValidCourseName(" ")); // spaces only
        assertFalse(CourseName.isValidCourseName("Software?")); //contains characters and special characters
        assertFalse(CourseName.isValidCourseName("CS2131")); //contains characters and numbers
        // valid addresses
        assertTrue(CourseName.isValidCourseName("Computer Science")); //two words with one space
        assertTrue(CourseName.isValidCourseName("Mathematics")); // one word
        assertTrue(CourseName.isValidCourseName("Computer Science and Statistics")); //multiple spaces
        assertTrue(CourseName.isValidCourseName("A")); //one character
    }
}
