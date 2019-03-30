package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfoCode;

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
        return first.getCourseReqName();
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
    public boolean isFulfilled(List<ModuleInfoCode> moduleInfoCodes) {
        switch(connector) {
        case AND:
            return first.isFulfilled(moduleInfoCodes) && second.isFulfilled(moduleInfoCodes);
        case OR:
        default:
            return first.isFulfilled(moduleInfoCodes) || second.isFulfilled(moduleInfoCodes);
        }
    }


    @Override
    public boolean canFulfill(ModuleInfoCode moduleInfoCode) {
        switch(connector) {
        case AND:
            return first.canFulfill(moduleInfoCode) && second.canFulfill(moduleInfoCode);
        case OR:
        default:
            return first.canFulfill(moduleInfoCode) || second.canFulfill(moduleInfoCode);
        }
    }

    @Override
    public double getFulfilledPercentage(List<ModuleInfoCode> moduleInfoCode) {
        if (isFulfilled(moduleInfoCode)) {
            return 1;
        }
        switch(connector) {
        case OR:
            return Math.max(first.getFulfilledPercentage(moduleInfoCode),
                    second.getFulfilledPercentage(moduleInfoCode));
        case AND:
        default:
            //TODO:
            return 0;
        }
    }

    @Override
    public String getUnfulfilled(List<ModuleInfoCode> moduleInfoCodes) {
        switch(connector) {
        case AND:
            return "(" + first.getUnfulfilled(moduleInfoCodes) + ") AND ("
                    + second.getUnfulfilled(moduleInfoCodes) + ")";
        case OR:
        default:
            return "(" + first.getUnfulfilled(moduleInfoCodes) + ") OR ("
                    + second.getUnfulfilled(moduleInfoCodes) + ")";
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


    public CourseRequirement getFirst() {
        return first;
    }

    public CourseRequirement getSecond() {
        return second;
    }

    public LogicalConnector getLogicalConnector() {
        return connector;
    }

    public CourseReqType getCourseReqType() {
        return courseReqType;
    }
}
