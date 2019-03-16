package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyHotelManagementSystem;

/**
 * A class to access HotelManagementSystem data stored as a json file on the hard disk.
 */
public class JsonHotelManagementSystemStorage implements HotelManagementSystemStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHotelManagementSystemStorage.class);

    private Path filePath;

    public JsonHotelManagementSystemStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHotelManagementSystemFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem() throws DataConversionException {
        return readHotelManagementSystem(filePath);
    }

    /**
     * Similar to {@link #readHotelManagementSystem()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHotelManagementSystem> readHotelManagementSystem(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableHotelManagementSystem> jsonHotelManagementSystem = JsonUtil.readJsonFile(
            filePath, JsonSerializableHotelManagementSystem.class);
        if (!jsonHotelManagementSystem.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHotelManagementSystem.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem) throws IOException {
        saveHotelManagementSystem(hotelManagementSystem, filePath);
    }

    /**
     * Similar to {@link #saveHotelManagementSystem(ReadOnlyHotelManagementSystem)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem, Path filePath) throws IOException {
        requireNonNull(hotelManagementSystem);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHotelManagementSystem(hotelManagementSystem), filePath);
    }

}
