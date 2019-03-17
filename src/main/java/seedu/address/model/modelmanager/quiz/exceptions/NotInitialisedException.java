package seedu.address.model.modelmanager.quiz.exceptions;

/**
 *  Represents not yet initialised error encountered by Quiz
 */
public class NotInitialisedException extends Exception {

    public NotInitialisedException(String message) {
        super(message);
    }
}
