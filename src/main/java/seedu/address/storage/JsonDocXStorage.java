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
import seedu.address.model.ReadOnlyDocX;

/**
 * A class to access DocX data stored as a json file on the hard disk.
 */
public class JsonDocXStorage implements DocXStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDocXStorage.class);

    private Path filePath;

    public JsonDocXStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDocXFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDocX> readDocX() throws DataConversionException {
        return readDocX(filePath);
    }

    /**
     * Similar to {@link #readDocX()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDocX> readDocX(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDocX> jsonDocX = JsonUtil.readJsonFile(
                filePath, JsonSerializableDocX.class);
        if (!jsonDocX.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDocX.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDocX(ReadOnlyDocX docX) throws IOException {
        saveDocX(docX, filePath);
    }

    /**
     * Similar to {@link #saveDocX(ReadOnlyDocX)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDocX(ReadOnlyDocX docX, Path filePath) throws IOException {
        requireNonNull(docX);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDocX(docX), filePath);
    }

}
