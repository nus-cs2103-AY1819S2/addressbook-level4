package seedu.address.storage.coursestorage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CourseRequirement;

/**
 * Jackson-friendly version of {@link CourseRequirement}.
 */
public interface JsonAdaptedCourseRequirement {
    /**
     * Converts this Jackson-friendly adapted moduleTaken object into the model's {@code CourseRequirement}
     * interface objects.
     * @throws IllegalValueException if unacceptable values of CourseRequirements are used
     */
    CourseRequirement toModelType() throws IllegalValueException;

}
