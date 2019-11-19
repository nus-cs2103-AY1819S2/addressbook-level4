package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents an error when an invalid command is passed in the current mode
 */
public class InvalidCommandModeException extends IllegalValueException {

    public InvalidCommandModeException(String message) {
        super(message);
    }

    public InvalidCommandModeException(String message, Throwable cause) {
        super(message, cause);
    }
}
