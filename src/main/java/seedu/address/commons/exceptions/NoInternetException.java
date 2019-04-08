package seedu.address.commons.exceptions;

/**
 * Signals that application is not connected to the Internet
 */
public class NoInternetException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public NoInternetException(String message) {
        super(message);
    }
}
