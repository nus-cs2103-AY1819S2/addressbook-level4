package seedu.equipment.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.equipment.commons.exceptions.DataConversionException;
import seedu.equipment.model.ReadOnlyEquipmentManager;
import seedu.equipment.model.ReadOnlyUserPrefs;
import seedu.equipment.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends EquipmentManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEquipmentManagerFilePath();

    @Override
    Optional<ReadOnlyEquipmentManager> readEquipmentManager() throws DataConversionException, IOException;

    @Override
    void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager) throws IOException;

}
