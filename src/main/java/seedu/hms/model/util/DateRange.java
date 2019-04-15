package seedu.hms.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        this.startDate.set(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
        this.endDate = Calendar.getInstance();
        this.endDate.set(Integer.parseInt(ed[2]), Integer.parseInt(ed[1]) - 1, Integer.parseInt(ed[0]));
        if (!isValidDate(this.startDate) || !isValidDate(this.endDate)) {
            throw new seedu.hms.logic.parser.exceptions.ParseException(
                "Date should be after current date and within one year after current date\n"
                    + "Invalid date:" + (!isValidDate(this.startDate)
                    ? this.startDate.getTime() : this.endDate.getTime()) + "\n"
                    + "Current date:" + Calendar.getInstance().getTime());
        }
        if (this.numOfDays() < 0) {
            throw new seedu.hms.logic.parser.exceptions.ParseException("Your end date should be after the start date");
        }
    }

    public DateRange(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns whether this date is inside the other date
     *
     * @param other The date to check if exists inside this date or not.
     */
    public boolean withinDates(DateRange other) {
        return dateCompare(this.startDate, other.startDate) >= 0
            && dateCompare(this.endDate, other.endDate) <= 0;
    }

    /**
     *
     * @param cA Calendar a
     * @param cB Calendar b
     * @return -1 if a is before b
     *          0 if a is the same date as b
     *          1 if a is after b
     */
    public static int dateCompare(Calendar cA, Calendar cB) {
        int dayA = cA.get(Calendar.DAY_OF_MONTH);
        int monthA = cA.get(Calendar.MONTH);
        int yearA = cA.get(Calendar.YEAR);
        int dayB = cB.get(Calendar.DAY_OF_MONTH);
        int monthB = cB.get(Calendar.MONTH);
        int yearB = cB.get(Calendar.YEAR);

        if (yearA < yearB) {
            return -1;
        } else if (yearA > yearB) {
            return 1;
        }

        if (monthA < monthB) {
            return -1;
        } else if (monthA > monthB) {
            return 1;
        }

        if (dayA < dayB) {
            return -1;
        } else if (dayA > dayB) {
            return 1;
        }

        return 0;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * Returns whether the date is valid
     * The date should be 1.after current date
     * 2.within in 1 year after current date
     *
     * @return
     */
    public static boolean isValidDate(Calendar dateTested) {
        long diff = dateTested.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        long diffDays = diff / 1000 / 60 / 60 / 24;
        return diffDays >= 0 && diffDays < 365;
    }

    @Override
    public String toString() {
        return startDate.get(Calendar.DATE) + "/" + (startDate.get(Calendar.MONTH) + 1) + "/"
            + startDate.get(Calendar.YEAR) + " - "
            + endDate.get(Calendar.DATE) + "/" + (endDate.get(Calendar.MONTH) + 1) + "/" + endDate.get(Calendar.YEAR);
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

    /**
     * Generates the number of days the reservation was for
     */
    public long numOfDays() {
        Date startDate = this.startDate.getTime();
        Date endDate = this.endDate.getTime();
        long diff = endDate.getTime() - startDate.getTime();
        return diff / 1000 / 60 / 60 / 24;
    }
}
