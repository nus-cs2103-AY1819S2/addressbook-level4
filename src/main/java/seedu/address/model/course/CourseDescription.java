package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents description of a course
 */
public class CourseDescription {
    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot begin with whitespace";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String courseReqDesc;

    public CourseDescription(String courseReqDesc) {
        checkArgument(isValidCourseReq(courseReqDesc), MESSAGE_CONSTRAINTS);
        this.courseReqDesc = courseReqDesc;

    }

    public static boolean isValidCourseReq(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return courseReqDesc;
    }
}
