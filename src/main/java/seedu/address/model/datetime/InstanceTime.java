package seedu.address.model.datetime;

/**
 * DateTime format for creation/modification
 */
public class InstanceTime extends DateBase {
    private final String instanceTime;

    public InstanceTime(String date, String time) {
        super(date);
        this.instanceTime = time;
    }

    public static String getCurrentDateTime () {
        return java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return super.getParsableFormat() + " " + instanceTime;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * First compares their base dates, only if a date occurs does it compare the time.
     * @param o The other InstanceTime object to compareTo
     */
    public int compareTo(InstanceTime o) {
        int result;
        result = super.compareTo(o);
        if (result == 0) {
            return this.instanceTime.compareTo(o.instanceTime);
        } else {
            return result;
        }
    }
}
