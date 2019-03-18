package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyArchiveBook;

/**
 * Represents a storage for {@link seedu.address.model.ArchiveBook}.
 */
public interface ArchiveBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArchiveBookFilePath();

    /**
     * Returns ArchiveBook data as a {@link ReadOnlyArchiveBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyArchiveBook> readArchiveBook() throws DataConversionException, IOException;

    /**
     * @see #getArchiveBookFilePath()
     */
    Optional<ReadOnlyArchiveBook> readArchiveBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyArchiveBook} to the storage.
     * @param archiveBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchiveBook(ReadOnlyArchiveBook archiveBook) throws IOException;

    /**
     * @see #saveArchiveBook(ReadOnlyArchiveBook)
     */
    void saveArchiveBook(ReadOnlyArchiveBook archiveBook, Path filePath) throws IOException;

}

