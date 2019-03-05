package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CourseReqNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseReqName(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidCourseReqName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseReqName(invalidCourseReqName));
    }

    @Test
    public void isValidCourseReqName() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> CourseReqName.isValidCourseReqName(null));

        // invalid course requirement names
        assertFalse(CourseReqName.isValidCourseReqName("")); // empty string
        assertFalse(CourseReqName.isValidCourseReqName(" ")); // spaces only
        assertFalse(CourseReqName.isValidCourseReqName("General Education?"));
        //contains characters and special characters
        assertFalse(CourseReqName.isValidCourseReqName("IT & Pr0Fe3551onalism"));
        //contains characters and numbers
        assertFalse(CourseReqName.isValidCourseReqName("212313")); //only numbers

        // valid course requirement names
        assertTrue(CourseReqName.isValidCourseReqName("Mathematics and Sciences")); //two words with one space
        assertTrue(CourseReqName.isValidCourseReqName("Foundational Modules")); // one word
        assertTrue(CourseReqName.isValidCourseReqName("IT and Professionalism")); //multiple spaces
        assertTrue(CourseReqName.isValidCourseReqName("A")); //one character
    }
}
