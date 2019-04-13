package quickdocs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents a time slot in a day, from a start time to an optional end time.
 */
public class Slot implements Comparable<Slot> {
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    public Slot (LocalDate date, LocalTime start, LocalTime end) {
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public Slot() {
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public int compareTo(Slot other) {
        if (date.equals(other.getDate())) {
            return start.compareTo(other.getStart());
        } else {
            return date.compareTo(other.getDate());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, start, end);
    }

    /**
     * Returns true if both slots have the same identity and data fields.
     * This defines a stronger notion of equality between two slots.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (!(other.getClass() == getClass())) {
            return false;
        }

        Slot otherSlot = (Slot) other;
        // Objects.equals() to handle null fields
        return otherSlot.date.equals(date)
                && otherSlot.start.equals(start)
                && Objects.equals(otherSlot.end, end);
    }
}
