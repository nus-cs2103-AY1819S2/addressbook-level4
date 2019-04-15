package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Restaurant's opening hours in the food diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidOpeningHours(String)}
 */
public class OpeningHours {
    public static final String MESSAGE_CONSTRAINTS = "Opening hours should be of the format 'HHMM to HHMM' or '24hrs'."
            + "\n If it should not open and close at the same hours, ie. '1300 to 1300'. "
            + "If it opens for 24 hours, please use 24hrs."
            + "for example, 1000 to 2200";
    // alphanumeric and special characters
    private static final String DEFAULT_OPENING_HOURS = "No opening hours added";
    private static final String HOURS = "(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]";
    private static final String VALIDATION_REGEX = HOURS + " to " + HOURS;
    private static final String EMPTY_STRING = "";
    public final String value;


    /**
     * Constructs an {@code OpeningHours}.
     *
     * @param openingHours A opening hour.
     */
    public OpeningHours(String openingHours) {
        requireNonNull(openingHours);
        checkArgument(isValidOpeningHours(openingHours), MESSAGE_CONSTRAINTS);
        value = openingHours;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidOpeningHours(String test) {
        return (test.matches(VALIDATION_REGEX) || test.matches(DEFAULT_OPENING_HOURS)
                || test.matches("24hrs")) && !isSameOpeningAndClosingHours(test);
    }

    /**
     * @return true if opening hours == closing hours
     */
    private static boolean isSameOpeningAndClosingHours(String test) {

        // if input string is 24hrs or default placeholder, do not check for opening and closing hours
        if (test.equals("24hrs") || test.equals(DEFAULT_OPENING_HOURS)) {
            return false;
        }

        String[] openingAndClosing = test.split(" to ");
        String opening = openingAndClosing[0];
        String closing = openingAndClosing[1];
        return opening.equals(closing);
    }

    public static OpeningHours makeDefaultOpening() {
        return new OpeningHours(DEFAULT_OPENING_HOURS);
    }

    public boolean isDefault() {
        return value.equals(DEFAULT_OPENING_HOURS);
    }

    @Override
    public String toString() {
        if (this.isDefault()) {
            return EMPTY_STRING;
        } else {
            return value;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OpeningHours // instanceof handles nulls
                && value.equals(((OpeningHours) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
