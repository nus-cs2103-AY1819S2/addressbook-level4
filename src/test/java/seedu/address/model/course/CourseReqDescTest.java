package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CourseReqDescTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseReqDesc(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidCourseReqDesc = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseReqDesc(invalidCourseReqDesc));
    }

    @Test
    public void isValidCourseReqDesc() {
        // null descriptions
        Assert.assertThrows(NullPointerException.class, () -> CourseReqDesc.isValidCourseReqDesc(null));

        // invalid descriptions
        assertFalse(CourseReqDesc.isValidCourseReqDesc("")); // empty string
        assertFalse(CourseReqDesc.isValidCourseReqDesc(" ")); // spaces only

        // valid descriptions
        assertTrue(CourseReqDesc.isValidCourseReqDesc("Description here")); //two words with one space
        assertTrue(CourseReqDesc.isValidCourseReqDesc("Nil")); // one word
        assertTrue(CourseReqDesc.isValidCourseReqDesc(
                "Pass all of the following: "
                        + "CS1101S Programming Methodology, "
                        + "CS1231 Discrete Structures, "
                        + "CS2030 Programming Methodology II, "
                        + "CS2040 Data Structures and Algorithms, "
                        + "CS2100 Computer Organisation, "
                        + "CS2103T Software Engineering, "
                        + "CS2105 Introduction to Computer Networks, "
                        + "CS2106 Introduction to Operating Systems and "
                        + "CS3230 Design and Analysis of Algorithms")); //long description
        assertTrue(CourseReqDesc.isValidCourseReqDesc("112312")); //only numbers
        assertTrue(CourseReqDesc.isValidCourseReqDesc("testing123 1111"));
        assertTrue(CourseReqDesc.isValidCourseReqDesc("a")); //only one alphabet
        assertTrue(CourseReqDesc.isValidCourseReqDesc("1")); //only one number
    }
}
