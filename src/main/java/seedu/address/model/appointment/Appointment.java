package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import seedu.address.model.person.Person;


// TODO: Change Person to Patient
/**
 * Represents an appointment created in QuickDocs.
 */
public class Appointment {
    private final Person patient;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final String comment;

    public Appointment(Person patient, LocalDate date, LocalTime startTime, LocalTime endTime, String comment) {
        this.patient = patient;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }

    public Person getPatient() {
        return patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
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
                .append("Date: ")
                .append(getDate() + "\n")
                .append(" Start Time: ")
                .append(getStartTime() + "\n")
                .append(" End Time: ")
                .append(getEndTime() + "\n")
                .append(" Comments: ")
                .append(getComment() + "\n");
        return builder.toString();
    }
}
