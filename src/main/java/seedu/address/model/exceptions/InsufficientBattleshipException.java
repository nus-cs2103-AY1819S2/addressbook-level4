package seedu.address.model.exceptions;

/**
 * Represents an insufficient number of battleships exception.
 */
public class InsufficientBattleshipException extends Exception {
    public InsufficientBattleshipException(String message) {
        super(message);
    }
}
