package seedu.address.model.datetime;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents the birth day of a patient.
 */
public class DateBase {
    private static final String[] MONTHS = {"", "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"};

    protected int day;
    protected int month;
    protected int year;

    protected DateBase() { }

    /**
     * Default constructor that takes in a birth day.
     * @param dob the dob in dd-mm-yyyy format.
     */
    protected DateBase(String dob) {
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
    public static boolean isValidDob(String test) {
        requireNonNull(test);
        String[] temp = test.split("-");
        if (temp.length != 3) {
            return false;
        } else {
            String day = temp[0].trim();
            String month = temp[1].trim();
            String year = temp[2].trim();
            boolean lengthCheck = year.length() == 4 && month.length() == 2 && day.length() == 2;

            Date today = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            int currentYear = cal.get(Calendar.YEAR);
            boolean contentCheck = Integer.parseInt(year) <= currentYear && Integer.parseInt(month) <= 12;

            int dayOfMonth = Integer.parseInt(day);
            boolean daysCheck;

            switch (Integer.parseInt(month)) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysCheck = dayOfMonth <= 31;
                break;
            case 2:
                daysCheck = dayOfMonth <= 29;
                break;
            default:
                daysCheck = dayOfMonth <= 30;
                break;
            }
            return lengthCheck && contentCheck && daysCheck;
        }
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
}
