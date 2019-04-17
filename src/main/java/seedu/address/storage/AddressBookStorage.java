package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FashionMatch;
import seedu.address.model.ReadOnlyFashionMatch;

/**
 * Represents a storage for {@link FashionMatch}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns FashionMatch data as a {@link ReadOnlyFashionMatch}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFashionMatch> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyFashionMatch> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFashionMatch} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyFashionMatch addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyFashionMatch)
     */
    void saveAddressBook(ReadOnlyFashionMatch addressBook, Path filePath) throws IOException;

}
