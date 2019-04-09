package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an appointment's time.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentTime(String)}
 */
public class AppointmentTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Appointment time should be in the 24 hour format HH:mm"
                    + " and only in exact 1 hour increments." + "\n"
                    + "Doctors are only free between 09:00 to 11:00 and 13:00 to 17:00." + "\n"
                    + "Examples include 09:00, 10:00, 13:00, 15:00";

    public final LocalTime time;

    /**
     * Constructs a {@code AppointmentTime}.
     *
     * @param time a valid time.
     */
    public AppointmentTime(String time) {
        requireNonNull(time);
        checkArgument(isValidAppointmentTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time);
    }

    /**
     * Returns true if a given string is a valid appointment time.
     */
    public static boolean isValidAppointmentTime(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime time = LocalTime.parse(test, formatter);
            if (time.getMinute() != 0) {
                return false;
            }
            if (!isOfficeHours(time)) {
                return false;
            }
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if the time is between the valid office hours
     */
    private static boolean isOfficeHours(LocalTime time) {
        LocalTime period1Start = LocalTime.parse("08:59");
        LocalTime period1End = LocalTime.parse("11:01");

        LocalTime period2Start = LocalTime.parse("12:59");
        LocalTime period2End = LocalTime.parse("17:01");

        final boolean withinPeriod1 = time.isAfter(period1Start) && time.isBefore(period1End);
        final boolean withinPeriod2 = time.isAfter(period2Start) && time.isBefore(period2End);

        return withinPeriod1 || withinPeriod2;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentTime // instanceof handles nulls
                && time.equals(((AppointmentTime) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }


    @Override
    public String toString() {
        return time.toString();
    }
}
