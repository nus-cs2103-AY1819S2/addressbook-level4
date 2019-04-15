package seedu.hms.logic.stats.exceptions;

/**
 * Signals a StatsItem user wants to show does not exist.
 */
public class ShownItemOutOfBoundException extends RuntimeException {
    public ShownItemOutOfBoundException() {
        super("The stats item selected does not exist.");
    }
}
