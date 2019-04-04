package seedu.address.storage.coursestorage;

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
    private static final String inputStreamPath = "/json/course.json";

    private CourseStorage courseStorage;

    public CourseManager() {
        super();
        this.courseStorage = new JsonCourseStorage(inputStreamPath);
    }

    @Override
    public String getCourseInputStreamPath() {
        return inputStreamPath;
    }

    @Override
    public Optional<CourseList> readCourseFile(String inputStreamPath) throws DataConversionException {
        logger.fine("Attempting to read data from file: " + inputStreamPath);
        return courseStorage.readCourseFile(inputStreamPath);
    }

    @Override
    public Optional<CourseList> readCourseFile() throws DataConversionException {
        return courseStorage.readCourseFile(courseStorage.getCourseInputStreamPath());
    }
}
