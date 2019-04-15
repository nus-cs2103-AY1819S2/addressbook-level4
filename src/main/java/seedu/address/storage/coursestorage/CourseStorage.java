package seedu.address.storage.coursestorage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseList;


/**
 * Represents a storage for {@link Course}.
 */
public interface CourseStorage {
    /**
     * Returns the file path of the data file containing all course Information and listings.
     */
    String getCourseInputStreamPath();

    /**
     * Returns an optional CourseList of some of the courses in nus
     * @param inputStreamPath
     * @return An optional courseList of moduleInfo of all available modules
     * @throws DataConversionException
     * @throws IOException
     */
    Optional<CourseList> readCourseFile(String inputStreamPath) throws DataConversionException;

    Optional<CourseList> readCourseFile()throws DataConversionException;
}
