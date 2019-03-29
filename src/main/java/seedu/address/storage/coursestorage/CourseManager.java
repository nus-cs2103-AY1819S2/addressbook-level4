package seedu.address.storage.coursestorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.course.CourseList;

/**
 * Manages storage of All the course data in local storage.
 */
public class CourseManager implements CourseStorage {

    private static final Logger logger = LogsCenter.getLogger(CourseManager.class);
    private static final Path courseFilePath = Paths.get("src", "main", "resources", "course.json");

    private CourseStorage courseStorage;

    public CourseManager() {
        super();
        this.courseStorage = new JsonCourseStorage(courseFilePath);
    }

    @Override
    public Path getCourseFilePath() {
        return this.courseFilePath;
    }

    @Override
    public Optional<CourseList> readCourseFile(Path filePath) throws DataConversionException {
        return courseStorage.readCourseFile(filePath);
    }

    @Override
    public Optional<CourseList> readCourseFile() throws DataConversionException {
        return courseStorage.readCourseFile(courseStorage.getCourseFilePath());
    }
}
