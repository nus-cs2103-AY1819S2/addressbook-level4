package seedu.equipment.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.commons.exceptions.DataConversionException;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.ReadOnlyUserPrefs;
import seedu.equipment.model.UserPrefs;

/**
 * Manages storage of EquipmentManager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EquipmentManagerStorage equipmentManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(EquipmentManagerStorage equipmentManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.equipmentManagerStorage = equipmentManagerStorage;
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


    // ================ EquipmentManager methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return equipmentManagerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyEquipmentManager> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(equipmentManagerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyEquipmentManager> readAddressBook(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return equipmentManagerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyEquipmentManager addressBook) throws IOException {
        saveAddressBook(addressBook, equipmentManagerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyEquipmentManager addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        equipmentManagerStorage.saveAddressBook(addressBook, filePath);
    }
}
