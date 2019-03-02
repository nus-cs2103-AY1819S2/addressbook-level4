package seedu.address.model.course;

import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
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

    public boolean isFulfilled(List<Module> modulesTaken) {
        int satisfied = modulesTaken.stream()
                                    .filter(x -> modules.contains(x))
                                    .mapToInt(x -> x.getModuleCredits().getCredits())
                                    .sum();
        return satisfied >= courseReqCredits.getCourseReqCredits();
    }

    public List<Module> getUnfufilledList(List<Module> modulesTaken) {
        if (isFulfilled(modulesTaken)) {
            return new LinkedList<>();
        }

        return modules.stream()
                .filter(x -> !modulesTaken.contains(x))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return courseReqName.toString() + ": " + courseReqDesc.toString();
    }

}
