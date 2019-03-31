package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPinBook;

/**
 * Represents a storage for {@link seedu.address.model.PinBook}.
 */
public interface PinBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPinBookFilePath();

    /**
     * Returns PinBook data as a {@link ReadOnlyPinBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPinBook> readPinBook() throws DataConversionException, IOException;

    /**
     * @see #getPinBookFilePath()
     */
    Optional<ReadOnlyPinBook> readPinBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPinBook} to the storage.
     * @param pinBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePinBook(ReadOnlyPinBook pinBook) throws IOException;

    /**
     * @see #savePinBook(ReadOnlyPinBook)
     */
    void savePinBook(ReadOnlyPinBook pinBook, Path filePath) throws IOException;

}

