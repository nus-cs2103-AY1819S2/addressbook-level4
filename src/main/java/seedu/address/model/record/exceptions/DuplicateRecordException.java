package seedu.address.model.record.exceptions;

/**
 * Signals that the operation will result in duplicate Records.
 * Should be checked equal using the Record equals() method.
 */
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException() {
        super("Operation would result in duplicate records");
    }
}
