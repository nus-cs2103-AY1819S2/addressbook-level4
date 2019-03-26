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
import seedu.address.model.ReadOnlyPinBook;

/**
 * A class to access PinBook data stored as a json file on the hard disk.
 */
public class JsonPinBookStorage implements PinBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPinBookStorage.class);

    private Path filePath;

    public JsonPinBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPinBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPinBook> readPinBook() throws DataConversionException {
        return readPinBook(filePath);
    }

    /**
     * Similar to {@link #readPinBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPinBook> readPinBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePinBook> jsonPinBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePinBook.class);
        if (!jsonPinBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPinBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void savePinBook(ReadOnlyPinBook pinBook) throws IOException {
        savePinBook(pinBook, filePath);
    }

    /**
     * Similar to {@link #savePinBook(ReadOnlyPinBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePinBook(ReadOnlyPinBook pinBook, Path filePath) throws IOException {
        requireNonNull(pinBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePinBook(pinBook), filePath);
    }

}
