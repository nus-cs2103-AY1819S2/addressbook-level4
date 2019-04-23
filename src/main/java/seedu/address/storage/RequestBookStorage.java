package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRequestBook;

/**
 * Represents a storage for {@link seedu.address.model.RequestBook}.
 *
 * @@author daviddl9
 */
public interface RequestBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRequestBookFilePath();

    /**
     * Returns RequestBook data as a {@link ReadOnlyRequestBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException;

    /**
     * @see #getRequestBookFilePath()
     */
    Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRequestBook} to the storage.
     * @param requestBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRequestBook(ReadOnlyRequestBook requestBook) throws IOException;

    /**
     * @see #saveRequestBook(ReadOnlyRequestBook)
     */
    void saveRequestBook(ReadOnlyRequestBook readOnlyRequestBook, Path filePath) throws IOException;
}
