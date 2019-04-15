package quickdocs.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import quickdocs.commons.core.LogsCenter;
import quickdocs.commons.exceptions.DataConversionException;
import quickdocs.model.QuickDocs;
import quickdocs.model.ReadOnlyUserPrefs;
import quickdocs.model.UserPrefs;

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
        logger.fine("Attempting to read user prefs data from file: " + getUserPrefsFilePath());
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        logger.fine("Attempting to write user prefs data to file: " + getUserPrefsFilePath());
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ QuickDocs methods ==============================

    /**
     * Read {@code QuickDocs} data file and retrieve data from json format.
     *
     * @return an {@code Optional<QuickDocs>} object that contains all objects read from the json file.
     * @throws DataConversionException if the file is not in the correct format.
     * @throws IOException if there was an error reading the json file.
     */
    @Override
    public Optional<QuickDocs> readQuickDocs() throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + quickDocsStorage.getFilePath());
        return quickDocsStorage.readQuickDocs();
    }

    /**
     * Saves {@code QuickDocs} data into a data file in json format.
     *
     * @param quickDocs the {@code QuickDocs} object to save.
     * @throws IOException if there was an error writing to the json file.
     */
    @Override
    public void saveQuickDocs(QuickDocs quickDocs) throws IOException {
        logger.fine("Attempting to write to data file: " + quickDocsStorage.getFilePath());
        quickDocsStorage.saveQuickDocs(quickDocs);
    }
}
