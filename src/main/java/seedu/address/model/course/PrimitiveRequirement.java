package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Represents a simple Course Requirement that is not composite or not connected by logical connectors.
 */
public class PrimitiveRequirement implements CourseRequirement {

    private final String courseReqName;
    private final String courseReqDesc;
    private final List<Condition> conditions;
    private final CourseReqType courseReqType;

    /**
     * Constructs a {@code  PrimitiveRequirement}
     * @param courseReqName name of requirement
     * @param courseReqDesc description of requirement
     * @param conditions list of condition for which PrimitiveRequirement is satisfied
     *                   only if all conditions are satisfied
     */
    public PrimitiveRequirement(String courseReqName, String courseReqDesc,
                                   CourseReqType courseReqType, Condition... conditions) {
        requireAllNonNull(courseReqName, courseReqDesc, courseReqType, conditions);
        requireAllNonNull(List.of(conditions));
        this.courseReqName = courseReqName;
        this.courseReqDesc = courseReqDesc;
        this.conditions = List.of(conditions);
        this.courseReqType = courseReqType;
    }

    @Override
    public CourseReqType getType() {
        return courseReqType;
    }

    @Override
    public String getCourseReqName() {
        return courseReqName;
    }

    @Override
    public String getCourseReqDesc() {
        return courseReqDesc;
    }

    @Override
    public boolean isFulfilled(List<ModuleInfoCode> moduleInfoCodes) {
        return conditions.stream().allMatch(condition -> condition.isSatisfied(moduleInfoCodes));
    }

    @Override
    public boolean canFulfill(ModuleInfoCode moduleInfoCode) {
        return conditions.stream().anyMatch(condition -> condition.canSatisfy(moduleInfoCode));
    }

    @Override
    public double getFulfilledPercentage(List<ModuleInfoCode> moduleInfoCodes) {
        return conditions.stream()
                .mapToInt(condition -> condition.getNumCompleted(moduleInfoCodes))
                .sum() / (double) conditions.stream().mapToInt(condition -> condition.getMinToSatisfy()).sum();
    }

    @Override
    public List<String> getUnfulfilled(List<ModuleInfoCode> moduleInfoCodes) {
        return conditions.stream()
                .filter(condition -> !condition.isSatisfied(moduleInfoCodes))
                .map(condition -> condition.getPattern().toString())
                .collect(Collectors.toList());
    }

    @Override
    public CourseRequirement and(CourseRequirement other) {
        return new CompositeRequirement(this, other, CompositeRequirement.LogicalConnector
                .AND, this.courseReqType);
    }

    @Override
    public CourseRequirement or(CourseRequirement other) {
        return new CompositeRequirement(this, other, CompositeRequirement.LogicalConnector
                .OR, this.courseReqType);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PrimitiveRequirement)) {
            return false;
        }

        PrimitiveRequirement other = (PrimitiveRequirement) obj;
        return this.courseReqName.equals(other.courseReqName)
                && this.courseReqType.equals(other.courseReqType)
                && this.courseReqDesc.equals(other.courseReqDesc)
                && this.conditions.equals(other.conditions);
    }
}
