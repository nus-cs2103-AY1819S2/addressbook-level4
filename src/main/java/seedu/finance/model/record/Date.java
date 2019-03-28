package seedu.finance.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Record's date in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be of the format dd/mm/yyyy and be a valid date";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);

        String[] parsedDate = date.split("/");
        int year = Integer.parseInt(parsedDate[2]);
        int month = Integer.parseInt(parsedDate[1]);
        int day = Integer.parseInt(parsedDate[0]);
        this.setDate(year, month, day);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        String[] parsedDate = test.split("/");
        try {
            int year = Integer.parseInt(parsedDate[2]);
            int month = Integer.parseInt(parsedDate[1]);
            int day = Integer.parseInt(parsedDate[0]);
            LocalDate date = LocalDate.of(year, month, day);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(int year, int month, int day) {
        date = LocalDate.of(year, month, day);
    }

    @Override
    public String toString() {
        return date.format(formatter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
