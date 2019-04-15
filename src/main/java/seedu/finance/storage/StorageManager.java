package seedu.finance.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.finance.commons.core.LogsCenter;
import seedu.finance.commons.exceptions.DataConversionException;
import seedu.finance.model.ReadOnlyFinanceTracker;
import seedu.finance.model.ReadOnlyUserPrefs;
import seedu.finance.model.UserPrefs;

/**
 * Manages storage of FinanceTracker data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static FinanceTrackerStorage financeTrackerStorage;
    private static UserPrefsStorage userPrefsStorage;


    public StorageManager(FinanceTrackerStorage financeTrackerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.financeTrackerStorage = financeTrackerStorage;
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


    // ================ FinanceTracker methods ==============================

    @Override
    public Path getFinanceTrackerFilePath() {
        return financeTrackerStorage.getFinanceTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyFinanceTracker> readFinanceTracker() throws DataConversionException, IOException {
        return readFinanceTracker(financeTrackerStorage.getFinanceTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyFinanceTracker> readFinanceTracker(Path filePath) throws
            DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return financeTrackerStorage.readFinanceTracker(filePath);
    }

    @Override
    public void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker) throws IOException {
        saveFinanceTracker(financeTracker, financeTrackerStorage.getFinanceTrackerFilePath());
    }

    @Override
    public void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        financeTrackerStorage.saveFinanceTracker(financeTracker, filePath);
    }

    public static void setFinanceTrackerStorage(FinanceTrackerStorage newStorage) {
        financeTrackerStorage = newStorage;
    }

    public static FinanceTrackerStorage getFinanceTrackerStorage() {
        return financeTrackerStorage;
    }

}
