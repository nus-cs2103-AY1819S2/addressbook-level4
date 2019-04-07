package seedu.address.model.statistics.exceptions;

/**
 * Signals that the operation will result in duplicate Revenue (Revenue are considered duplicates if they
 * have the same day, month, year).
 */
public class DuplicateRevenueException extends RuntimeException {
    public DuplicateRevenueException() {
        super("Operation would result in duplicate revenue.");
    }
}
