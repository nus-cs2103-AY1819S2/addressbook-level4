package seedu.address.model.datetime;

import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Represents the birth day of a patient.
 */
public class DateOfBirth extends DateBase {
    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth is compulsory, denoted by " + PREFIX_YEAR + " and should be in dd-MM-yyyy format.";
    public static final String MESSAGE_CONSTRAINTS_FUTURE_DAY =
            "Date of birth cannot be after today!";

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
        return isToday(test) || dateCompare(test, currentDateString);
    }

    /**
     * Test if both date represents the same day.
     * @param test the date to be tested.
     * @return true if same day, false otherwise.
     */
    static boolean isToday(String test) {
        DateBase today = DateOfBirth.getToday();
        DateBase toTest = new DateBase(test);
        return today.equals(toTest);
    }

    /**
     * Returns true if a given string is a valid DOB.
     *
     * @param test the string to be tested.
     */
    public static boolean isValidDate(String test) {
        return DateBase.isValidDate(test);
    }

    /**
     * Returns true if the given dob is not after today.
     * @param test the date to be tested.
     * @return true if the date is before today, false otherwise.
     */
    public static boolean isNotFutureDay(String test) {
        return DateOfBirth.isDateBeforeToday(test);
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
