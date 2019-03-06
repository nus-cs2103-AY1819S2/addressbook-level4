package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.lesson.Lesson;

/**
 * Represents explicit external lesson importing and exporting of {@link Lesson}.
 */
public interface LessonImportExport {
    /**
     * Returns the default folder exported lessons are placed.
     */
    Path getImportExportFilePath();

    /**
     * Returns Lesson data as a {@link Lesson} from a given location.
     * Returns {@code Optional.empty()} if file is not found.
     * @throws IOException if there was any problem when reading from the location.
     */
    Optional<Lesson> importLesson(Path filePath) throws IOException;

    /**
     * Exports the given {@link Lesson} to the given location.
     * @param lesson cannot be null.
     * @param filePath cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void exportLesson(Lesson lesson, Path filePath) throws IOException;
}
