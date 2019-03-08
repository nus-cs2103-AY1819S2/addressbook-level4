package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;

/**
 * Represents a storage for {@link BookShelf}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns BookShelf data as a {@link ReadOnlyBookShelf}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBookShelf> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyBookShelf> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyBookShelf} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyBookShelf addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyBookShelf)
     */
    void saveAddressBook(ReadOnlyBookShelf addressBook, Path filePath) throws IOException;

    void backupAddressBook(ReadOnlyBookShelf addressBook) throws IOException;

}
