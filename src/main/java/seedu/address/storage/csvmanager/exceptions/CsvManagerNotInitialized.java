package seedu.address.storage.csvmanager.exceptions;

/**
 * Represents an error in the initialization of the csv manager
 */
public class CsvManagerNotInitialized extends Exception {

    public CsvManagerNotInitialized(String message) {
        super(message);
    }
}
