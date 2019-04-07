package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an appointment's doctor ID.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDoctorId(String)}
 */
public class AppointmentDoctorId {
    public static final String MESSAGE_CONSTRAINTS =
            "Doctor ID must a positive number.";

    public final int doctorId;

    /**
     * Constructs a {@code AppointmentDoctorId}.
     *
     * @param doctorId a valid doctor ID.
     */
    public AppointmentDoctorId(String doctorId) {
        requireNonNull(doctorId);
        checkArgument(isValidAppointmentDoctorId(doctorId), MESSAGE_CONSTRAINTS);
        this.doctorId = Integer.parseInt(doctorId);
    }

    /**
     * Returns true if a given string is a valid appointment doctor ID.
     */
    public static boolean isValidAppointmentDoctorId(String test) {
        int positiveNumber;
        try {
            positiveNumber = Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }

        if (positiveNumber > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentDoctorId // instanceof handles nulls
                && this.doctorId == ((AppointmentDoctorId) other).doctorId); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(doctorId);
    }


    @Override
    public String toString() {
        return Integer.toString(doctorId);
    }
}
