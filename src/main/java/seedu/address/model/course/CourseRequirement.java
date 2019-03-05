package seedu.address.model.course;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.module.Module;

/**
 * Represents requirement of a course
 */

public class CourseRequirement {

    private CourseReqName courseReqName;
    private List<Module> modules;
    private CourseReqDesc courseReqDesc;
    private CourseReqCredits courseReqCredits;

    public CourseRequirement(CourseReqName courseReqName, CourseReqDesc courseReqDesc,
                             CourseReqCredits courseReqCredits, Module... modules) {
        this.modules = Arrays.asList(modules);
        this.courseReqDesc = courseReqDesc;
        this.courseReqName = courseReqName;
        this.courseReqCredits = courseReqCredits;
    }

    /**
     * Checks from a list whether this requirement is satisfied
     * @param modulesTaken
     * @return true if requirement is satisfied, false otherwise
     */
    public boolean isFulfilled(List<Module> modulesTaken) {
        int satisfied = modulesTaken.stream()
                                    .filter(x -> modules.contains(x))
                                    .mapToInt(x -> x.getModuleCredits().getCredits())
                                    .sum();
        return satisfied >= courseReqCredits.getCourseReqCredits();
    }

    /**
     * Returns a list of modules that can be taken to satisfy this requirement
     * @param modulesTaken
     * @return A list of modules where modules, an empty list if requirement is satisfied
     *
     */
    public List<Module> getUnfufilledList(List<Module> modulesTaken) {
        if (isFulfilled(modulesTaken)) {
            return new LinkedList<>();
        }

        return modules.stream()
                .filter(x -> !modulesTaken.contains(x))
                .collect(Collectors.toList());
    }

    public CourseReqCredits getCourseReqCredits() {
        return courseReqCredits;
    }

    public CourseReqDesc getCourseReqDesc() {
        return courseReqDesc;
    }

    public CourseReqName getCourseReqName() {
        return courseReqName;
    }

    public List<Module> getModules() {
        return modules;
    }

    @Override
    public String toString() {
        return courseReqName.toString() + ": " + courseReqDesc.toString();
    }

}
