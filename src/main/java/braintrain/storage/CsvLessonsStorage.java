package braintrain.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import braintrain.model.Lessons;
import braintrain.model.lesson.Lesson;

/**
 * A class to access Lessons stored in the hard disk as a csv file
 */
public class CsvLessonsStorage implements LessonsStorage {

    @Override
    public Path getLessonsFolderPath() {
        return null;
    }

    @Override
    public Optional<Lessons> readLessons() throws IOException {
        return Optional.empty();
    }

    @Override
    public Optional<Lessons> readLessons(Path folderPath) throws IOException {
        return Optional.empty();
    }

    @Override
    public Optional<Lesson> importLesson(Path filePath) throws IOException {
        return Optional.empty();
    }

    @Override
    public void saveLessons(Lessons Lessons) throws IOException {

    }

    @Override
    public void saveLessons(Lessons Lessons, Path filePath) throws IOException {

    }
}
