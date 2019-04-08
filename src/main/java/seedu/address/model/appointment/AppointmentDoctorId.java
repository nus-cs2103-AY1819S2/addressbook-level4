package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.PersonId;

/**
 * Represents an appointment's doctor ID.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDoctorId(String)}
 */
public class AppointmentDoctorId extends PersonId {
    public static final String MESSAGE_CONSTRAINTS =
            "Doctor ID must a positive number.";

    public final PersonId doctorId;

    /**
     * Constructs a {@code AppointmentDoctorId}.
     *
     * @param doctorId a valid doctor ID.
     */
    public AppointmentDoctorId(String doctorId) {
        super(doctorId);
        requireNonNull(doctorId);
        checkArgument(isValidAppointmentDoctorId(doctorId), MESSAGE_CONSTRAINTS);
        this.doctorId = new PersonId(doctorId);
    }

    /**
     * Returns true if a given string is a valid appointment doctor ID.
     */
    public static boolean isValidAppointmentDoctorId(String test) {
        return PersonId.isValidPersonId(test);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentDoctorId // instanceof handles nulls
                && this.doctorId.equals(((AppointmentDoctorId) other).doctorId)); // state check
    }

    @Override
    public int hashCode() {
        return doctorId.hashCode();
    }


    @Override
    public String toString() {
        return doctorId.toString();
    }
}
