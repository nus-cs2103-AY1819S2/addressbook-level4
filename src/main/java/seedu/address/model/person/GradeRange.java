package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents expected grade range of a module taken
 */
public class GradeRange {
    private final Grade min;
    private final Grade max;

    public GradeRange(Grade min, Grade max) {
        this.min = min;
        this.max = max;
    }

    public Grade getMin() {
        return min;
    }

    public Grade getMax() {
        return max;
    }

    /**
     * Returns true if the min gradepoint is no more than max gradepoint
     */
    public boolean checkMinNotMoreThanMax() {
        return min.isLowerOrEqualTo(max);
    }

    /**
     * Returns true if both grade ranges have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GradeRange)) {
            return false;
        }

        GradeRange otherGradeRange = (GradeRange) other;
        return otherGradeRange.getMin().equals(getMin())
                && otherGradeRange.getMax().equals(getMax());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Min Grade: ")
                .append(getMin())
                .append(" Max Grade: ")
                .append(getMax());
        return builder.toString();
    }
}
