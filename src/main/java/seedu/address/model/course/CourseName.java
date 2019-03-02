package seedu.address.model.course;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents name of a course
 */
public class CourseName {
    public static final String MESSAGE_CONSTRAINTS =
            "Must consist of at least 1 word and cannot begin with whitespace";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private final String courseName;

    public CourseName(String courseName) {
        checkArgument(isValidCourseReq(courseName), MESSAGE_CONSTRAINTS);
        this.courseName = courseName;

    }

    public static boolean isValidCourseReq(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return courseName;
    }
}
