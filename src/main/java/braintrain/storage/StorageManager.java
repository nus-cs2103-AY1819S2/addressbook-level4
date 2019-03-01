package braintrain.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import braintrain.commons.exceptions.DataConversionException;
import braintrain.model.Lessons;
import braintrain.model.ReadOnlyUserPrefs;
import braintrain.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private UserPrefsStorage userPrefsStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage) {
        super();
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

    // ================ Lessons methods ==============================

    @Override
    public Path getLessonsFilePath() {
        return null;
    }

    @Override
    public Optional<Lessons> readLessons() throws IOException {
        return Optional.empty();
    }

    @Override
    public Optional<Lessons> readLessons(Path filePath) throws IOException {
        return Optional.empty();
    }

    @Override
    public void saveLessons(Lessons lessons) throws IOException {

    }

    @Override
    public void saveLessons(Lessons Lessons, Path filePath) throws IOException {

    }

}
