package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for ArchiveBook implementing {@link seedu.address.model.AddressBook}.
 */
public interface ArchiveBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getArchiveBookFilePath();

    /**
     * Returns ArchiveBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readArchiveBook() throws DataConversionException, IOException;

    /**
     * @see #getArchiveBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readArchiveBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param archiveBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveArchiveBook(ReadOnlyAddressBook archiveBook) throws IOException;

    /**
     * @see #saveArchiveBook(ReadOnlyAddressBook)
     */
    void saveArchiveBook(ReadOnlyAddressBook archiveBook, Path filePath) throws IOException;

}

