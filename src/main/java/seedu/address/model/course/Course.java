package seedu.address.model.course;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.module.Module;

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

    /**
     * Checks for modulesTaken against requirements to find requirement not satisfied
     * @param modulesTaken
     * @return list of requirements satisfied
     */
    public List<CourseRequirement> satisfiedRequirements(List<Module> modulesTaken) {
        return courseRequirements.stream()
                                 .filter(x -> x.isFulfilled(modulesTaken))
                                 .collect(Collectors.toList());
    }


    /**
     * Checks for modulesTaken against requirements to find requirement not satisfied
     * @param modulesTaken
     * @return list of requirements not satisfied
     */
    public List<CourseRequirement> unsatisfiedRequirements(List<Module> modulesTaken) {
        return courseRequirements.stream()
                .filter(x -> !x.isFulfilled(modulesTaken))
                .collect(Collectors.toList());
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
