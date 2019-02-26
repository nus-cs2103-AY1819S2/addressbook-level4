package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the expiry date of a batch Medicine in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Expiry implements Comparable<Expiry> {

    public static final String MESSAGE_CONSTRAINTS = "Expiry date should be of the format dd/mm/yyyy.\n"
            + "The day, month and year should only contain numbers.\n" + "Day should not be more than 31. "
            + "Month should not be more than 12. Year should begin with 20";
    public static final String VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((20)\\d\\d)";

    public final String value;

    /**
     * Constructs an {@code Expiry}.
     *
     * @param expiry A valid expiry date.
     */
    public Expiry(String expiry) {
        requireNonNull(expiry);

        checkArgument(isValidDate(expiry), MESSAGE_CONSTRAINTS);
        value = expiry;
    }

    /**
     * Returns if a given string is a valid expiry.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX) || test.equals("-");
    }

    @Override
    public int compareTo(Expiry o) {
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date date1 = format.parse(this.value, new ParsePosition(0));
        Date date2 = format.parse(o.value, new ParsePosition(0));
        return date1.compareTo(date2);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expiry // instanceof handles nulls
                && value.equals(((Expiry) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
