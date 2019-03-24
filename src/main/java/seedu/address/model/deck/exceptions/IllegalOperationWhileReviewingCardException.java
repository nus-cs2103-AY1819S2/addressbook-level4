package seedu.address.model.deck.exceptions;

/**
 * Signals that the operation cannot occur when reviewing cards.
 */
public class IllegalOperationWhileReviewingCardException extends RuntimeException {
    public IllegalOperationWhileReviewingCardException() {
        super("Operation is not allowed when user is reviewing decks.");
    }
}
