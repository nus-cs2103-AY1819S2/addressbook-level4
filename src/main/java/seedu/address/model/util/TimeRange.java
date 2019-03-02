package seedu.address.model.util;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a time range of format HH:00 - HH:00.
 */
public class TimeRange {

    private final LocalTime startTime;
    private final LocalTime endTime;

    public TimeRange(int startHour, int endHour) {
        this.startTime = LocalTime.of(startHour, 0);
        this.endTime = LocalTime.of(endHour, 0);
    }

    /**
     * Returns whether the other timing is inside this timing.
     * @param other The timing to check if exists inside this timing or not.
     */
    public boolean withinTiming(TimeRange other) {
        return (other.getStartTime().isAfter(this.startTime)
            || other.getStartTime().equals(this.startTime))
            && (other.getEndTime().isBefore(this.endTime)
            || other.getEndTime().equals(this.endTime));
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.startTime.format(formatter)
            + " - "
            + this.endTime.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeRange)) {
            return false;
        }
        TimeRange timeRange = (TimeRange) o;
        return getStartTime().equals(timeRange.getStartTime())
            && getEndTime().equals(timeRange.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartTime(), getEndTime());
    }

    public Iterable<TimeRange> getHourlySlots() {
        List<TimeRange> hourlySlots = new ArrayList<TimeRange>();
        for(int i = this.startTime.getHour(); i < this.endTime.getHour(); i++) {
            hourlySlots.add(new TimeRange(i, i + 1));
        }
        return hourlySlots;
    }

    public int numOfHours() {
        return this.endTime.getHour() - this.startTime.getHour();
    }
}
