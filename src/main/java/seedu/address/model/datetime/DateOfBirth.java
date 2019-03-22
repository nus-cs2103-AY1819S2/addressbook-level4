package seedu.address.model.datetime;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Represents the birth day of a patient.
 */
public class DateOfBirth extends DateBase {
    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth is compulsory, denoted by dob/ and should be in dd-MM-yyyy format.";

    /**
     * Default constructor that takes in a birth day.
     *
     * @param dob the dob in dd-mm-yyyy format.
     */
    public DateOfBirth(String dob) {
        super(dob);
    }

    /**
     * Returns false if the given date is before the current date
     *
     * @param test the date to be tested
     */
    static boolean isDateBeforeToday(String test) {
        String currentDateString = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        return dateCompare(test, currentDateString);
    }

    /**
     * Returns true if a given string is a valid DOB.
     *
     * @param test the string to be tested.
     */
    public static boolean isValidDate(String test) {
        return DateBase.isValidDate(test) && DateOfBirth.isDateBeforeToday(test);
    }

    /**
     * Calculates the patient's age.
     *
     * @return the patient's age.
     */
    public int getAge() {
        return Period.between(LocalDate.parse(super.getParsableFormat()), LocalDate.now()).getYears();
    }
}
