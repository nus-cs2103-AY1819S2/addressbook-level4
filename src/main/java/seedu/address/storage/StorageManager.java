package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
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

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CardFolderStorage cardFolderStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CardFolderStorage cardFolderStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.cardFolderStorage = cardFolderStorage;
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


    // ================ CardFolder methods ==============================

    @Override
    public Path getCardFolderFilePath() {
        return cardFolderStorage.getCardFolderFilePath();
    }

    @Override
    public Optional<ReadOnlyCardFolder> readCardFolder() throws DataConversionException, IOException {
        return readCardFolder(cardFolderStorage.getCardFolderFilePath());
    }

    @Override
    public Optional<ReadOnlyCardFolder> readCardFolder(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cardFolderStorage.readCardFolder(filePath);
    }

    @Override
    public void saveCardFolder(ReadOnlyCardFolder cardFolder) throws IOException {
        saveCardFolder(cardFolder, cardFolderStorage.getCardFolderFilePath());
    }

    @Override
    public void saveCardFolder(ReadOnlyCardFolder cardFolder, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cardFolderStorage.saveCardFolder(cardFolder, filePath);
    }

}
