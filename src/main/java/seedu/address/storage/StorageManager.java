package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCardFolder;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CardFolder data in local storage.
 */
public class StorageManager implements Storage {

    // Temporary constant
    public static final String DEFAULT_FOLDER_NAME = "cardfolder";
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private List<CardFolderStorage> cardFolderStorageList;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(List<CardFolderStorage> cardFolderStorageList, UserPrefsStorage userPrefsStorage) {
        super();
        this.cardFolderStorageList = new ArrayList<>(cardFolderStorageList);
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================


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


    // ================ CardFolder methods ==============================

    @Override
    public Path getcardFolderFilesPath() {
        return cardFolderStorageList.get(0).getcardFolderFilesPath();
    }

    @Override
    public Optional<ReadOnlyCardFolder> readCardFolder() throws DataConversionException, IOException {
        return readCardFolder(cardFolderStorageList.get(0).getcardFolderFilesPath());
    }

    /**
     * Javadoc comment
     */
    public Optional<ReadOnlyCardFolder> readCardFolder(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cardFolderStorageList.get(0).readCardFolder(filePath);
    }


    @Override
    public void saveCardFolder(ReadOnlyCardFolder cardFolder) throws IOException {
        saveCardFolder(cardFolder, cardFolderStorageList.get(0).getcardFolderFilesPath());
    }

    /**
     * Javadoc comment
     */
    public void saveCardFolder(ReadOnlyCardFolder cardFolder, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cardFolderStorageList.get(0).saveCardFolder(cardFolder, filePath);
    }

}
