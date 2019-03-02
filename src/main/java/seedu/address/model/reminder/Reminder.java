package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a Reminder created in quickdocs.
 */
public class Reminder {
    private final String title;
    private final String comment;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public Reminder(String title, String comment, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.title = title;
        this.comment = comment;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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
                && otherReminder.getComment().equals(getComment())
                && otherReminder.getDate().equals(getDate())
                && otherReminder.getStartTime().equals(getStartTime())
                && otherReminder.getEndTime().equals(getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, comment, date, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle() + ":\n")
                .append("Date: ")
                .append(getDate() + "\n")
                .append("Start Time: ")
                .append(getStartTime() + "\n")
                .append("End Time: ")
                .append(getEndTime() + "\n")
                .append("Comments: ")
                .append(getComment() + "\n");
        return builder.toString();
    }
}
