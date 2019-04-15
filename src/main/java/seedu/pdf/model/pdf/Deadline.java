package seedu.pdf.model.pdf;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.MissingFormatArgumentException;

/**
 * Represents a Pdf's deadline in the pdf book.
 * Guarantees: immutable;
 */
public class Deadline implements Comparable<Deadline> {
    public static final String MESSAGE_CONSTRAINTS = "Deadline can take any valid date, "
            + "and it should not be blank";
    public static final String TOSTRING_HEADER_PREFIX = "Deadline: ";
    public static final String STATUS_DONE_PREFIX = " (Done)\n";
    public static final String STATUS_ONGOING_PREFIX = " (Ongoing)\n";
    public static final String STATUS_NONE_PREFIX = "None\n";
    private static final String PROPERTY_SEPARATOR_PREFIX = "/";
    private static final int PROPERTY_DATE_INDEX = 0;
    private static final int PROPERTY_IS_DONE_INDEX = 1;

    private final LocalDate date;
    private final boolean isDone;

    /**
     * Constructs a valid {@code Deadline}.
     *
     */
    public Deadline() {
        this.date = LocalDate.MIN;
        this.isDone = false;
    }

    /**
     * Constructs a valid {@code Deadline}. Specifically used for Json reading.
     * Interprets a deadline from its #toString() method.
     *
     */
    public Deadline(String jsonFormat) {

        if (jsonFormat.equals("")) {
            this.date = LocalDate.MIN;
            this.isDone = false;
        } else {
            try {

                if (!jsonFormat.split(PROPERTY_SEPARATOR_PREFIX)[PROPERTY_IS_DONE_INDEX].equals("true")
                        && !jsonFormat.split(PROPERTY_SEPARATOR_PREFIX)[PROPERTY_IS_DONE_INDEX].equals("false")) {
                    throw new AssertionError();
                }

                this.date = LocalDate.parse(jsonFormat.split(PROPERTY_SEPARATOR_PREFIX)[PROPERTY_DATE_INDEX]);
                this.isDone = Boolean.parseBoolean(jsonFormat.split(PROPERTY_SEPARATOR_PREFIX)[PROPERTY_IS_DONE_INDEX]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new MissingFormatArgumentException("Missing Parameters.");
            }
        }
    }

    /**
     * Constructs a valid {@code Deadline}.
     *
     * @param date - LocalDate of deadline
     * @throws DateTimeException - If Invalid input is detected (Invalid Date)
     */
    public Deadline(LocalDate date) throws DateTimeException {
        this.date = date;
        this.isDone = false;
    }

    /**
     * Constructs a valid {@code Deadline}.
     *
     * @param date - Date of deadline
     * @param month - Month of Deadline
     * @param year - Year of Deadline
     * @throws DateTimeException - If invalid input is detected
     */

    public Deadline(int date, int month, int year) throws DateTimeException {
        this.date = LocalDate.of(year, month, date);
        this.isDone = false;
    }

    /**
     * Takes an existing deadline and parses its values while replacing its status with
     * user input.
     * @param existingDeadline - Existing Deadline whose status you want to change.
     * @param isDone - Specifying if Deadline has been met.
     */

    public Deadline(Deadline existingDeadline, boolean isDone) {
        this.date = existingDeadline.getValue();
        this.isDone = isDone;
    }

    /**
     * Returns the LocalDate object that represents
     * a pdf's deadline.
     *
     * @return date
     */
    public LocalDate getValue() {
        return this.date;
    }

    /**
     * Calculates the number of days to a particular deadline.
     *
     * @return - Number of Days to Deadline as long.
     */
    public long getDaysToDeadline() {
        return DAYS.between(LocalDate.now(), this.date);
    }

    /**
     * Returns the state of the Deadline.
     *
     * @return true or false depending on this.isDone
     */

    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns true or false based on the existence of a deadline.
     *
     * @return - existence of localdate.
     */
    public boolean exists() {
        return this.date != LocalDate.MIN;
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(TOSTRING_HEADER_PREFIX);
        if (this.exists()) {
            builder.append(this.date);
            if (this.isDone()) {
                builder.append(STATUS_DONE_PREFIX);
            } else {
                builder.append(STATUS_ONGOING_PREFIX);
            }
        } else {
            builder.append(STATUS_NONE_PREFIX);
        }
        return builder.toString();
    }

    /**
     * Json adapted toString() method
     *
     * @return - Json adapted Deadline.toString().
     */
    public String toJsonString() {
        return this.exists()
                ? this.date + Deadline.PROPERTY_SEPARATOR_PREFIX + this.isDone
                : "";
    }

    public static Deadline setDone(Deadline completedDeadline) {
        return new Deadline(completedDeadline, true);
    }

    public static Deadline setRemove(Deadline deadlineToRemove) {
        return new Deadline();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof Deadline // instanceof handles nulls;
                && date.equals(((Deadline) other).date)
                && isDone == ((Deadline) other).isDone;
    }

    @Override
    public int compareTo(Deadline other) {
        return this.date.compareTo(other.date);
    }
}
