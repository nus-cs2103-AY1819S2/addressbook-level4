package seedu.address.storage.coursestorage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.course.CourseList;

/**
 * A class to access Course info data stored as a json file on the hard disk.
 */
public class JsonCourseStorage implements CourseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCourseStorage.class);

    private Path filePath;

    public JsonCourseStorage(Path filePath) {
        this.filePath = filePath;
    }


    @Override
    public Path getCourseFilePath() {
        return filePath;
    }


    /**
     * Similar to {@link #readCourseFile()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<CourseList> readCourseFile(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCourseList> jsonSerializableCourseList = JsonUtil.readJsonFile(
                filePath, JsonSerializableCourseList.class);
        if (!jsonSerializableCourseList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSerializableCourseList.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public Optional<CourseList> readCourseFile() throws DataConversionException {
        return readCourseFile(filePath);
    }

    /**
     * Temporary use will remove
     */
    public void saveCourse(List<JsonAdaptedCourse> courseList, Path filePath) throws IOException {
        requireNonNull(courseList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCourseList(courseList), filePath);
    }
}
