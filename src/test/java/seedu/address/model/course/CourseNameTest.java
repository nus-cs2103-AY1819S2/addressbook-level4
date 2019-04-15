package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CourseNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseName(null));
    }

    @Test
    public void isValidCourseName() {
        //invalid course name
        assertFalse(CourseName.isValidCourseName("&S*A&D!"));
        assertFalse(CourseName.isValidCourseName(" starts with white space"));
        assertFalse(CourseName.isValidCourseName("2138129839120"));

        //valid course name
        assertTrue(CourseName.isValidCourseName("coursename"));
    }

    @Test
    public void equals() {
        CourseName test = new CourseName("valid coursename");
        //equal
        assertEquals(test, test);
        assertEquals(new CourseName("valid coursename"), test);

        //not equal
        assertNotEquals(null, test);
        assertNotEquals("somethingelse", test);
        assertNotEquals(new CourseName("some other name"), test);
    }

}
