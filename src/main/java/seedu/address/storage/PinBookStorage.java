package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for PinBook implementing {@link seedu.address.model.AddressBook}.
 */
public interface PinBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPinBookFilePath();

    /**
     * Returns PinBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readPinBook() throws DataConversionException, IOException;

    /**
     * @see #getPinBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readPinBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param pinBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePinBook(ReadOnlyAddressBook pinBook) throws IOException;

    /**
     * @see #savePinBook(ReadOnlyAddressBook)
     */
    void savePinBook(ReadOnlyAddressBook pinBook, Path filePath) throws IOException;

}

