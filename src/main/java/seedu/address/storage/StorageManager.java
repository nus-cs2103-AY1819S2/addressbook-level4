package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of PocketProject data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PocketProjectStorage pocketProjectStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(PocketProjectStorage pocketProjectStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.pocketProjectStorage = pocketProjectStorage;
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


    // ================ PocketProject methods ==============================

    @Override
    public Path getPocketProjectFilePath() {
        return pocketProjectStorage.getPocketProjectFilePath();
    }

    @Override
    public Optional<ReadOnlyPocketProject> readPocketProject() throws DataConversionException, IOException {
        return readPocketProject(pocketProjectStorage.getPocketProjectFilePath());
    }

    @Override
    public Optional<ReadOnlyPocketProject> readPocketProject(Path filePath)
                                            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return pocketProjectStorage.readPocketProject(filePath);
    }

    @Override
    public void savePocketProject(ReadOnlyPocketProject pocketProject) throws IOException {
        savePocketProject(pocketProject, pocketProjectStorage.getPocketProjectFilePath());
    }

    @Override
    public void savePocketProject(ReadOnlyPocketProject pocketProject, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        pocketProjectStorage.savePocketProject(pocketProject, filePath);
    }

}
