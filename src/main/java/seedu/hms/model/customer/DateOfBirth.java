package seedu.hms.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.AppUtil.checkArgumentForDob;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;

/**
 * Represents a Customer's identification number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDob(String)}
 */
public class DateOfBirth {
    public static final String MESSAGE_CONSTRAINTS = "Date of Birth should be of the format - dd/mm/yyyy and should "
        + "not exceed the current year. It should have the correct day and month values. ";
    public static final String VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/([0-9]{4})";
    public static final String VALIDATION_REGEX_2 = "(^$)";
    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid identification number.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgumentForDob(isValidDob(dob));
        value = dob;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static Pair<Boolean, String> isValidDob(String test) {

        if (!test.matches(VALIDATION_REGEX_2)) {

            LocalDate currentDate = LocalDate.now();
            Pattern pattern = Pattern.compile(VALIDATION_REGEX);
            Matcher matcher = pattern.matcher(test);

            if (matcher.matches()) {

                matcher.reset();

                if (matcher.find()) {
                    return DateOfBirth.checksDob(matcher, currentDate);
                } else {
                    return new Pair<>(false, MESSAGE_CONSTRAINTS);
                }
            } else {
                return new Pair<>(false, MESSAGE_CONSTRAINTS);
            }
        } else {
            return new Pair<>(true, "Works.");
        }
    }

    /**
     * Returns a pair that contains whether the given string is valid date of birth or not
     */
    private static Pair<Boolean, String> checksDob(Matcher matcher, LocalDate currentDate) {
        int currentDay = currentDate.getDayOfMonth();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int intYear;
        int intDay;
        int intMonth;
        String day = matcher.group(1);
        String month = matcher.group(2);
        String year = matcher.group(3);
        if ((" ").equals(year)) {
            return new Pair<>(true, "Works");
        } else {
            intYear = Integer.parseInt(year);
            intDay = Integer.parseInt(day);
            intMonth = Integer.parseInt(month);
            if ((intYear >= currentYear && currentDay < intDay && intMonth >= currentMonth)
                || intYear >= currentYear && intMonth > currentMonth) {
                return new Pair<>(false, "Date of birth can't exceed current date.");
            }
            if (intYear > currentYear || intYear < 1) {
                return new Pair<>(false, "Year value wrong.");
            }

            if (("31").equals(day) && (("4").equals(month) || ("6").equals(month) || ("9").equals(month)
                || ("11").equals(month) || ("04").equals(month) || ("06").equals(month)
                || ("09").equals(month))) {
                return new Pair<>(false, "This month can't have 31 days."); // only 1,3,5,7,8,10,12 has
                // 31 days
            } else if (("2").equals(month) || ("02").equals(month)) {
                //leap year
                if (intYear % 4 == 0) {
                    if (("30").equals(day) || ("31").equals(day)) {
                        return new Pair<>(false, "February can't have more than 29 days in leap year.");
                    }
                } else {
                    if (("29").equals(day) || ("31").equals(day) || ("30").equals(day)) {
                        return new Pair<>(false, "February can't have more than 28 days in a non - leap "
                            + "year.");
                    }
                }
            } else {
                return new Pair<>(true, "Works");
            }
            return new Pair<>(true, "Works");
        }
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateOfBirth // instanceof handles nulls
            && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
