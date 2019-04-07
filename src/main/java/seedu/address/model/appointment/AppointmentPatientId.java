package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an appointment's patient ID.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentPatientId(String)}
 */
public class AppointmentPatientId {
    public static final String MESSAGE_CONSTRAINTS =
            "Patient ID must a positive number.";

    public final int patientId;

    /**
     * Constructs a {@code AppointmentPatientId}.
     *
     * @param patientId a valid patient ID.
     */
    public AppointmentPatientId(String patientId) {
        requireNonNull(patientId);
        checkArgument(isValidAppointmentPatientId(patientId), MESSAGE_CONSTRAINTS);
        this.patientId = Integer.parseInt(patientId);
    }

    /**
     * Returns true if a given string is a valid appointment patient ID.
     */
    public static boolean isValidAppointmentPatientId(String test) {
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
                || (other instanceof AppointmentPatientId // instanceof handles nulls
                && this.patientId == ((AppointmentPatientId) other).patientId); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(patientId);
    }


    @Override
    public String toString() {
        return Integer.toString(patientId);
    }
}
