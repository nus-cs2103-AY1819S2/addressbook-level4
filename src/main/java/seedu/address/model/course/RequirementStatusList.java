package seedu.address.model.course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * A list of requirement status
 */
public class RequirementStatusList {

    private final List<RequirementStatus> requirementStatusList;

    {
        requirementStatusList = new ArrayList<>();
    }

    public RequirementStatusList() {
    }

    /**
     * Updates with new Course Requirements
     */
    public void updateCourseRequirements(Course course, List<ModuleInfoCode> moduleInfoCodes,
                                         ObservableList<ModuleInfo> allModules) {
        requirementStatusList.clear();
        requirementStatusList.addAll(course.getCourseRequirements()
                .stream()
                .map(courseRequirement -> new RequirementStatus(courseRequirement, moduleInfoCodes, allModules))
                .collect(Collectors.toList()));
    }

    /**
     * Update moduleInfoCodes of each Requirement Status
     */
    public void updateModuleInfoCodes(List<ModuleInfoCode> moduleInfoCodes) {
        requirementStatusList.forEach(requirementStatus -> requirementStatus.updateModuleInfoCodes(moduleInfoCodes));
    }

    /**
     * Returns a unmodifiable copy of requirement status list
     */
    public ObservableList<RequirementStatus> getRequirementStatusList() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(requirementStatusList));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RequirementStatusList)) {
            return false;
        }

        RequirementStatusList another = (RequirementStatusList) other;
        return another.requirementStatusList.equals(this.requirementStatusList);
    }
}
