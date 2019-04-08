package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.PersonId;

/**
 * Represents an appointment's patient ID.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentPatientId(String)}
 */
public class AppointmentPatientId extends PersonId {
    public static final String MESSAGE_CONSTRAINTS =
            "Patient ID must a positive number.";

    public final PersonId patientId;

    /**
     * Constructs a {@code AppointmentPatientId}.
     *
     * @param patientId a valid patient ID.
     */
    public AppointmentPatientId(String patientId) {
        super(patientId);
        requireNonNull(patientId);
        checkArgument(isValidAppointmentPatientId(patientId), MESSAGE_CONSTRAINTS);
        this.patientId = new PersonId(patientId);
    }

    /**
     * Returns true if a given string is a valid appointment patient ID.
     */
    public static boolean isValidAppointmentPatientId(String test) {
        return PersonId.isValidPersonId(test);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentPatientId // instanceof handles nulls
                && this.patientId.equals(((AppointmentPatientId) other).patientId)); // state check
    }

    @Override
    public int hashCode() {
        return patientId.hashCode();
    }


    @Override
    public String toString() {
        return patientId.toString();
    }
}
