package seedu.travel.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.travel.commons.core.LogsCenter;
import seedu.travel.commons.exceptions.DataConversionException;
import seedu.travel.commons.exceptions.IllegalValueException;
import seedu.travel.commons.util.FileUtil;
import seedu.travel.commons.util.JsonUtil;
import seedu.travel.model.ReadOnlyTravelBuddy;

/**
 * A class to access TravelBuddy data stored as a json file on the hard disk.
 */
public class JsonTravelBuddyStorage implements TravelBuddyStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTravelBuddyStorage.class);

    private final Path filePath;
    private final Path backupFilePath;

    public JsonTravelBuddyStorage(Path filePath) {
        this.filePath = filePath;
        this.backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    @Override
    public Path getTravelBuddyFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTravelBuddy> readTravelBuddy() throws DataConversionException {
        return readTravelBuddy(filePath);
    }

    /**
     * Similar to {@link #readTravelBuddy()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTravelBuddy> readTravelBuddy(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTravelBuddy> jsonTravelBuddy = JsonUtil.readJsonFile(
                filePath, JsonSerializableTravelBuddy.class);
        if (!jsonTravelBuddy.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTravelBuddy.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy) throws IOException {
        saveTravelBuddy(travelBuddy, filePath);
    }

    /**
     * Similar to {@link #saveTravelBuddy(ReadOnlyTravelBuddy)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy, Path filePath) throws IOException {
        requireNonNull(travelBuddy);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTravelBuddy(travelBuddy), filePath);
    }

    @Override
    public void backupTravelBuddy(ReadOnlyTravelBuddy travelBuddy) throws IOException {
        saveTravelBuddy(travelBuddy, backupFilePath);
    }

}
