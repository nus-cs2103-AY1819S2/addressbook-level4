package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;

/**
 * Represents a storage for {@link RestOrRant}.
 */
public interface RestOrRantStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns RestOrRant data as a {@link ReadOnlyRestOrRant}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestOrRant> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyRestOrRant> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestOrRant} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyRestOrRant addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyRestOrRant)
     */
    void saveAddressBook(ReadOnlyRestOrRant addressBook, Path filePath) throws IOException;

    void backupAddressBook(ReadOnlyRestOrRant addressBook) throws IOException;

}
