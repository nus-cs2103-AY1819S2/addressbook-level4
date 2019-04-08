package seedu.address.model.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Wrapper class of CourseRequirement to store a snapshot of the completion of requirement
 * at the specific time frame when this class is created
 */
public class RequirementStatus {

    private List<ModuleInfoCode> moduleInfoCodes;
    private ObservableList<ModuleInfo> allModules;
    private final CourseRequirement courseRequirement;
    private double percentageFulfilled;
    private boolean isFulfilled;
    private List<String> unsatisfiedRegex = new ArrayList<>();

    public RequirementStatus(CourseRequirement courseRequirement,
                             List<ModuleInfoCode> moduleInfoCodes,
                             ObservableList<ModuleInfo> allModules) {
        this.allModules = allModules;
        this.courseRequirement = courseRequirement;
        this.updateModuleInfoCodes(moduleInfoCodes);
    }

    /**
     * Updates moduleInfoCodes of this class
     */
    public void updateModuleInfoCodes(List<ModuleInfoCode> moduleInfoCodes) {
        this.moduleInfoCodes = moduleInfoCodes;
        this.isFulfilled = courseRequirement.isFulfilled(moduleInfoCodes);
        this.percentageFulfilled = courseRequirement.getFulfilledPercentage(moduleInfoCodes);
        unsatisfiedRegex.addAll(courseRequirement.getUnfulfilled(moduleInfoCodes));

    }

    public CourseRequirement getCourseRequirement() {
        return courseRequirement;
    }

    public double getPercentageFulfilled() {
        return percentageFulfilled;
    }

    public boolean isFulfilled() {
        return isFulfilled;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RequirementStatus)) {
            return false;
        }

        RequirementStatus another = (RequirementStatus) other;

        return this.courseRequirement.equals(another.courseRequirement)
                && this.allModules.equals(another.allModules)
                && this.moduleInfoCodes.equals(another.moduleInfoCodes);
    }
}
