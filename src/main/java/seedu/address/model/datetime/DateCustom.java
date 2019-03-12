package seedu.address.model.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateCustom {
    public static final String MESSAGE_CONSTRAINTS = "Date should only contain exactly 8 numbers and a valid date should"
                                                   + " be in the form of dd-mm-yyyy";

    public static final String VALIDATION_REGEX = "^(((0[1-9]|[1-2][0-9]|3[0,1])-(01|03|05|07|08|10|12))|"
                                                + "((0[1-9]|[1-2][0-9]|30)-(04|06|09|11))|((0[1-9]|[1-2]["
                                                + "0-9])-(02)))-(\\d{4})$";

    public static String dateformat = "dd-mm-yyyy";

    public final String storedDate;

    /**
     * Constructs a {@code DateCustom}
     *
     * @param date A valid date
     */
    public DateCustom(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        storedDate = date;
    }

    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isDateBeforeToday(String givenFormat, String test) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(givenFormat);
        String currentDateString = new SimpleDateFormat(givenFormat).format(new Date());
        Date currentDate =  sdf.parse(currentDateString);
        Date givenDate = sdf.parse(test);
        if(currentDate.compareTo(givenDate) > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    public String toString() {
        return storedDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateCustom // instanceof handles nulls
            && storedDate.equals(((DateCustom) other).storedDate)); // state check
    }

    @Override
    public int hashCode() {
        return storedDate.hashCode();
    }
}
