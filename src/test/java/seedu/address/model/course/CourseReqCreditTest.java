package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CourseReqCreditTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseReqCredits(null));
    }

    @Test
    public void constructor_invalidCourseReqCredits_throwsIllegalArgumentException() {
        int invalidCourseReqCredits = -5;
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseReqCredits(invalidCourseReqCredits));
    }

    @Test
    public void isValidModuleCredit() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> CourseReqCredits.isValidCourseReqCredits(null));

        // invalid addresses
        assertFalse(CourseReqCredits.isValidCourseReqCredits(-21312)); //negative

        // valid addresses
        assertTrue(CourseReqCredits.isValidCourseReqCredits(0));
        assertTrue(CourseReqCredits.isValidCourseReqCredits(5)); //minimum
    }

}
