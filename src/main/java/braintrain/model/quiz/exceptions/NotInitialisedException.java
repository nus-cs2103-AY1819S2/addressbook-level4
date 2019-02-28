package braintrain.model.quiz.exceptions;

/**
 *  Represents not yet initialised error encountered by Quiz
 */
public class NotInitialisedException extends Exception {

    public NotInitialisedException(String message) {
        super(message);
    }
}
