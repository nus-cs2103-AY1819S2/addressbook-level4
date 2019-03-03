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
public class JsonMenuStorage implements MenuStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMenuStorage.class);

    private final Path filePath;
    private final Path backupFilePath;

    public JsonMenuStorage(Path filePath) {
        this.filePath = filePath;
        backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    public Path getMenuFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRestOrRant> readMenu() throws DataConversionException {
        return readMenu(filePath);
    }

    /**
     * Similar to {@link #readMenu()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRestOrRant> readMenu(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMenu> jsonMenu = JsonUtil.readJsonFile(
                filePath, JsonSerializableMenu.class);
        if (!jsonMenu.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMenu.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMenu(ReadOnlyRestOrRant menu) throws IOException {
        saveMenu(menu, filePath);
    }

    /**
     * Similar to {@link #saveMenu(ReadOnlyRestOrRant)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMenu(ReadOnlyRestOrRant menu, Path filePath) throws IOException {
        requireNonNull(menu);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMenu(menu), filePath);
    }

    @Override
    public void backupMenu(ReadOnlyRestOrRant menu) throws IOException {
        saveMenu(menu, backupFilePath);
    }

}
