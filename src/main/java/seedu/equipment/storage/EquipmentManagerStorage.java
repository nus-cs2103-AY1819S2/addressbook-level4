package seedu.equipment.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.equipment.commons.exceptions.DataConversionException;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.ReadOnlyEquipmentManager;

/**
 * Represents a storage for {@link EquipmentManager}.
 */
public interface EquipmentManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEquipmentManagerFilePath();

    /**
     * Returns EquipmentManager data as a {@link ReadOnlyEquipmentManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEquipmentManager> readEquipmentManager() throws DataConversionException, IOException;

    /**
     * @see #getEquipmentManagerFilePath()
     */
    Optional<ReadOnlyEquipmentManager> readEquipmentManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEquipmentManager} to the storage.
     * @param equipmentManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager) throws IOException;

    /**
     * @see #saveEquipmentManager(ReadOnlyEquipmentManager)
     */
    void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager, Path filePath) throws IOException;
}
