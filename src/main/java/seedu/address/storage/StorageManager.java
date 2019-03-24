package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.User;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private UserPrefsStorage userPrefsStorage;
    private LessonsStorage lessonsStorage;
    private UserStorage userStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage,
                          LessonsStorage lessonsStorage,
                          UserStorage userStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.lessonsStorage = lessonsStorage;
        this.userStorage = userStorage;
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

    // ================ LessonList methods ==============================

    @Override
    public Path getLessonsFolderPath() {
        return lessonsStorage.getLessonsFolderPath();
    }

    @Override
    public void setLessonsFolderPath(Path folderPath) {
        lessonsStorage.setLessonsFolderPath(folderPath);
    }

    @Override
    public Optional<LessonList> readLessons() {
        return lessonsStorage.readLessons();
    }

    @Override
    public Optional<LessonList> readLessons(Path filePath) {
        return lessonsStorage.readLessons(filePath);
    }

    @Override
    public int saveLessons(LessonList lessonList) {
        return lessonsStorage.saveLessons(lessonList);
    }

    @Override
    public int saveLessons(LessonList lessonList, Path filePath) {
        return lessonsStorage.saveLessons(lessonList, filePath);
    }

    // ================ User methods ==============================

    @Override
    public Path getUserFilePath() {
        return userStorage.getUserFilePath();
    }

    @Override
    public void setUserFilePath(Path folderPath) {
        userStorage.setUserFilePath(folderPath);
    }

    @Override
    public Optional<User> readUser() {
        return userStorage.readUser();
    }

    @Override
    public Optional<User> readUser(Path folderPath) {
        return userStorage.readUser(folderPath);
    }

    @Override
    public void saveUser(User user) {
        userStorage.saveUser(user);
    }

    @Override
    public void saveUser(User user, Path filePath) {
        userStorage.saveUser(user, filePath);
    }
}
