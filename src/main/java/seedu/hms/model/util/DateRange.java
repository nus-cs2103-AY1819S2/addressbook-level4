package seedu.hms.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Represents a calendar range of format HH:00 - HH:00.
 */
public class DateRange {

    private final Calendar startDate;
    private final Calendar endDate;

    public DateRange(String startDate, String endDate) throws seedu.hms.logic.parser.exceptions.ParseException {
        String[] sd = startDate.split("/");
        String[] ed = endDate.split("/");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            format.parse(startDate);
            format.parse(endDate);
        } catch (ParseException e) {
            throw new seedu.hms.logic.parser.exceptions.ParseException("You have entered an invalid date");
        }
        this.startDate = Calendar.getInstance();
        this.startDate.set(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]), Integer.parseInt(sd[0]));
        this.endDate = Calendar.getInstance();
        this.endDate.set(Integer.parseInt(ed[2]), Integer.parseInt(ed[1]), Integer.parseInt(ed[0]));
    }

    public DateRange(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns whether the other timing is inside this timing.
     *
     * @param other The timing to check if exists inside this timing or not.
     */
    public boolean withinDates(DateRange other) {
        return (this.startDate.after(other.getStartDate())
            || this.startDate.equals(other.getStartDate()))
            && (this.endDate.before(other.getEndDate())
            || this.endDate.equals(other.getEndDate()));
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return startDate.get(Calendar.DATE) + "/" + (startDate.get(Calendar.MONTH) + 1) + "/"
            + startDate.get(Calendar.YEAR) + " - "
            + endDate.get(Calendar.DATE) + "/" + endDate.get(Calendar.MONTH) + 1 + "/" + endDate.get(Calendar.YEAR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DateRange)) {
            return false;
        }
        DateRange dateRange = (DateRange) o;
        return getStartDate().equals(dateRange.getStartDate())
            && getEndDate().equals(dateRange.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate());
    }

    public Iterable<DateRange> getEachDay() {
        List<DateRange> datelySlots = new ArrayList<DateRange>();
        Calendar startDate = (Calendar) this.startDate.clone();
        while (startDate.before(this.endDate)) {
            Calendar firstDate = (Calendar) startDate.clone();
            startDate.add(Calendar.DAY_OF_MONTH, 1);
            datelySlots.add(new DateRange(firstDate, startDate));
        }
        return datelySlots;
    }
}
