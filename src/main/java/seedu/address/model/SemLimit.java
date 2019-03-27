package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;

/**
 * Represents a SemLimit in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SemLimit {

    // Data fields
    private final Grade minGrade;
    private final Grade maxGrade;
    private final Hour minLectureHour;
    private final Hour maxLectureHour;

    /**
     * Every field must be present and not null.
     */
    public SemLimit(Grade minGrade, Grade maxGrade, Hour minLectureHour,
                       Hour maxLectureHour) {
        requireAllNonNull(minGrade, maxGrade, minLectureHour, maxLectureHour);
        this.minGrade = minGrade;
        this.maxGrade = minGrade;
        this.minLectureHour = minLectureHour;
        this.maxLectureHour = maxLectureHour;
    }

    public Grade getMinGrade() {
        return minGrade;
    }

    public Grade getMaxGrade() {
        return maxGrade;
    }

    public Hour getMinLectureHour() {
        return minLectureHour;
    }

    public Hour getMaxLectureHour() {
        return maxLectureHour;
    }

    /**
     * Returns true if both SemLimit have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SemLimit)) {
            return false;
        }

        SemLimit otherSemLimit = (SemLimit) other;
        return otherSemLimit.getMinGrade().equals(getMinGrade())
                && otherSemLimit.getMaxGrade().equals(getMaxGrade())
                && otherSemLimit.getMinLectureHour().equals(getMinLectureHour())
                && otherSemLimit.getMaxLectureHour().equals(getMaxLectureHour());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(minGrade, maxGrade, minLectureHour, maxLectureHour);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Min Grade: ")
                .append(getMinGrade())
                .append(" Max Grade: ")
                .append(getMaxGrade())
                .append(" Min Lecture Hour: ")
                .append(getMinLectureHour())
                .append(" Max Lecture Hour: ")
                .append(getMaxLectureHour());
        return builder.toString();
    }
}
