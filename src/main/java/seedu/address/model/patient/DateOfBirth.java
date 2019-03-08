package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;

/**
 * Represents the birth day of a patient.
 */
public class DateOfBirth {
    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth is compulsory, denoted by \\dob and should be in standard format.";
    private static final String[] MONTHS = {"", "January", "February", "March", "April", "May", "June", "July",
        "August", "September", "October", "November", "December"};

    private int day;
    private int month;
    private int year;

    /**
     * Default constructor that takes in a birth day.
     * @param dob the dob in yyyy-mm-dd format.
     */
    public DateOfBirth(String dob) {
        String[] temp = dob.split("-");
        int year = Integer.parseInt(temp[0].trim());
        int month = Integer.parseInt(temp[1].trim());
        int day = Integer.parseInt(temp[2].trim());
        setTo(day, month, year);
    }

    /**
     * Returns true if a given string is a valid DOB.
     * @param test the string to be tested.
     */
    public static boolean isValidDob(String test) {
        requireNonNull(test);
        try {
            String[] temp = test.split("-");
            String year = temp[0].trim();
            String month = temp[1].trim();
            String day = temp[2].trim();
            assert year.length() == 4 : "Year length is not 4";
            assert month.length() == 2 : "Month length is not 2";
            assert day.length() == 2 : "Day length is not 2";
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets the birth day of a patient to a specified date.
     * @param day the day of birth.
     * @param month the month of birth.
     * @param year the year of birth.
     */
    void setTo(int day, int month, int year) {
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
     * Calculates the patient's age.
     * @return the patient's age.
     */
    int getAge() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int currentYear = cal.get(Calendar.DAY_OF_YEAR);
        return currentYear - year;
    }
}
