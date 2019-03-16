package seedu.address.model.person;

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
        return min.isWithin(max);
    }
}
