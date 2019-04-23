package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyHealthWorkerBook;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Books data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private RequestBookStorage requestBookStorage;
    private HealthWorkerBookStorage healthWorkerBookStorage;


    public StorageManager(UserPrefsStorage userPrefStorage,
                          RequestBookStorage requestBookStorage, HealthWorkerBookStorage healthWorkerBookStorage) {
        super();
        this.userPrefsStorage = userPrefStorage;
        this.requestBookStorage = requestBookStorage;
        this.healthWorkerBookStorage = healthWorkerBookStorage;
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


    // ================ RequestBook methods ==============================

    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getRequestBookFilePath() {
        return requestBookStorage.getRequestBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook() throws DataConversionException, IOException {
        return readRequestBook(requestBookStorage.getRequestBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRequestBook> readRequestBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return requestBookStorage.readRequestBook(filePath);
    }

    @Override
    public void saveRequestBook(ReadOnlyRequestBook requestBook) throws IOException {
        saveRequestBook(requestBook, requestBookStorage.getRequestBookFilePath());
    }

    @Override
    public void saveRequestBook(ReadOnlyRequestBook readOnlyRequestBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        requestBookStorage.saveRequestBook(readOnlyRequestBook, filePath);
    }


    //================ HealthWorkerBook methods ==============================
    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getHealthWorkerBookFilePath() {
        return healthWorkerBookStorage.getHealthWorkerBookFilePath();
    }

    @Override
    public Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook() throws DataConversionException, IOException {
        return readHealthWorkerBook(healthWorkerBookStorage.getHealthWorkerBookFilePath());
    }

    @Override
    public Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return healthWorkerBookStorage.readHealthWorkerBook(filePath);
    }

    @Override
    public void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook) throws IOException {
        saveHealthWorkerBook(healthWorkerBook, healthWorkerBookStorage.getHealthWorkerBookFilePath());
    }

    @Override
    public void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        healthWorkerBookStorage.saveHealthWorkerBook(healthWorkerBook, filePath);
    }

}
