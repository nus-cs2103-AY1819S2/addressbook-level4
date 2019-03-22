package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Represents a composite Course Requirement that is connected by logical connectors.
 */
public class CompositeRequirement implements CourseRequirement {

    /**
     * Represents logical connectors of CompositeRequirement
     */
    public enum LogicalConnector {
        AND, OR
    }

    private final CourseRequirement first;
    private final CourseRequirement second;
    private final LogicalConnector connector;
    private final CourseReqType courseReqType;

    /**
     * Constructs a {@code CompositeRequirement}
     * @param first first requirement to check
     * @param second second requirement to check
     * @param connector logical connector to combine (either AND or OR);
     */
    public CompositeRequirement(CourseRequirement first, CourseRequirement second,
                                LogicalConnector connector, CourseReqType courseReqType) {
        requireAllNonNull(first, second, connector, courseReqType);
        this.first = first;
        this.second = second;
        this.connector = connector;
        this.courseReqType = courseReqType;
    }


    @Override
    public CourseReqType getType() {
        return courseReqType;
    }

    @Override
    public String getCourseReqName() {
        switch(connector) {
        case AND:
            return "(" + first.getCourseReqName() + ") AND (" + second.getCourseReqName() + ")";
        case OR:
        default:
            return "(" + first.getCourseReqName() + ") OR (" + second.getCourseReqName() + ")";
        }
    }

    @Override
    public String getCourseReqDesc() {
        switch(connector) {
        case AND:
            return "(" + first.getCourseReqDesc() + ") AND (" + second.getCourseReqDesc() + ")";
        case OR:
        default:
            return "(" + first.getCourseReqDesc() + ") OR (" + second.getCourseReqDesc() + ")";
        }
    }

    @Override
    public boolean isFulfilled(List<ModuleInfo> moduleInfos) {
        switch(connector) {
        case AND:
            return first.isFulfilled(moduleInfos) && second.isFulfilled(moduleInfos);
        case OR:
        default:
            return first.isFulfilled(moduleInfos) || second.isFulfilled(moduleInfos);
        }
    }


    @Override
    public boolean canFulfill(ModuleInfo moduleInfo) {
        switch(connector) {
        case AND:
            return first.canFulfill(moduleInfo) && second.canFulfill(moduleInfo);
        case OR:
        default:
            return first.canFulfill(moduleInfo) || second.canFulfill(moduleInfo);
        }
    }

    @Override
    public double getFulfilledPercentage(List<ModuleInfo> moduleInfos) {
        //TODO: find a good way to return percentage
        return 0;
    }

    @Override
    public String getUnfulfilled(List<ModuleInfo> moduleInfos) {
        switch(connector) {
        case AND:
            return "(" + first.getUnfulfilled(moduleInfos) + ") AND (" + second.getUnfulfilled(moduleInfos) + ")";
        case OR:
        default:
            return "(" + first.getUnfulfilled(moduleInfos) + ") OR (" + second.getUnfulfilled(moduleInfos) + ")";
        }
    }

    @Override
    public CourseRequirement and(CourseRequirement other) {
        return new CompositeRequirement(this, other, LogicalConnector.AND, this.courseReqType);
    }

    @Override
    public CourseRequirement or(CourseRequirement other) {
        return new CompositeRequirement(this, other, LogicalConnector.AND, this.courseReqType);
    }
}
