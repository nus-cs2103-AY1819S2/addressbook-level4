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
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, LessonsStorage, LessonImportExport {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Optional<Lessons> readLessons() throws IOException;

    @Override
    void saveLessons(Lessons lessons) throws IOException;

    @Override
    Optional<Lesson> importLesson(Path filePath) throws IOException;

    @Override
    void exportLesson(Lesson lesson, Path filePath) throws IOException;

}
