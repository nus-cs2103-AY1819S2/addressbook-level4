package seedu.address.model.patient;

import java.util.Calendar;
import java.util.Date;

import seedu.address.model.datetime.DateBase;

/**
 * Represents the birth day of a patient.
 */
public class DateOfBirth extends DateBase {
    public static final String MESSAGE_CONSTRAINTS =
            "Date of birth is compulsory, denoted by \\dob and should be in standard format.";

    /**
     * Default constructor that takes in a birth day.
     * @param dob the dob in dd-mm-yyyy format.
     */
    public DateOfBirth(String dob) {
        super(dob);
    }

    /**
     * Calculates the patient's age.
     * @return the patient's age.
     */
    int getAge() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int currentYear = cal.get(Calendar.YEAR);
        return currentYear - year;
    }
}
