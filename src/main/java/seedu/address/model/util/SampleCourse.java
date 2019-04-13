package seedu.address.model.util;

import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SYSTEM_TEAM_PROJECT;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_AI;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_ALGORITHMS;
import static seedu.address.model.util.SampleCourseRequirement.FOCUS_AREA_SOFTWARE_ENG;
import static seedu.address.model.util.SampleCourseRequirement.INDUSTRIAL_SYSTEM_EXPERIENCE;
import static seedu.address.model.util.SampleCourseRequirement.IT_PROFESSIONALISM;
import static seedu.address.model.util.SampleCourseRequirement.MATH_REQUIREMENT;
import static seedu.address.model.util.SampleCourseRequirement.SCIENCE_REQUIREMENT;
import static seedu.address.model.util.SampleCourseRequirement.TOTAL_MODULE_COUNT;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseDescription;
import seedu.address.model.course.CourseList;
import seedu.address.model.course.CourseName;

/**
 * Represents default implementation of Course Classes
 */
public class SampleCourse {
    public static final Course COMPUTER_SCIENCE_ALGORITHMS = new Course(
        new CourseName("Computer Science Algorithms"),
        new CourseDescription("Computer Science Major with Focus Area in Algorithms"),
            UNIVERSITY_LEVEL_REQUIREMENT, COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_ALGORITHMS,
            INDUSTRIAL_SYSTEM_EXPERIENCE, COMPUTER_SYSTEM_TEAM_PROJECT, IT_PROFESSIONALISM,
            MATH_REQUIREMENT, SCIENCE_REQUIREMENT, TOTAL_MODULE_COUNT);
    public static final Course COMPUTER_SCIENCE_SOFTWARE_ENG = new Course(
            new CourseName("Computer Science Software Engineering"),
            new CourseDescription("Computer Science Major with Focus Area in Software Engineering"),
            UNIVERSITY_LEVEL_REQUIREMENT, COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_SOFTWARE_ENG,
            INDUSTRIAL_SYSTEM_EXPERIENCE, COMPUTER_SYSTEM_TEAM_PROJECT, IT_PROFESSIONALISM,
            MATH_REQUIREMENT, SCIENCE_REQUIREMENT, TOTAL_MODULE_COUNT);
    public static final Course COMPUTER_SCIENCE_AI = new Course(
            new CourseName("Computer Science Artificial Intelligence"),
            new CourseDescription("Computer Science Major with Focus Area in Artificial Intelligence"),
            UNIVERSITY_LEVEL_REQUIREMENT, COMPUTER_SCIENCE_FOUNDATION, FOCUS_AREA_AI,
            INDUSTRIAL_SYSTEM_EXPERIENCE, COMPUTER_SYSTEM_TEAM_PROJECT, IT_PROFESSIONALISM,
            MATH_REQUIREMENT, SCIENCE_REQUIREMENT, TOTAL_MODULE_COUNT);
    public static CourseList getSampleCourseList() {
        CourseList courseList = new CourseList();
        courseList.addCourse(COMPUTER_SCIENCE_ALGORITHMS);
        courseList.addCourse(COMPUTER_SCIENCE_AI);
        courseList.addCourse(COMPUTER_SCIENCE_SOFTWARE_ENG);
        return courseList;
    }
}
