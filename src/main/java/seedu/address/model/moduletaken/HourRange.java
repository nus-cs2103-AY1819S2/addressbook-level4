package seedu.address.model.moduletaken;

/**
 * Represents expected grade range of a module taken
 */
public class HourRange {
    private Hour min;
    private Hour max;

    public Hour getMin() {
        return min;
    }

    public Hour getMax() {
        return max;
    }

    public void setMin(Hour min) {
        this.min = min;
    }

    public void setMax(Hour max) {
        this.max = max;
    }

    /**
     * Returns true if the min hour limit is no more than max hour limit
     */
    public boolean checkMinNotMoreThanMax() {
        return min.isWithin(max);
    }
}
