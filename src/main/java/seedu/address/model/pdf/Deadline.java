package seedu.address.model.pdf;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a Pdf's deadline in the pdf book.
 * Guarantees: immutable;
 */
public class Deadline implements Comparable<Deadline> {
    public static final String MESSAGE_CONSTRAINTS = "Deadline can take any valid date, "
            + "and it should not be blank";
    private static final String PROPERTY_SEPARATOR_PREFIX = "/";
    private static final int PROPERTY_DATE_INDEX = 0;
    private static final int PROPERTY_STATUS_INDEX = 1;

    private final LocalDate date;
    private final DeadlineStatus status;


    /**
     * Constructs a valid {@code Deadline}.
     *
     */
    public Deadline() {
        this.date = LocalDate.MIN;
        this.status = DeadlineStatus.REMOVE;
    }

    /**
     * Constructs a valid {@code Deadline}. Specifically used for Json reading.
     * Interprets a deadline from its #toString() method.
     *
     */
    public Deadline(String jsonFormat) {
        this.date = LocalDate.parse(jsonFormat.split(PROPERTY_SEPARATOR_PREFIX)[Deadline.PROPERTY_DATE_INDEX]);

        String stringStatus = jsonFormat.split(PROPERTY_SEPARATOR_PREFIX)[Deadline.PROPERTY_STATUS_INDEX];
        switch(stringStatus) {
        case "REMOVE":
            this.status = DeadlineStatus.REMOVE;
            break;
        case "READY":
            this.status = DeadlineStatus.READY;
            break;
        case "COMPLETE":
            this.status = DeadlineStatus.COMPLETE;
            break;
        default:
            throw new AssertionError("Unknown DeadlineStatus " + stringStatus);
        }
    }

    /**
     * Constructs a valid {@code Deadline}.
     *
     * @param date - Date of deadline
     * @param month - Month of Deadline
     * @param year - Year of Deadline
     * @throws DateTimeException - If Invalid input is detected (Invalid Date)
     */
    public Deadline(int date, int month, int year) throws DateTimeException {
        this.date = LocalDate.of(year, month, date);
        this.status = DeadlineStatus.READY;
    }

    /**
     * Constructs a valid {@code Deadline}.
     *
     * @param date - Date of deadline
     * @param month - Month of Deadline
     * @param year - Year of Deadline
     * @param status - Specifying if Deadline has been met.
     * @throws DateTimeException - If invalid input is detected
     */

    public Deadline(int date, int month, int year, DeadlineStatus status) throws DateTimeException {
        if (status == DeadlineStatus.COMPLETE) {
            if (LocalDate.of(year, month, date).equals(LocalDate.MIN)) {
                this.date = LocalDate.MIN;
                this.status = DeadlineStatus.REMOVE;
            } else {
                this.date = LocalDate.of(year, month, date);
                this.status = DeadlineStatus.COMPLETE;
            }
        } else if (status == DeadlineStatus.REMOVE) {
            this.date = LocalDate.MIN;
            this.status = DeadlineStatus.REMOVE;
        } else {
            this.date = LocalDate.of(year, month, date);
            this.status = DeadlineStatus.READY;
        }
    }

    /**
     * Takes an existing deadline and parses its values while replacing its status with
     * user input.
     * @param existingDeadline - Existing Deadline whose status you want to change.
     * @param status - Status of the deadline
     */

    public Deadline(Deadline existingDeadline, DeadlineStatus status) {
        this(existingDeadline.date.getDayOfMonth(), existingDeadline.date.getMonthValue(),
                existingDeadline.date.getYear(), status);
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
        return this.status == DeadlineStatus.COMPLETE;
    }

    /**
     * Returns true or false based on the existence of a deadline.
     *
     * @return - existence of localdate.
     */
    public boolean exists() {
        return this.status != DeadlineStatus.REMOVE;
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public String toString() {
        return new StringBuilder().append(this.date.toString())
                .append(Deadline.PROPERTY_SEPARATOR_PREFIX)
                .append(this.status)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls;
                && date.equals(((Deadline) other).date));
    }

    @Override
    public int compareTo(Deadline other) {
        return this.date.compareTo(other.date);
    }
}
