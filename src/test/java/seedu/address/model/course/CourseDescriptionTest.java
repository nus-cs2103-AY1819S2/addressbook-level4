package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


public class CourseDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseDescription(null));
    }

    @Test
    public void isValidCourseDescription() {
        //invalid course description
        assertFalse(CourseDescription.isValidCourseDescription(" starts with white space"));

        //valid course description
        assertTrue(CourseName.isValidCourseName("some description"));
    }

    @Test
    public void equals() {
        CourseDescription test = new CourseDescription("valid course description");
        //equal
        assertTrue(test.equals(test));
        assertTrue(test.equals(new CourseDescription("valid course description")));

        //not equal
        assertFalse(test.equals(null));
        assertFalse(test.equals(5));
        assertFalse(test.equals(new CourseDescription("some other name")));
    }

}
