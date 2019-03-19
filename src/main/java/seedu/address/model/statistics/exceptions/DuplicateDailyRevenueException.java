package seedu.address.model.statistics.exceptions;

/**
 * Signals that the operation will result in duplicate DailyRevenue (Daily Revenue are considered duplicates if they
 * have the same day, month, year).
 */
public class DuplicateDailyRevenueException extends RuntimeException {
    public DuplicateDailyRevenueException() {
        super("Operation would result in duplicate daily revenue.");
    }
}
