package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents the expiry date of a batch Medicine in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Expiry implements Comparable<Expiry> {

    public static final String MESSAGE_CONSTRAINTS = "Expiry date should be of the format dd/mm/yyyy.\n"
            + "The day, month and year should only contain numbers.\n" + "Day should not be more than 31. "
            + "Month should not be more than 12. Year should begin with 20.";
    public static final String VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((20)\\d\\d)";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/uuuu");

    private final LocalDate expiryDate;

    /**
     * Constructs an {@code Expiry}.
     *
     * @param expiry A valid expiry date.
     */
    public Expiry(String expiry) {
        requireNonNull(expiry);
        checkArgument(isValidDate(expiry), MESSAGE_CONSTRAINTS);

        if (expiry.equals("-")) {
            this.expiryDate = null;
        } else {
            this.expiryDate = parseRawDate(expiry);
        }
    }

    /**
     * Returns if a given string is a valid expiry.
     * */
    public static boolean isValidDate(String test) {

        if (test.equals("-")) {
            return true;
        }

        if (test.matches(VALIDATION_REGEX)) {
            // check if date input is a real, valid date
            try {
                LocalDate.parse(test, formatter.withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException e) {
                return false;
            }
            return true;
        }
        return false;
    }

    private static LocalDate parseRawDate(String expiry) {
        return LocalDate.parse(expiry, formatter);
    }

    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    public boolean isExpired() {
        return this.expiryDate.compareTo(LocalDate.now()) < 0;
    }

    @Override
    public int compareTo(Expiry o) {
        LocalDate date1 = this.expiryDate;
        LocalDate date2 = o.getExpiryDate();
        if (date1 == null) {
            return -1;
        } else if (date2 == null) {
            return 1;
        } else {
            return date1.compareTo(date2);
        }
    }

    @Override
    public String toString() {
        if (this.expiryDate == null) {
            return "-";
        } else {
            return expiryDate.format(formatter);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            // short circuit if same object
            return true;
        }

        if (other instanceof Expiry) {
            if (expiryDate == null) {
                return ((Expiry) other).getExpiryDate() == null;
            } else {
                return expiryDate.equals(((Expiry) other).getExpiryDate());
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return expiryDate.hashCode();
    }

}
