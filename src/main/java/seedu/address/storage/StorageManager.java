package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.QuickDocs;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;

    private QuickDocsStorage quickDocsStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage,
                          QuickDocsStorage quickDocsStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;

        this.quickDocsStorage = quickDocsStorage;
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

    // ================ QuickDocs methods ==============================

    /**
     * Read QuickDocs data file and retrieve data from json format
     * @return
     * @throws DataConversionException
     * @throws IOException
     */
    @Override
    public Optional<QuickDocs> readQuickDocs() throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + quickDocsStorage.getFilePath());
        return quickDocsStorage.readQuickDocs();
    }

    @Override
    public void saveQuickDocs(QuickDocs quickDocs) throws IOException {
        logger.fine("Attempting to write to data file: " + quickDocsStorage.getFilePath());
        quickDocsStorage.saveQuickDocs(quickDocs);
    }
}
