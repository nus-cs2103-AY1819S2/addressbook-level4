package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, ArchiveBookStorage, PinBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getArchiveBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readArchiveBook() throws DataConversionException, IOException;

    @Override
    void saveArchiveBook(ReadOnlyAddressBook archiveBook) throws IOException;

    @Override
    Path getPinBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readPinBook() throws DataConversionException, IOException;

    @Override
    void savePinBook(ReadOnlyAddressBook pinBook) throws IOException;

}
