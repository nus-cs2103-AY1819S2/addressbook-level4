package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Association class between {@code CourseRequirement} and {@code GradTrak}that stores
 * a snapshot of the completion of {@code CourseRequirement} object.
 */
public class RequirementStatus {

    private final CourseRequirement courseRequirement;
    private double percentageFulfilled;
    private boolean isFulfilled;

    public RequirementStatus(CourseRequirement courseRequirement,
                             List<ModuleInfoCode> moduleInfoCodes) {
        requireAllNonNull(courseRequirement, moduleInfoCodes);
        requireAllNonNull(moduleInfoCodes);
        this.courseRequirement = courseRequirement;
        this.updateRequirementStatus(moduleInfoCodes);
    }

    /**
     * Updates isFulfilled and percentageFulfilled based off same {@code CourseRequirement} but different
     * list of ModuleInfoCode
     */
    public void updateRequirementStatus(List<ModuleInfoCode> moduleInfoCodes) {
        this.isFulfilled = courseRequirement.isFulfilled(moduleInfoCodes);
        this.percentageFulfilled = courseRequirement.getFulfilledPercentage(moduleInfoCodes);

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
                && this.isFulfilled == another.isFulfilled
                && this.percentageFulfilled == another.percentageFulfilled;
    }
}
