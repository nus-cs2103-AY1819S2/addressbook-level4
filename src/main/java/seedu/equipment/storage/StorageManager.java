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
    public Path getEquipmentManagerFilePath() {
        return equipmentManagerStorage.getEquipmentManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyEquipmentManager> readEquipmentManager() throws DataConversionException, IOException {
        return readEquipmentManager(equipmentManagerStorage.getEquipmentManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyEquipmentManager> readEquipmentManager(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return equipmentManagerStorage.readEquipmentManager(filePath);
    }

    @Override
    public void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager) throws IOException {
        saveEquipmentManager(equipmentManager, equipmentManagerStorage.getEquipmentManagerFilePath());
    }

    @Override
    public void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        equipmentManagerStorage.saveEquipmentManager(equipmentManager, filePath);
    }
}
