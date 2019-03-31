package seedu.address.model.datetime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
<<<<<<< HEAD
public class TimeCustom implements Comparable<TimeCustom> {
    public static final String MESSAGE_CONSTRAINTS = "Time should only contain exactly 4 numbers,"
                                            + " the first two not going above 24, the latter two not going above 59.\n"
                                            + "Start time should not be after End time "
                                            + "if Start Date is equal to End Date"
                                            + "Example: 0015, 2359 or 1130.\n";
||||||| merged common ancestors
public class TimeCustom {
    public static final String MESSAGE_CONSTRAINTS = "TimeCustom should only contain exactly 4 numbers,"
        + " the first two not " + "going above 24, the latter two not going above 59.";
=======
public class TimeCustom {
    public static final String MESSAGE_CONSTRAINTS = "Time should only contain exactly 4 numbers,"
                                            + " the first two not going above 24, the latter two not going above 59.\n"
                                            + "Start time should not be after End time "
                                            + "if Start Date is equal to End Date"
                                            + "Example: 0015, 2359 or 1130.\n";
>>>>>>> 65d0b783941c6ac34db4849964fec51cf0db48fc

    public static final String VALIDATION_REGEX = "^([0-1][0-9]|2[0-3])([0-5][0-9])$";

    public final String storedTime;


    /**
     * Constructs a {@code time}
     *
     * @param time A valid time
     */
    public TimeCustom(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
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
        if (equals(t)) { return 0; }
        return timeCompare(storedTime, t.storedTime) ? 1 : -1;
    }

    @Override
    public int hashCode() {
        return storedTime.hashCode();
    }
}
