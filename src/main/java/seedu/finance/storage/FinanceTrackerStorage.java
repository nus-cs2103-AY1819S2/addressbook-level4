package seedu.finance.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.ReadOnlyFinanceTracker;

/**
 * Represents a storage for {@link FinanceTracker}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns FinanceTracker data as a {@link ReadOnlyFinanceTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFinanceTracker> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyFinanceTracker> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFinanceTracker} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFinanceTracker(ReadOnlyFinanceTracker addressBook) throws IOException;

    /**
     * @see #saveFinanceTracker(ReadOnlyFinanceTracker)
     */
    void saveFinanceTracker(ReadOnlyFinanceTracker addressBook, Path filePath) throws IOException;

}
