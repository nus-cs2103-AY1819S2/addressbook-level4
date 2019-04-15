package seedu.address.model.project.exceptions;

/**
 * Signals that the operation will result in a date that falls outside of the valid range of dates. Eg a milestone
 * date is invalid if it falls outside the start and end date of a project.
 */
public class DateNotInRangeException extends RuntimeException {
    public DateNotInRangeException() {
        super("Operation would result in a date falling outside the valid date range");
    }
}

