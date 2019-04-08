package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an appointment's date.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDate(String)}
 */
public class AppointmentDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Appointment date should be in the format YYYY-MM-DD and be a valid date on the calendar.";

    public final LocalDate date;

    /**
     * Constructs a {@code AppointmentDate}.
     *
     * @param date a valid date.
     */
    public AppointmentDate(String date) {
        requireNonNull(date);
        checkArgument(isValidAppointmentDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    /**
     * Returns true if a given string is a valid appointment date.
     */
    public static boolean isValidAppointmentDate(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(test, formatter);
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentDate // instanceof handles nulls
                && date.equals(((AppointmentDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }


    @Override
    public String toString() {
        return date.toString();
    }
}
