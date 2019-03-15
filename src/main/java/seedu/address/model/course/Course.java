package seedu.address.model.course;

import java.util.Arrays;
import java.util.List;

/**
 *  Represents course of the user that is enrolled in
 */
public class Course {

    private CourseName courseName;
    private CourseDescription courseDescription;
    private List<CourseRequirement> courseRequirements;

    public Course(CourseName courseName, CourseDescription courseDescription,
                  CourseRequirement... courseRequirements) {
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
