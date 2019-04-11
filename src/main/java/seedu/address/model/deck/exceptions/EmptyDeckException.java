package seedu.address.model.deck.exceptions;

/**
 * Signals that the operation is unable to find the specified Card.
 */
public class EmptyDeckException extends IndexOutOfBoundsException {
    public EmptyDeckException(String message) {
        super(message);
    }
}
