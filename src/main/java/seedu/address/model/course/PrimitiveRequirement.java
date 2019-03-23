package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Represents a simple Course Requirement that is not composite or not connected by logical connectors.
 */
public class PrimitiveRequirement implements CourseRequirement {

    private final String courseReqName;
    private final String courseReqDesc;
    private final Condition condition;
    private final CourseReqType courseReqType;

    /**
     * Constructs a {@code  PrimitiveRequirement}
     * @param courseReqName name of requirement
     * @param courseReqDesc description of requirement
     * @param condition condition for which PrimitiveRequirement is satisfied
     */
    public PrimitiveRequirement(String courseReqName, String courseReqDesc,
                                   Condition condition, CourseReqType courseReqType) {
        requireAllNonNull(courseReqName, courseReqDesc, condition, courseReqType);
        this.courseReqName = courseReqName;
        this.courseReqDesc = courseReqDesc;
        this.condition = condition;
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
    public boolean isFulfilled(List<ModuleInfo> moduleInfos) {
        return condition.isSatisfied(moduleInfos);
    }

    @Override
    public boolean canFulfill(ModuleInfo moduleInfo) {
        return condition.canSatisfy(moduleInfo);
    }

    @Override
    public double getFulfilledPercentage(List<ModuleInfo> moduleInfos) {
        return condition.getPercentageCompleted(moduleInfos);
    }

    @Override
    public String getUnfulfilled(List<ModuleInfo> moduleInfos) {
        return condition.getUnsatisfied(moduleInfos);
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

    public Condition getCondition() {
        return condition;
    }
}
