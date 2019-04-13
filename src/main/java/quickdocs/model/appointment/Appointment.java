package quickdocs.model.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import quickdocs.model.Slot;
import quickdocs.model.patient.Patient;

/**
 * Represents an Appointment created in QuickDocs.
 */
public class Appointment extends Slot {
    private Patient patient;
    private String comment;

    public Appointment(Patient patient, LocalDate date, LocalTime start, LocalTime end, String comment) {
        super(date, start, end);
        this.patient = patient;
        this.comment = comment;
    }

    public Appointment() {
        super();
    }

    public Patient getPatient() {
        return patient;
    }

    public String getComment() {
        return comment;
    }

    /**
     * Generates a {@code String title} to be used in the creation of a {@code Reminder}
     *
     * @return the {@code String title} of this {@code Appointment}
     */
    public String createTitle() {
        return "Appointment with " + this.getPatient().getName() + ", " + this.getPatient().getNric();
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
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
        return super.equals(other)
                && otherApp.patient.equals(patient)
                && otherApp.comment.equals(comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getStart(), getEnd(), patient, comment);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPatient().getName()).append(" ")
                .append(getPatient().getNric()).append(", ")
                .append(getDate()).append(", ")
                .append(getStart())
                .append(" to ")
                .append(getEnd()).append(", ")
                .append(getComment());
        return builder.toString();
    }
}
