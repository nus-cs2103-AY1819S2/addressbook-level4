package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents name of a course
 */
public class CourseName {
    public static final String MESSAGE_CONSTRAINTS =
            "Consists of characters and spaces only ";
    public static final String VALIDATION_REGEX = "[^\\s][A-z ]*";
    private final String courseName;

    public CourseName(String courseName) {
        requireNonNull(courseName);
        checkArgument(isValidCourseName(courseName), MESSAGE_CONSTRAINTS);
        this.courseName = courseName;

    }

    public static boolean isValidCourseName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return courseName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof CourseName) {
            return this.courseName.equals(((CourseName) obj).courseName);
        } else {
            return false;
        }
    }
}
