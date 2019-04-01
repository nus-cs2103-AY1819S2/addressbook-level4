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
import seedu.address.model.ReadOnlyRequestBook;

/**
 * A class to access RequestBook data stored as a json file on the hard disk.
 */
public class JsonRequestBookStorage implements RequestBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRequestBookStorage.class);

    private Path filePath;

    public JsonRequestBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getRequestBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException {
        return readRequestBook(filePath);
    }

    /**
     * Similar to {@link #readRequestBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRequestBook> jsonRequestBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableRequestBook.class);
        if (!jsonRequestBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRequestBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRequestBook(ReadOnlyRequestBook requestBook) throws IOException {
        saveRequestBook(requestBook, filePath);
    }

    /**
     * Similar to {@link #saveRequestBook(ReadOnlyRequestBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRequestBook(ReadOnlyRequestBook requestBook, Path filePath) throws IOException {
        requireNonNull(requestBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRequestBook(requestBook), filePath);
    }

}
