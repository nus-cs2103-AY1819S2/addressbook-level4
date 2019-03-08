package seedu.address.model.patient;

/**
 * Represents the birth day of a patient.
 */
public class DateOfBirth {
    private int day;
    private int month;
    private int year;

    /**
     * Default constructor that takes in a birth day.
     * @param day the day of birth.
     * @param month the month of birth.
     * @param year the year of birth.
     */
    public DateOfBirth(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
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
