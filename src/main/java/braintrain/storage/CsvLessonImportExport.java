package braintrain.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import braintrain.model.lesson.Lesson;

/**
 * A class to access individual Lesson stored in the hard disk as a csv file
 */
public class CsvLessonImportExport implements LessonImportExport {

    private Path filePath;

    public CsvLessonImportExport(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getImportExportFilePath() {
        return null;
    }

    @Override
    public Optional<Lesson> importLesson(Path filePath) throws IOException {
        return Optional.empty();
    }

    @Override
    public void exportLesson(Lesson lesson, Path filePath) throws IOException {

    }
}
