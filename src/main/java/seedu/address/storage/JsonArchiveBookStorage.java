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
import seedu.address.model.ReadOnlyArchiveBook;

/**
 * A class to access ArchiveBook data stored as a json file on the hard disk.
 */
public class JsonArchiveBookStorage implements ArchiveBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonArchiveBookStorage.class);

    private Path filePath;

    public JsonArchiveBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getArchiveBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyArchiveBook> readArchiveBook() throws DataConversionException {
        return readArchiveBook(filePath);
    }

    /**
     * Similar to {@link #readArchiveBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyArchiveBook> readArchiveBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableArchiveBook> jsonArchiveBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableArchiveBook.class);
        if (!jsonArchiveBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonArchiveBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveArchiveBook(ReadOnlyArchiveBook archiveBook) throws IOException {
        saveArchiveBook(archiveBook, filePath);
    }

    /**
     * Similar to {@link #saveArchiveBook(ReadOnlyArchiveBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveArchiveBook(ReadOnlyArchiveBook archiveBook, Path filePath) throws IOException {
        requireNonNull(archiveBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableArchiveBook(archiveBook), filePath);
    }

}
