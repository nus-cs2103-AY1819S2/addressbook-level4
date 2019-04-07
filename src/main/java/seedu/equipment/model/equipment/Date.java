package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Represents a Equipment's due date in the equipment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Next preventive maintenance due date should only contain numbers. "
                    + "and it should not be blank" + "\n" + "It should be in a format dd-MM-yyyy.";


    public static final DateTimeFormatter VALID_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private LocalDate date;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        String[] parsedDate = date.split("-");
        int year = Integer.parseInt(parsedDate[2]);
        int month = Integer.parseInt(parsedDate[1]);
        int day = Integer.parseInt(parsedDate[0]);
        this.setDate(year, month, day);
    }
    /**
     * Constructs a (@code Date).
     *
     * @param date A local date object.
     */
    public Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns if a given string is a valid date.
     * The date must be a valid date, e.g: 32 February 2019 is not accepted.
     */
    public static boolean isValidDate(String test) {
        String[] parsedDate = test.split("-");
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
        return date.format(VALID_DATE_FORMAT);
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
