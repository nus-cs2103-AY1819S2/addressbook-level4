package seedu.address.model.dentist.exceptions;

import java.io.IOException;

/**
 * Exception that describes problems occurred when storing and retrieving dentist information.
 */
public class DentistNotFoundException extends IOException {
    public DentistNotFoundException(String message) {
        super(message);
    }
}
