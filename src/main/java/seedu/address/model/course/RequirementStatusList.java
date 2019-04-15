package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
     * Updates RequirementStatus list with RequirementStatus containing new Course Requirements
     */
    public void updateRequirementStatus(Course course, List<ModuleInfoCode> moduleInfoCodes) {
        requireAllNonNull(course, moduleInfoCodes);
        requireAllNonNull(moduleInfoCodes);
        requirementStatusList.clear();
        requirementStatusList.addAll(course.getCourseRequirements()
                .stream()
                .map(courseRequirement -> new RequirementStatus(courseRequirement, moduleInfoCodes))
                .collect(Collectors.toList()));
    }

    /**
     * Updates each RequirementStatus with new ModuleInfoCodes
     */
    public void updateRequirementStatus(List<ModuleInfoCode> moduleInfoCodes) {
        requirementStatusList.forEach(requirementStatus -> requirementStatus.updateRequirementStatus(moduleInfoCodes));
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
