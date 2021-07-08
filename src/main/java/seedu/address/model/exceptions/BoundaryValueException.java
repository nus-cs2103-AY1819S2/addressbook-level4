package seedu.address.model.exceptions;

/**
 * Represents an insufficient number of battleships exception.
 */
public class BoundaryValueException extends Exception {
    /**
     * Default constructor method.
     *
     * @param message exception message.
     */
    public BoundaryValueException(String message) {
        super(message);
    }
}
