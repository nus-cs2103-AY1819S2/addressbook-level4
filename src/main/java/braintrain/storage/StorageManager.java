package braintrain.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import braintrain.commons.exceptions.DataConversionException;
import braintrain.model.Lessons;
import braintrain.model.ReadOnlyUserPrefs;
import braintrain.model.UserPrefs;
import braintrain.model.lesson.Lesson;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private UserPrefsStorage userPrefsStorage;
    private LessonsStorage lessonsStorage;
    private LessonImportExport lessonImportExport;


    public StorageManager(UserPrefsStorage userPrefsStorage,
                          LessonsStorage lessonsStorage,
                          LessonImportExport lessonImportExport) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.lessonsStorage = lessonsStorage;
        this.lessonImportExport = lessonImportExport;
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
    public Path getLessonsFolderPath() {
        return lessonsStorage.getLessonsFolderPath();
    }

    @Override
    public void setLessonsFolderPath(Path folderPath) {
        lessonsStorage.setLessonsFolderPath(folderPath);
    }

    @Override
    public Optional<Lessons> readLessons() throws IOException {
        return lessonsStorage.readLessons();
    }

    @Override
    public Optional<Lessons> readLessons(Path filePath) throws IOException {
        return lessonsStorage.readLessons(filePath);
    }

    @Override
    public void saveLessons(Lessons lessons) throws IOException {

    }

    @Override
    public void saveLessons(Lessons lessons, Path filePath) throws IOException {

    }

    @Override
    public Path getImportExportFilePath() {
        return lessonImportExport.getImportExportFilePath();
    }

    @Override
    public Optional<Lesson> importLesson(Path filePath) throws IOException {
        return Optional.empty();
    }

    @Override
    public void exportLesson(Lesson lesson, Path filePath) throws IOException {

    }


}
