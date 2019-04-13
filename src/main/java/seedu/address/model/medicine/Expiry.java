package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

/**
 * Represents the expiry date of a batch Medicine in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Expiry implements Comparable<Expiry> {
    public static final LocalDate MAX_DATE = parseRawDate("31/12/9999");
    public static final int MAX_DAYS_TO_EXPIRY = (int) ChronoUnit.DAYS.between(LocalDate.now(), MAX_DATE);
    public static final String MESSAGE_CONSTRAINTS =
            "Expiry date should be of the format dd/mm/yyyy and should be a valid date before "
            + MAX_DATE.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".\n"
            + "All dates after will be considered invalid.\n";

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

        try {
            LocalDate parsed = parseRawDate(test);
            if (parsed.isAfter(MAX_DATE)) {
                return false;
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private static LocalDate parseRawDate(String expiry) {
        return LocalDate.parse(expiry, DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT));
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
            return expiryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
