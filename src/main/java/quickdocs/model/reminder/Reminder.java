package quickdocs.model.reminder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import quickdocs.model.Slot;

/**
 * Represents a Reminder created in quickdocs.
 */
public class Reminder extends Slot {
    private String title;
    private String comment;

    public Reminder() {
        super();
    }

    public Reminder(String title, LocalDate date, LocalTime start) {
        super(date, start, null);
        this.title = title;
        this.comment = "";
    }

    public Reminder(String title, String comment, LocalDate date, LocalTime start) {
        super(date, start, null);
        this.title = title;
        this.comment = comment;
    }

    public Reminder(String title, LocalDate date, LocalTime start, LocalTime end) {
        super(date, start, end);
        this.title = title;
        this.comment = "";
    }

    public Reminder(String title, String comment, LocalDate date, LocalTime start, LocalTime end) {
        super(date, start, end);
        this.title = title;
        this.comment = comment;

    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
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
        return super.equals(other)
                && otherReminder.getTitle().equals(getTitle());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, super.hashCode());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle()).append(":\n")
                .append("Date: ")
                .append(getDate()).append("\n")
                .append("Time: ")
                .append(getStart());

        if (getEnd() != null) {
            builder.append(" to ").append(getEnd());
        }
        builder.append("\n");

        if (comment != null) {
            builder.append("Comments: ").append(getComment()).append("\n");
        }
        return builder.toString();
    }
}
