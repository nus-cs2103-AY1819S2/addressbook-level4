package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.user.User;

/**
 * Manages storage of Lesson and user data in local storage.
 */
public class StorageManager implements Storage {

    private UserPrefsStorage userPrefsStorage;
    private LessonListStorage lessonListStorage;
    private UserStorage userStorage;


    public StorageManager(UserPrefsStorage userPrefsStorage,
                          LessonListStorage lessonListStorage,
                          UserStorage userStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.lessonListStorage = lessonListStorage;
        this.userStorage = userStorage;
    }

    private void deleteLessonFile(String lessonName) throws IOException {
        Path lessonPath = getLessonListFolderPath().resolve(lessonName + ".csv");
        Files.delete(lessonPath);
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
    public Path getLessonListFolderPath() {
        return lessonListStorage.getLessonListFolderPath();
    }

    @Override
    public void setLessonListFolderPath(Path folderPath) {
        lessonListStorage.setLessonListFolderPath(folderPath);
    }

    @Override
    public Optional<LessonList> readLessonList() {
        return lessonListStorage.readLessonList();
    }

    @Override
    public Optional<LessonList> readLessonList(Path filePath) {
        return lessonListStorage.readLessonList(filePath);
    }

    @Override
    public int saveLessonList(LessonList lessonList) {
        return lessonListStorage.saveLessonList(lessonList);
    }

    @Override
    public int saveLessonList(LessonList lessonList, Path filePath) {
        return lessonListStorage.saveLessonList(lessonList, filePath);
    }

    @Override
    public void deleteLesson(String lessonName) throws IOException {
        deleteLessonFile(lessonName);
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
