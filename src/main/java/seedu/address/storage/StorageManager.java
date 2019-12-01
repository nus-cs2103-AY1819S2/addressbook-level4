package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDocX;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of DocX data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private DocXStorage docXStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(DocXStorage docXStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.docXStorage = docXStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ DocX methods ==============================

    @Override
    public Path getDocXFilePath() {
        return docXStorage.getDocXFilePath();
    }

    @Override
    public Optional<ReadOnlyDocX> readDocX() throws DataConversionException, IOException {
        return readDocX(docXStorage.getDocXFilePath());
    }

    @Override
    public Optional<ReadOnlyDocX> readDocX(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return docXStorage.readDocX(filePath);
    }

    @Override
    public void saveDocX(ReadOnlyDocX docX) throws IOException {
        saveDocX(docX, docXStorage.getDocXFilePath());
    }

    @Override
    public void saveDocX(ReadOnlyDocX docX, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        docXStorage.saveDocX(docX, filePath);
    }

}
