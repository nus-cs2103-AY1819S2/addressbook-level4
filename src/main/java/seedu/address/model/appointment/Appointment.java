package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.model.person.Person;


// TODO: Change Person to Patient
/**
 * Represents an appointment created in QuickDocs.
 */
public class Appointment {
    private final Person patient;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    //private final String description;

    public Appointment(Person patient, LocalDateTime startTime, LocalDateTime endTime) {
        this.patient = patient;
        this.startTime = startTime;
        this.endTime = endTime;
        //this.description = description;
    }

    public Person getPatient() {
        return patient;
    }

    //public String getDescription() {
    // return description;
    //}

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherApp = (Appointment) other;
        return otherApp.getPatient().equals(getPatient())
                && otherApp.getStartTime().equals(getStartTime())
                && otherApp.getEndTime().equals(getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patient, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPatient())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime());
        return builder.toString();
    }
}
