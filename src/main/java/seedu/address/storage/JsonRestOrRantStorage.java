package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyRestOrRant;

/**
 * A class to access RestOrRant data stored as a json file on the hard disk.
 */
public class JsonRestOrRantStorage implements RestOrRantStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRestOrRantStorage.class);

    private final Path filePath;
    private final Path backupFilePath;

    public JsonRestOrRantStorage(Path filePath) {
        this.filePath = filePath;
        backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    public Path getRestOrRantFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRestOrRant> readRestOrRant() throws DataConversionException {
        return readRestOrRant(filePath);
    }

    /**
     * Similar to {@link #readRestOrRant()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRestOrRant> readRestOrRant(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRestOrRant> jsonRestOrRant = JsonUtil.readJsonFile(
                filePath, JsonSerializableRestOrRant.class);
        if (!jsonRestOrRant.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRestOrRant.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRestOrRant(ReadOnlyRestOrRant restOrRant) throws IOException {
        saveRestOrRant(restOrRant, filePath);
    }

    /**
     * Similar to {@link #saveRestOrRant(ReadOnlyRestOrRant)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRestOrRant(ReadOnlyRestOrRant restOrRant, Path filePath) throws IOException {
        requireNonNull(restOrRant);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRestOrRant(restOrRant), filePath);
    }

    @Override
    public void backupRestOrRant(ReadOnlyRestOrRant restOrRant) throws IOException {
        saveRestOrRant(restOrRant, backupFilePath);
    }

}
