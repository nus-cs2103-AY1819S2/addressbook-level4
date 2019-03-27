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
import seedu.address.model.ReadOnlyGradTrak;

/**
 * A class to access GradTrak data stored as a json file on the hard disk.
 */
public class JsonGradTrakStorage implements GradTrakStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGradTrakStorage.class);

    private Path filePath;

    public JsonGradTrakStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGradTrakFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGradTrak> readGradTrak() throws DataConversionException {
        return readGradTrak(filePath);
    }

    /**
     * Similar to {@link #readGradTrak()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGradTrak> readGradTrak(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGradTrak> jsonGradTrak = JsonUtil.readJsonFile(
                filePath, JsonSerializableGradTrak.class);
        if (!jsonGradTrak.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGradTrak.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGradTrak(ReadOnlyGradTrak addressBook) throws IOException {
        saveGradTrak(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveGradTrak(ReadOnlyGradTrak)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGradTrak(ReadOnlyGradTrak addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGradTrak(addressBook), filePath);
    }

}
