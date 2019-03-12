package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PdfBook;
import seedu.address.model.ReadOnlyPdfBook;

/**
 * Represents a storage for {@link PdfBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file value of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns PdfBook data as a {@link ReadOnlyPdfBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPdfBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyPdfBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPdfBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyPdfBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyPdfBook)
     */
    void saveAddressBook(ReadOnlyPdfBook addressBook, Path filePath) throws IOException;

}
