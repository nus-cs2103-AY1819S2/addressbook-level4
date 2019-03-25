package seedu.address.model.record.exceptions;

/**
 * Indicates when a read record format is invalid.
 */
public class BadRecordFormatException extends RuntimeException {
    public BadRecordFormatException() {
        super("Record is not stored in the appropriate format.");
    }
}
