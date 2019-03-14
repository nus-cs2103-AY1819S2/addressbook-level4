package seedu.address.model.person;

/**
 * Represents expected grade range of a module taken
 */
public class GradeRange {
    private Grade min;
    private Grade max;

    public boolean checkMinNotMoreThanMax(Grade max) {
        return min.isWithin(max);
    }
}
