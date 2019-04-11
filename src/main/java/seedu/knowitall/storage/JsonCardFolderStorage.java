package seedu.knowitall.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.knowitall.commons.core.LogsCenter;
import seedu.knowitall.commons.exceptions.DataConversionException;
import seedu.knowitall.commons.exceptions.IllegalValueException;
import seedu.knowitall.commons.util.FileUtil;
import seedu.knowitall.commons.util.JsonUtil;
import seedu.knowitall.model.ReadOnlyCardFolder;

/**
 * A class to access CardFolder data stored as a json file on the hard disk.
 */
public class JsonCardFolderStorage implements CardFolderStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCardFolderStorage.class);

    private Path filePath;

    public JsonCardFolderStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns true if provided {@code filePath} is a file that can be read as a {@code JsonCardFolderStorage}.
     */
    public static boolean isCardFolderStorage(Path filePath) {
        JsonCardFolderStorage cardFolderStorage = new JsonCardFolderStorage(filePath);
        try {
            cardFolderStorage.readCardFolder();
        } catch (DataConversionException | IOException e) {
            return false;
        }
        return true;
    }

    public Path getcardFolderFilesPath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCardFolder> readCardFolder() throws DataConversionException, IOException {
        return readCardFolder(filePath);
    }

    /**
     * Similar to {@link #readCardFolder()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCardFolder> readCardFolder(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableCardFolder> jsonCardFolder = JsonUtil.readJsonFile(
                filePath, JsonSerializableCardFolder.class);
        if (!jsonCardFolder.isPresent()) {
            logger.info("File not found at " + filePath);
            throw new IOException();
        }

        try {
            return Optional.of(jsonCardFolder.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCardFolder(ReadOnlyCardFolder cardFolder) throws IOException {
        saveCardFolder(cardFolder, filePath);
    }

    /**
     * Similar to {@link #saveCardFolder(ReadOnlyCardFolder)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCardFolder(ReadOnlyCardFolder cardFolder, Path filePath) throws IOException {
        requireNonNull(cardFolder);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCardFolder(cardFolder), filePath);
    }

    @Override
    public void deleteCardFolder(Path filePath) throws IOException {
        requireNonNull(filePath);

        FileUtil.deleteFile(filePath);
    }

}
