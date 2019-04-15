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
import seedu.address.model.ReadOnlyPocketProject;

/**
 * A class to access PocketProject data stored as a json file on the hard disk.
 */
public class JsonPocketProjectStorage implements PocketProjectStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPocketProjectStorage.class);

    private Path filePath;

    public JsonPocketProjectStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPocketProjectFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPocketProject> readPocketProject() throws DataConversionException {
        return readPocketProject(filePath);
    }

    /**
     * Similar to {@link #readPocketProject()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPocketProject> readPocketProject(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePocketProject> jsonPocketProject = JsonUtil.readJsonFile(
                filePath, JsonSerializablePocketProject.class);
        if (!jsonPocketProject.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPocketProject.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePocketProject(ReadOnlyPocketProject pocketProject) throws IOException {
        savePocketProject(pocketProject, filePath);
    }

    /**
     * Similar to {@link #savePocketProject(ReadOnlyPocketProject)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePocketProject(ReadOnlyPocketProject pocketProject, Path filePath) throws IOException {
        requireNonNull(pocketProject);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePocketProject(pocketProject), filePath);
    }

}
