package seedu.address.model.util;

import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_AI;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_ALGORITHMS;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_SOFTWARE_ENG;
import static seedu.address.model.util.SampleCourseRequirement.INDUSTRIAL_SYSTEM_EXPERIENCE;
import static seedu.address.model.util.SampleCourseRequirement.SCIENCE_REQUIREMENT;
import static seedu.address.model.util.SampleCourseRequirement.TOTAL_MODULE_COUNT;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseDescription;
import seedu.address.model.course.CourseName;
/**
 * Represents default implementation of Course Classes
 */
public class SampleCourse {
    public static final Course COMPUTER_SCIENCE_ALGORITHMS = new Course(
        new CourseName("Computer Science Algorithms"),
        new CourseDescription("To be completed"), UNIVERSITY_LEVEL_REQUIREMENT,
            COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_ALGORITHMS, INDUSTRIAL_SYSTEM_EXPERIENCE,
            SCIENCE_REQUIREMENT, TOTAL_MODULE_COUNT);
    public static final Course COMPUTER_SCIENCE_SOFTWARE_ENG = new Course(
            new CourseName("Computer Science Software Eng"),
            new CourseDescription("To be completed"), UNIVERSITY_LEVEL_REQUIREMENT,
            COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_SOFTWARE_ENG, SCIENCE_REQUIREMENT);
    public static final Course COMPUTER_SCIENCE_AI = new Course(
            new CourseName("Computer Science Artificial Intelligence"),
            new CourseDescription("To be added"), UNIVERSITY_LEVEL_REQUIREMENT,
            COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_AI, SCIENCE_REQUIREMENT);
}
