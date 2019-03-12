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
                                                   + " be in the form of dd-mm-yyyy, dd/mm/yyyy or dd.mm.yyyy";

    public static final String VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
                                                  +"(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^"
                                                  +"(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|"
                                                  +"[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])"
                                                  +"00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])"
                                                  +"|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static String dashformat = "dd-mm-yyyy";
    public static String slashformat = "dd/mm/yyyy";
    public static String dotformat = "dd.mm.yyyy";

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


    @Override
    public String toString() {
        return super.toString();
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
