package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import seedu.address.model.appointment.Appointment;

/**
 * Represents a Reminder created in quickdocs.
 */
public class Reminder {
    private String title;
    private String comment;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Reminder() {
    }

    public Reminder(String title, String comment, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reminder(Appointment app) {
        this.title = createTitle(app);
        this.comment = app.getComment();
        this.date = app.getDate();
        this.startTime = app.getStartTime();
        this.endTime = app.getEndTime();
    }

    /**
     * Returns a {@code String} title given an {@code Appointment}
     */
    public static String createTitle(Appointment app) {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment with ")
                .append(app.getPatient().getName());
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
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

    /**
     * Returns true if both reminders have the same identity and data fields.
     * This defines a stronger notion of equality between two reminders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getTitle().equals(getTitle())
                && otherReminder.getDate().equals(getDate())
                && otherReminder.getStartTime().equals(getStartTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, date, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + ":\n")
                .append("Date: ")
                .append(getDate() + "\n")
                .append("Start Time: ")
                .append(getStartTime() + "\n");
        if (endTime != null) {
            builder.append("End Time: ").append(getEndTime() + "\n");
        }
        if (comment != null) {
            builder.append("Comments: ").append(getComment() + "\n");
        }
        return builder.toString();
    }
}
