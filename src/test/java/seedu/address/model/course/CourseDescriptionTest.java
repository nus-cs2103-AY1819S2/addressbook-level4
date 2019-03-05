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
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidCourseDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseDescription(invalidCourseDescription));
    }

    @Test
    public void isValidCourseDescription() {
        // null descriptions
        Assert.assertThrows(NullPointerException.class, () -> CourseDescription.isValidCourseDescription(null));

        // invalid descriptions
        assertFalse(CourseDescription.isValidCourseDescription("")); // empty string
        assertFalse(CourseDescription.isValidCourseDescription(" ")); // spaces only

        // valid descriptions
        assertTrue(CourseDescription.isValidCourseDescription("Description here")); //two words with one space
        assertTrue(CourseDescription.isValidCourseDescription("Nil")); // one word
        assertTrue(CourseDescription.isValidCourseDescription(
                "The Bachelor of Computing (Honours) in Computer Science or BComp (CS) programme aims to nurture "
                        + "students for a rewarding computing career in various industry sectors. "
                        + "Suitable for those who love hands-on work and keen to apply computing technologies "
                        + "to solve real-world problems, the programme will equip students with the critical "
                        + "knowledge and capacity to take on the world with confidence.")); //long description
        assertTrue(CourseDescription.isValidCourseDescription("112312")); //only numbers
        assertTrue(CourseDescription.isValidCourseDescription("testing123 1111"));
        assertTrue(CourseDescription.isValidCourseDescription("a")); //only one alphabet
        assertTrue(CourseDescription.isValidCourseDescription("1")); //only one number
    }
}
