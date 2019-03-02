package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a name of a course requirement in the model
 */
public class CourseReqName {

    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot begin with whitespace";
    public static final String VALIDATION_REGEX = "[^\\s][A-z ]*";
    private final String courseReqName;

    public CourseReqName(String courseReqName) {
        checkArgument(isValidCourseReqName(courseReqName), MESSAGE_CONSTRAINTS);
        this.courseReqName = courseReqName;

    }

    public static boolean isValidCourseReqName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return courseReqName;
    }
}
