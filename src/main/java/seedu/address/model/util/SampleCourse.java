package seedu.address.model.util;

import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_ALGORITHMS;
import static seedu.address.model.util.SampleCourseRequirement.SCIENCE_REQUIREMENT;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseDescription;
import seedu.address.model.course.CourseName;

/**
 * Represents default implementation of Course Classes
 */
public class SampleCourse {
    public static final Course COMPUTER_SCIENCE_ALGORITHMS = new Course(
        new CourseName("Computer Science - Algorithms"),
        new CourseDescription("To be completed"), UNIVERSITY_LEVEL_REQUIREMENT,
            COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_ALGORITHMS, SCIENCE_REQUIREMENT
    );
}
