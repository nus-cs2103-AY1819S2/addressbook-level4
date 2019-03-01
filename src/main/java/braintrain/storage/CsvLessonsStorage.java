package braintrain.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import braintrain.model.Lessons;

/**
 * A class to access Lessons stored in the hard disk as a csv file
 */
public class CsvLessonsStorage implements LessonsStorage {

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
    public void saveLessons(Lessons Lessons) throws IOException {

    }

    @Override
    public void saveLessons(Lessons Lessons, Path filePath) throws IOException {

    }
}
