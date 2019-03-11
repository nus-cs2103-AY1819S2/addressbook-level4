package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents credits for course requirements
 */
public class CourseReqCredits {

    public static final String MESSAGE_CONSTRAINTS =
            "Credit to satisfiy prerequisite cannot be less than 0";
    public static final int MIN_CRED = 0;
    private final Integer courseReqCredits;

    public CourseReqCredits(Integer courseReqCredits) {
        checkArgument(isValidCourseReqCredits(courseReqCredits), MESSAGE_CONSTRAINTS);
        this.courseReqCredits = courseReqCredits;
    }

    public static boolean isValidCourseReqCredits(Integer test) {
        return test >= MIN_CRED;
    }

    @Override
    public String toString() {
        return String.format("%d", courseReqCredits);
    }

    public int getCourseReqCredits() {
        return getCourseReqCredits();
    }
}
