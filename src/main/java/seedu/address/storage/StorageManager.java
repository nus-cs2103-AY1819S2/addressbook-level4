package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of BookShelf data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BookShelfStorage bookShelfStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(BookShelfStorage bookShelfStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.bookShelfStorage = bookShelfStorage;
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

    // ================ BookShelf methods ==============================

    @Override
    public Path getBookShelfFilePath() {
        return bookShelfStorage.getBookShelfFilePath();
    }

    @Override
    public Optional<ReadOnlyBookShelf> readBookShelf() throws DataConversionException, IOException {
        return readBookShelf(bookShelfStorage.getBookShelfFilePath());
    }

    @Override
    public Optional<ReadOnlyBookShelf> readBookShelf(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return bookShelfStorage.readBookShelf(filePath);
    }

    @Override
    public void saveBookShelf(ReadOnlyBookShelf bookShelf) throws IOException {
        saveBookShelf(bookShelf, bookShelfStorage.getBookShelfFilePath());
    }

    @Override
    public void saveBookShelf(ReadOnlyBookShelf bookShelf, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        bookShelfStorage.saveBookShelf(bookShelf, filePath);
    }

    @Override
    public void backupBookShelf(ReadOnlyBookShelf bookShelf) throws IOException {
        bookShelfStorage.backupBookShelf(bookShelf);
    }
}
