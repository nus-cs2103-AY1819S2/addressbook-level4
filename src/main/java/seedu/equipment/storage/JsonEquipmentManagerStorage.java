package seedu.equipment.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.commons.exceptions.DataConversionException;
import seedu.equipment.commons.exceptions.IllegalValueException;
import seedu.equipment.commons.util.FileUtil;
import seedu.equipment.commons.util.JsonUtil;
import seedu.equipment.model.ReadOnlyEquipmentManager;

/**
 * A class to access EquipmentManager data stored as a json file on the hard disk.
 */
public class JsonEquipmentManagerStorage implements EquipmentManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEquipmentManagerStorage.class);

    private Path filePath;

    public JsonEquipmentManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEquipmentManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEquipmentManager> readEquipmentManager() throws DataConversionException {
        return readEquipmentManager(filePath);
    }

    /**
     * Similar to {@link #readEquipmentManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEquipmentManager> readEquipmentManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEquipmentManager> jsonEquipmentManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableEquipmentManager.class);
        if (!jsonEquipmentManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEquipmentManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager) throws IOException {
        saveEquipmentManager(equipmentManager, filePath);
    }

    /**
     * Similar to {@link #saveEquipmentManager(ReadOnlyEquipmentManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager, Path filePath) throws IOException {
        requireNonNull(equipmentManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEquipmentManager(equipmentManager), filePath);
    }
}
