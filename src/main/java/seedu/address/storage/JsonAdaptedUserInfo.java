package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserInfo;
import seedu.address.model.course.Course;
import seedu.address.storage.coursestorage.JsonAdaptedCourse;

/**
 * A class to access UserInfo stored in hard disk as a Json File
 */
public class JsonAdaptedUserInfo {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "UserInfo's %s field is missing!";

    private final JsonAdaptedCourse course;

    /**
     * Constructs a {@code JsonAdaptedUserInfo} with given details
     */
    @JsonCreator
    public JsonAdaptedUserInfo(@JsonProperty("course") JsonAdaptedCourse course) {
        this.course = course;
    }

    /**
     * Converts a {@code UserInfo} into this class for Jackson use
     */
    public JsonAdaptedUserInfo(UserInfo userInfo) {
        this.course = new JsonAdaptedCourse(userInfo.getCourse());
    }
    /**
     * Converts this Jackson-friendly adapted object into model's {@code UserInfo} object
     *
     * @throws IllegalValueException if there are any data constraints violated in adapted UserInfo
     */
    public UserInfo toModelType() throws IllegalValueException {
        if (course == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "course"));
        }

        final Course modelCourse = course.toModelType();

        return new UserInfo(modelCourse);
    }




}
