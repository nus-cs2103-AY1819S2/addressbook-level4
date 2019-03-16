package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.List;

/**
 *  Represents course of the user that is enrolled in
 */
public class Course {

    private final CourseName courseName;
    private final CourseDescription courseDescription;
    private final List<CourseRequirement> courseRequirements;

    public Course(CourseName courseName, CourseDescription courseDescription,
                  CourseRequirement... courseRequirements) {
        requireAllNonNull(courseName, courseDescription, courseRequirements);
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseRequirements = Arrays.asList(courseRequirements);
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public CourseDescription getCourseDescription() {
        return courseDescription;
    }

    public List<CourseRequirement> getCourseRequirements() {
        return courseRequirements;
    }

    public static Course getCourseByName(CourseName name) {
        return new Course(name, new CourseDescription("TODO"), null);
    }
}
