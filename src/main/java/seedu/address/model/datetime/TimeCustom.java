package seedu.address.model.datetime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class TimeCustom implements Comparable<TimeCustom> {

    public static final String MESSAGE_CONSTRAINTS_START_TIME = "Start time should only contain exactly 4 numbers,"
                                            + " the first two not going above 24, the latter two not going above 59.\n"
                                            + "Start time should not be after end time "
                                            + "if start date is equal to end date\n"
                                            + "Example: 0015, 2359 or 1130.";

    public static final String MESSAGE_CONSTRAINTS_END_TIME = "End time should only contain exactly 4 numbers,"
                                            + " the first two not going above 24, the latter two not going above 59.\n"
                                            + "Start time should not be after end time "
                                            + "if start date is equal to end date\n"
                                            + "Example: 0015, 2359 or 1130.";

    public static final String VALIDATION_REGEX = "^([0-1][0-9]|2[0-3])([0-5][0-9])$";

    public final String storedTime;


    /**
     * Constructs a {@code time}
     *
     * @param time A valid time
     */
    public TimeCustom(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS_START_TIME);
        storedTime = time;
    }

    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean timeCompare(String startTime, String endTime) {
        return (Integer.valueOf(startTime) >= Integer.valueOf(endTime));
    }

    @Override
    public String toString() {
        return storedTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TimeCustom // instanceof handles nulls
            && storedTime.equals(((TimeCustom) other).storedTime)); // state check
    }

    @Override
    public int compareTo(TimeCustom t) {
        if (equals(t)) {
            return 0;
        }
        return timeCompare(storedTime, t.storedTime) ? 1 : -1;
    }

    @Override
    public int hashCode() {
        return storedTime.hashCode();
    }
}
