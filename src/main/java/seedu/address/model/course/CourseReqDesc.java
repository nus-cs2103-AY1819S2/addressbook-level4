package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Course Requirement Description in Model
 */
public class CourseReqDesc {

    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot begin with whitespace";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String courseReqDesc;

    public CourseReqDesc(String courseReqDesc) {
        checkArgument(isValidCourseReqDesc(courseReqDesc), MESSAGE_CONSTRAINTS);
        this.courseReqDesc = courseReqDesc;

    }

    public static boolean isValidCourseReqDesc(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return courseReqDesc;
    }
}
