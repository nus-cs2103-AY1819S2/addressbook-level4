package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents description of a course
 */
public class CourseDescription {
    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot begin with whitespace";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String courseDescription;

    public CourseDescription(String courseDescription) {
        checkArgument(isValidCourseDescription(courseDescription), MESSAGE_CONSTRAINTS);
        this.courseDescription = courseDescription;

    }

    public static boolean isValidCourseDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return courseDescription;
    }

}
