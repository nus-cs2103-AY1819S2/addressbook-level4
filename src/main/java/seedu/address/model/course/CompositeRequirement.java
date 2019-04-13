package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Represents a composite Course Requirement that is connected by logical connectors.
 */
public class CompositeRequirement implements CourseRequirement {
    //TODO: Refine implementation of Course Requirement to store more than 2 modules

    /**
     * Represents logical connectors of CompositeRequirement
     */
    public enum LogicalConnector {
        AND, OR
    }

    private final String courseReqName;
    private final String courseReqDesc;
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
        this.courseReqName = first.getCourseReqName();
        switch(connector) {
        case AND:
            this.courseReqDesc = first.getCourseReqDesc() + " "
                + LogicalConnector.AND + " " + second.getCourseReqDesc();
            break;
        case OR:
        default:
            this.courseReqDesc = first.getCourseReqDesc() + " "
                + LogicalConnector.OR + " " + second.getCourseReqDesc();
        }
        this.first = first;
        this.second = second;
        this.connector = connector;
        this.courseReqType = courseReqType;
    }

    /**
     * Private constructor for a {@code CompositeRequirement}
     * allows to set name and courseReq desc;
     * @param first first requirement to check
     * @param second second requirement to check
     * @param connector logical connector to combine (either AND or OR);
     */
    private CompositeRequirement(String courseReqName, String courseReqDesc,
                                 CourseRequirement first, CourseRequirement second,
                                 LogicalConnector connector, CourseReqType courseReqType) {
        requireAllNonNull(first, second, connector, courseReqType);
        this.courseReqName = courseReqName;
        this.courseReqDesc = courseReqDesc;
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
        return courseReqName;
    }

    @Override
    public String getCourseReqDesc() {
        return courseReqDesc;
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
        return first.canFulfill(moduleInfoCode) || second.canFulfill(moduleInfoCode);
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
            //TODO: "AND" does not reveal the true rate of completion.
            // Perhaps use some method to get aggregate min to Satisfy
            return (first.getFulfilledPercentage(moduleInfoCode) + second.getFulfilledPercentage(moduleInfoCode)) / 2.0;
        }
    }

    @Override
    public List<String> getUnfulfilled(List<ModuleInfoCode> moduleInfoCodes) {
        List<String> unfulfilledRegexes = first.getUnfulfilled(moduleInfoCodes);
        unfulfilledRegexes.addAll(second.getUnfulfilled(moduleInfoCodes));
        return unfulfilledRegexes;
    }

    @Override
    public CourseRequirement and(CourseRequirement other) {
        switch (connector) {
        case AND:
            return new CompositeRequirement(courseReqName, courseReqDesc + " " + connector + " "
                + other.getCourseReqDesc(), this, other, connector, courseReqType);
        case OR:
        default:
            return new CompositeRequirement(courseReqName, "(" + courseReqDesc + ") " + connector + " "
                    + other.getCourseReqDesc(), this, other, connector, courseReqType);
        }
    }

    @Override
    public CourseRequirement or(CourseRequirement other) {
        switch (connector) {
        case OR:
            return new CompositeRequirement(courseReqName, courseReqDesc + " " + connector + " "
                + other.getCourseReqDesc(), this, other, connector, courseReqType);
        case AND:
        default:
            return new CompositeRequirement(courseReqName, "(" + courseReqDesc + ") " + connector + " "
                + other.getCourseReqDesc(), this, other, connector, courseReqType);
        }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CompositeRequirement)) {
            return false;
        }

        CompositeRequirement other = (CompositeRequirement) obj;

        return ((this.first.equals(other.first) && this.second.equals(other.second))
                || (this.first.equals(other.second) && this.second.equals(other.first)))
                && this.courseReqType.equals(other.courseReqType)
                && this.connector.equals(other.connector);
    }
}
