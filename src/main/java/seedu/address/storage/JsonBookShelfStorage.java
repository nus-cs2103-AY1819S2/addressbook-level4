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
import seedu.address.model.ReadOnlyBookShelf;

/**
 * A class to access BookShelf data stored as a json file on the hard disk.
 */
public class JsonBookShelfStorage implements BookShelfStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBookShelfStorage.class);

    private final Path filePath;
    private final Path backupFilePath;


    public JsonBookShelfStorage(Path filePath) {
        this.filePath = filePath;
        backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    public Path getBookShelfFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBookShelf> readBookShelf() throws DataConversionException {
        return readBookShelf(filePath);
    }

    /**
     * Similar to {@link #readBookShelf()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBookShelf> readBookShelf(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBookShelf> jsonBookShelf = JsonUtil.readJsonFile(
                filePath, JsonSerializableBookShelf.class);
        if (!jsonBookShelf.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBookShelf.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBookShelf(ReadOnlyBookShelf bookShelf) throws IOException {
        saveBookShelf(bookShelf, filePath);
    }

    /**
     * Similar to {@link #saveBookShelf(ReadOnlyBookShelf)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBookShelf(ReadOnlyBookShelf bookShelf, Path filePath) throws IOException {
        requireNonNull(bookShelf);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBookShelf(bookShelf), filePath);
    }

    @Override
    public void backupBookShelf(ReadOnlyBookShelf bookShelf) throws IOException {
        saveBookShelf(bookShelf, backupFilePath);
    }

}
