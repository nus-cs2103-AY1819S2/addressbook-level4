package braintrain.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import braintrain.commons.core.LogsCenter;
import braintrain.commons.util.CsvUtil;
import braintrain.model.Lessons;
import braintrain.model.lesson.Lesson;

/**
 * A class to access Lessons stored in the hard disk as a csv file
 */
public class CsvLessonsStorage implements LessonsStorage {
    private static final Logger logger = LogsCenter.getLogger(CsvLessonsStorage.class);

    private static final String CORE_ESCAPE = "*";

    private static final String READ_WARNING_CORE_LABEL = "Core escape character [ "
        + CORE_ESCAPE
        + " ] was found after non-core column.";

    private Path folderPath;

    public CsvLessonsStorage(Path folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public Path getLessonsFolderPath() {
        return folderPath;
    }

    @Override
    public void setLessonsFolderPath(Path folderPath) {
        requireNonNull(folderPath);
        this.folderPath = folderPath;
    }

    @Override
    public Optional<Lessons> readLessons() {
        return readLessons(folderPath);
    }

    @Override
    public Optional<Lessons> readLessons(Path folderPath) {
        requireNonNull(folderPath);
        List<Path> paths = new ArrayList<>();
        Lessons lessons = new Lessons();
        try {
            Files.walk(folderPath).filter(path -> {
                return path.toString().endsWith(".csv");
            }).forEach(paths::add);
        } catch (IOException e) {
            return Optional.empty();
        }
        for (Path filePath : paths) {
            List<String[]> data;
            try {
                data = CsvUtil.readCsvFile(filePath);
            } catch (IOException e) {
                continue;
            }
            if (data == null) {
                continue;
            }
            String[] header = data.get(0);
            int coreCount = 0;
            boolean readingCores = true;
            for (int i = 0; i < header.length; i++) {
                String value = header[i];
                if (value.startsWith(CORE_ESCAPE)) {
                    if (!readingCores) {
                        logger.warning("File " + filePath.toString() + ": " + READ_WARNING_CORE_LABEL);
                        continue;
                    }
                    coreCount++;
                    header[i] = value.substring(CORE_ESCAPE.length());
                } else {
                    readingCores = false;
                }
            }
            if (coreCount < Lesson.CORE_COUNT_MINIMUM) {
                continue;
            }
            String lessonName = filePath.getFileName().toString();
            int extensionPos = lessonName.lastIndexOf(".");
            lessonName = lessonName.substring(0, extensionPos);
            List<String> fields = Arrays.asList(header);
            Lesson newLesson = new Lesson(lessonName, coreCount, fields);
            lessons.addLesson(newLesson);
        }
        return Optional.of(lessons);
    }

    @Override
    public void saveLessons(Lessons lessons) throws IOException {

    }

    @Override
    public void saveLessons(Lessons lessons, Path filePath) throws IOException {

    }
}
