package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyArchiveBook;
import seedu.address.model.ReadOnlyPinBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private ArchiveBookStorage archiveBookStorage;
    private PinBookStorage pinBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, ArchiveBookStorage archiveBookStorage,
                          PinBookStorage pinBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.archiveBookStorage = archiveBookStorage;
        this.pinBookStorage = pinBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ ArchiveBook methods ==============================

    @Override
    public Path getArchiveBookFilePath() {
        return archiveBookStorage.getArchiveBookFilePath();
    }

    @Override
    public Optional<ReadOnlyArchiveBook> readArchiveBook() throws DataConversionException, IOException {
        return readArchiveBook(archiveBookStorage.getArchiveBookFilePath());
    }

    @Override
    public Optional<ReadOnlyArchiveBook> readArchiveBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return archiveBookStorage.readArchiveBook(filePath);
    }

    @Override
    public void saveArchiveBook(ReadOnlyArchiveBook archiveBook) throws IOException {
        saveArchiveBook(archiveBook, archiveBookStorage.getArchiveBookFilePath());
    }

    @Override
    public void saveArchiveBook(ReadOnlyArchiveBook archiveBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        archiveBookStorage.saveArchiveBook(archiveBook, filePath);
    }

    // ============================= PinBook methods ==============================

    @Override
    public Path getPinBookFilePath() {
        return pinBookStorage.getPinBookFilePath();
    }

    @Override
    public Optional<ReadOnlyPinBook> readPinBook() throws DataConversionException, IOException {
        return readPinBook(pinBookStorage.getPinBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPinBook> readPinBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return pinBookStorage.readPinBook(filePath);
    }

    @Override
    public void savePinBook(ReadOnlyPinBook pinBook) throws IOException {
        savePinBook(pinBook, pinBookStorage.getPinBookFilePath());
    }

    @Override
    public void savePinBook(ReadOnlyPinBook pinBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        pinBookStorage.savePinBook(pinBook, filePath);
    }
}
