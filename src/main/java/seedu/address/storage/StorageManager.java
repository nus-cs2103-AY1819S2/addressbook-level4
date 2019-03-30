package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyGradTrak;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of GradTrak data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private GradTrakStorage gradTrakStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(GradTrakStorage gradTrakStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.gradTrakStorage = gradTrakStorage;
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


    // ================ GradTrak methods ==============================

    @Override
    public Path getGradTrakFilePath() {
        return gradTrakStorage.getGradTrakFilePath();
    }

    @Override
    public Optional<ReadOnlyGradTrak> readGradTrak() throws DataConversionException, IOException {
        return readGradTrak(gradTrakStorage.getGradTrakFilePath());
    }

    @Override
    public Optional<ReadOnlyGradTrak> readGradTrak(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return gradTrakStorage.readGradTrak(filePath);
    }

    @Override
    public void saveGradTrak(ReadOnlyGradTrak addressBook) throws IOException {
        saveGradTrak(addressBook, gradTrakStorage.getGradTrakFilePath());
    }

    @Override
    public void saveGradTrak(ReadOnlyGradTrak addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        gradTrakStorage.saveGradTrak(addressBook, filePath);
    }

}
