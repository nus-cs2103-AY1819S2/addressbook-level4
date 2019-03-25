package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    private List<CardFolderStorage> cardFolderStorageList;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(List<CardFolderStorage> cardFolderStorageList, UserPrefsStorage userPrefsStorage) {
        super();
        this.cardFolderStorageList = new ArrayList<>(cardFolderStorageList);
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

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
    public void readCardFolders(List<ReadOnlyCardFolder> readFolders) throws Exception {
        Exception exception = null;
        for (CardFolderStorage cardFolderStorage : cardFolderStorageList) {
            try {
                Optional<ReadOnlyCardFolder> cardFolder = readCardFolder(cardFolderStorage);
                cardFolder.ifPresent(readFolders::add);
            } catch (DataConversionException | IOException e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    /**
     * Reads a {@code ReadOnlyCardFolder} from a {@code CardFolderStorage}.
     * @return {@code Optional.empty} if the file is not found.
     */
    public Optional<ReadOnlyCardFolder> readCardFolder(CardFolderStorage cardFolderStorage)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + cardFolderStorage.getcardFolderFilesPath());
        return cardFolderStorage.readCardFolder(cardFolderStorage.getcardFolderFilesPath());
    }

    /**
     * Saves the CardFolder to the specified filePath
     */
    @Override
    public void saveCardFolder(ReadOnlyCardFolder cardFolder, int index) throws IOException {
        // TODO: Handle IOOB exception
        Path filePath = cardFolderStorageList.get(index).getcardFolderFilesPath();
        logger.fine("Attempting to write to data file: " + filePath);
        cardFolderStorageList.get(index).saveCardFolder(cardFolder, filePath);
    }

    @Override
    public void saveCardFolders(List<ReadOnlyCardFolder> cardFolders, Path path) throws IOException {
        cardFolderStorageList.clear();
        // Clear directory before saving
        List<Path> pathsToDelete = Files.walk(path)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        for (Path pathToDelete : pathsToDelete) {
            Files.deleteIfExists(pathToDelete);
        }
        for (ReadOnlyCardFolder cardFolder : cardFolders) {
            // TODO: Address hardcoding and add check for orphaned folders
            Path filePath = path.resolve(cardFolder.getFolderName() + ".json");
            CardFolderStorage cardFolderStorage = new JsonCardFolderStorage(filePath);
            cardFolderStorageList.add(cardFolderStorage);
            cardFolderStorage.saveCardFolder(cardFolder);
        }
    }
}
