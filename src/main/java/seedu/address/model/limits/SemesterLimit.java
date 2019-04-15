package seedu.address.model.limits;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * Represents a SemesterLimit in GradTrak.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SemesterLimit {

    // Data fields
    private final CapAverage minCap;
    private final CapAverage maxCap;
    private final Hour minLectureHour;
    private final Hour maxLectureHour;
    private final Hour minTutorialHour;
    private final Hour maxTutorialHour;
    private final Hour minLabHour;
    private final Hour maxLabHour;
    private final Hour minProjectHour;
    private final Hour maxProjectHour;
    private final Hour minPreparationHour;
    private final Hour maxPreparationHour;

    /**
     * Every field must be present and not null.
     */
    public SemesterLimit(CapAverage minCap, CapAverage maxCap, Hour minLectureHour, Hour maxLectureHour,
                         Hour minTutorialHour, Hour maxTutorialHour, Hour minLabHour, Hour maxLabHour,
                         Hour minProjectHour, Hour maxProjectHour, Hour minPreparationHour, Hour maxPreparationHour) {
        requireAllNonNull(minCap, maxCap, minLectureHour, maxLectureHour, minTutorialHour, maxTutorialHour,
                minLabHour, maxLabHour, minProjectHour, maxProjectHour, maxPreparationHour);
        this.minCap = minCap;
        this.maxCap = maxCap;
        this.minLectureHour = minLectureHour;
        this.maxLectureHour = maxLectureHour;
        this.minTutorialHour = minTutorialHour;
        this.maxTutorialHour = maxTutorialHour;
        this.minLabHour = minLabHour;
        this.maxLabHour = maxLabHour;
        this.minProjectHour = minProjectHour;
        this.maxProjectHour = maxProjectHour;
        this.minPreparationHour = minPreparationHour;
        this.maxPreparationHour = maxPreparationHour;
    }

    public CapAverage getMinCap() {
        return minCap;
    }

    public CapAverage getMaxCap() {
        return maxCap;
    }

    public Hour getMinLectureHour() {
        return minLectureHour;
    }

    public Hour getMaxLectureHour() {
        return maxLectureHour;
    }

    public Hour getMinTutorialHour() {
        return minTutorialHour;
    }

    public Hour getMaxTutorialHour() {
        return maxTutorialHour;
    }

    public Hour getMinLabHour() {
        return minLabHour;
    }

    public Hour getMaxLabHour() {
        return maxLabHour;
    }

    public Hour getMinProjectHour() {
        return minProjectHour;
    }

    public Hour getMaxProjectHour() {
        return maxProjectHour;
    }

    public Hour getMinPreparationHour() {
        return minPreparationHour;
    }

    public Hour getMaxPreparationHour() {
        return maxPreparationHour;
    }

    /**
     * Returns true if both SemesterLimit have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SemesterLimit)) {
            return false;
        }

        SemesterLimit otherSemesterLimit = (SemesterLimit) other;
        return otherSemesterLimit.getMinCap().equals(getMinCap())
                && otherSemesterLimit.getMaxCap().equals(getMaxCap())
                && otherSemesterLimit.getMinLectureHour().equals(getMinLectureHour())
                && otherSemesterLimit.getMaxLectureHour().equals(getMaxLectureHour())
                && otherSemesterLimit.getMinTutorialHour().equals(getMinTutorialHour())
                && otherSemesterLimit.getMaxTutorialHour().equals(getMaxTutorialHour())
                && otherSemesterLimit.getMinLabHour().equals(getMinLabHour())
                && otherSemesterLimit.getMaxLabHour().equals(getMaxLabHour())
                && otherSemesterLimit.getMinProjectHour().equals(getMinProjectHour())
                && otherSemesterLimit.getMaxProjectHour().equals(getMaxProjectHour())
                && otherSemesterLimit.getMinPreparationHour().equals(getMinPreparationHour())
                && otherSemesterLimit.getMaxPreparationHour().equals(getMaxPreparationHour());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(minCap, maxCap, minLectureHour, maxLectureHour);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" \nMin CAP: ")
                .append(getMinCap())
                .append(" , Max CAP: ")
                .append(getMaxCap())
                .append(" \nMin Lecture Hour: ")
                .append(getMinLectureHour())
                .append(" , Max Lecture Hour: ")
                .append(getMaxLectureHour())
                .append(" \nMin Tutorial Hour: ")
                .append(getMinTutorialHour())
                .append(" , Max Tutorial Hour: ")
                .append(getMaxTutorialHour())
                .append(" \nMin Lab Hour: ")
                .append(getMinLabHour())
                .append(" , Max Lab Hour: ")
                .append(getMaxLabHour())
                .append(" \nMin Project Hour: ")
                .append(getMinProjectHour())
                .append(" , Max Project Hour: ")
                .append(getMaxProjectHour())
                .append(" \nMin Preparation Hour: ")
                .append(getMinPreparationHour())
                .append(" , Max Preparation Hour: ")
                .append(getMaxPreparationHour());
        return builder.toString();
    }
}
