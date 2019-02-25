package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of RestOrRant data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RestOrRantStorage restOrRantStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(RestOrRantStorage restOrRantStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.restOrRantStorage = restOrRantStorage;
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


    // ================ RestOrRant methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return restOrRantStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRestOrRant> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(restOrRantStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRestOrRant> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return restOrRantStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyRestOrRant addressBook) throws IOException {
        saveAddressBook(addressBook, restOrRantStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyRestOrRant addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        restOrRantStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyRestOrRant addressBook) throws IOException {
        restOrRantStorage.backupAddressBook(addressBook);
    }
}
