package seedu.address.model.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Date format for patients and records.
 */
public class DateBase implements DateBuilder {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should be in dd-MM-yyyy format";

    private int day;
    private int month;
    private int year;

    protected DateBase() { }

    /**
     * Default constructor that takes in a birth day.
     * @param dob the dob in dd-mm-yyyy format.
     */
    public DateBase(String dob) {
        String[] temp = dob.split("-");
        int day = Integer.parseInt(temp[0].trim());
        int month = Integer.parseInt(temp[1].trim());
        int year = Integer.parseInt(temp[2].trim());
        setTo(day, month, year);
    }

    /**
     * Returns true if a given string is a valid DOB.
     * @param test the string to be tested.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Sets the birth day of a patient to a specified date.
     * @param day the day of birth.
     * @param month the month of birth.
     * @param year the year of birth.
     */
    public void setTo(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     *  Returns true if the first date given is before the second date given
     * @param d1 the first date to comapre with the second date
     * @param d2 the second date
     * @return true if first date is before, false otherwise.
     */
    static boolean dateCompare(String d1, String d2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate firstDate = LocalDate.parse(d1, formatter);
        LocalDate secondDate = LocalDate.parse(d2, formatter);
        return firstDate.isBefore(secondDate);
    }

    /**
     * Gets the birth day of the patient in DD MMMM YYYY format.
     * @return the birth day.
     */
    public String getDate() {
        return day + " " + MONTHS[month] + " " + year;
    }

    /**
     * Gets the birth day of the patient in dd-mm-yyyy format.
     * @return the birth day in the specified format.
     */
    public String getRawFormat() {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedYear = String.format("%04d", year);
        return formattedDay + "-" + formattedMonth + "-" + formattedYear;
    }

    /**
     * Gets a date format that can be parsed into local date class.
     * @return the parsable format to be used with local date class.
     */
    public String getParsableFormat() {
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);
        String formattedYear = String.format("%04d", year);
        return formattedYear + "-" + formattedMonth + "-" + formattedDay;
    }

    /**
     * Get today's date in a DateBase instance form.
     * @return today's date in DateBase.
     */
    public static DateBase getToday() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        String formattedDay = String.format("%02d", currentDay);
        String formattedMonth = String.format("%02d", currentMonth);
        String formattedYear = String.format("%04d", currentYear);
        return new DateBase(formattedDay + "-" + formattedMonth + "-" + formattedYear);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof DateBase) {
            return ((DateBase) o).day == this.day
                    && ((DateBase) o).month == this.month
                    && ((DateBase) o).year == this.year;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getRawFormat();
    }

    /**
     * Creates a hash based on the raw input, which is in dd-mm-yyyy format.
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return this.getRawFormat().hashCode();
    }
}
