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
import seedu.address.model.ReadOnlyHealthWorkerBook;


/**
 * A class to access HealthWorkerBook data stored as a json file on the hard disk.
 */
public class JsonHealthWorkerBookStorage implements HealthWorkerBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHealthWorkerBookStorage.class);

    private Path filePath;

    public JsonHealthWorkerBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHealthWorkerBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook() throws DataConversionException {
        return readHealthWorkerBook(filePath);
    }

    /**
     * Similar to {@link #readHealthWorkerBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableHealthWorkerBook> jsonHealthWorkerBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableHealthWorkerBook.class);
        if (!jsonHealthWorkerBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHealthWorkerBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook) throws IOException {
        saveHealthWorkerBook(healthWorkerBook, filePath);
    }

    /**
     * Similar to {@link #saveHealthWorkerBook(ReadOnlyHealthWorkerBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook, Path filePath) throws IOException {
        requireNonNull(healthWorkerBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHealthWorkerBook(healthWorkerBook), filePath);
    }

}
