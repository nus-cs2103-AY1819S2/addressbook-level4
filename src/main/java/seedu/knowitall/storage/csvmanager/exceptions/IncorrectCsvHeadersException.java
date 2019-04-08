package seedu.knowitall.storage.csvmanager.exceptions;

/**
 * Represents error in reading Csv headers during the import command
 */
public class IncorrectCsvHeadersException extends Exception {

    public IncorrectCsvHeadersException(String message) {
        super(message);
    }
}
