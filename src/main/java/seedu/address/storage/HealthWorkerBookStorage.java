package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHealthWorkerBook;

/**
 * Represents a storage for {@link seedu.address.model.HealthWorkerBook}.
 */
public interface HealthWorkerBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHealthWorkerBookFilePath();

    /**
     * Returns HealthWorkerBook data as a {@link ReadOnlyHealthWorkerBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook() throws DataConversionException, IOException;

    /**
     * @see #getHealthWorkerBookFilePath()
     */
    Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyHealthWorkerBook} to the storage.
     * @param healthWorkerBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook) throws IOException;

    /**
     * @see #saveHealthWorkerBook(ReadOnlyHealthWorkerBook)
     */
    void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook, Path filePath) throws IOException;

}
