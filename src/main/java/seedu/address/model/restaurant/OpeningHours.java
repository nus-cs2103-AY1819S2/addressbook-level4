package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class OpeningHours {
    public static final String MESSAGE_CONSTRAINTS = "Opening hours should be of the format 'HHMM to HHMM' "
            + "for example, 1000 to 2200";
    // alphanumeric and special characters
    private static final String HOURS = "[\\d{4}]";
    public static final String VALIDATION_REGEX = HOURS + " to " + HOURS;

    public final String value;

    /**
     * Constructs an {@code OpeningHours}.
     *
     * @param openingHours A opening hour.
     */
    public OpeningHours(String openingHours) {
        requireNonNull(openingHours);
        checkArgument(isValidOpeningHour(openingHours), MESSAGE_CONSTRAINTS);
        value = openingHours;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidOpeningHour(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static Weblink makeDefaultOpening() {
        return new Weblink("No opening hours added");
    }

    @Override
    public String toString() {
        return value;
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
