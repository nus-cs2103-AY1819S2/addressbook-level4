package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.SemLimit;
import seedu.address.model.UserInfo;
import seedu.address.model.course.Course;
import seedu.address.model.moduletaken.Semester;
import seedu.address.storage.coursestorage.JsonAdaptedCourse;

/**
 * A class to access UserInfo stored in hard disk as a Json File
 */
public class JsonAdaptedUserInfo {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "UserInfo's %s field is missing!";

    private final JsonAdaptedCourse course;
    private final String currentSemester;
    private final JsonAdaptedSemLimit semLimit;

    /**
     * Constructs a {@code JsonAdaptedUserInfo} with given details
     */
    @JsonCreator
    public JsonAdaptedUserInfo(@JsonProperty("course") JsonAdaptedCourse course,
                               @JsonProperty("currentSemester") String currentSemester,
                               @JsonProperty("semLimit") JsonAdaptedSemLimit semLimit) {
        this.course = course;
        this.currentSemester = currentSemester;
        this.semLimit = semLimit;
    }

    /**
     * Converts a {@code UserInfo} into this class for Jackson use
     */
    public JsonAdaptedUserInfo(UserInfo userInfo) {
        this.course = new JsonAdaptedCourse(userInfo.getCourse());
        this.currentSemester = userInfo.getCurrentSemester().toString();
        this.semLimit = new JsonAdaptedSemLimit(userInfo.getSemLimit());
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

        if (currentSemester == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, "currentSemester")));
        }

        if (semLimit == null) {
            throw new IllegalValueException((String.format(MISSING_FIELD_MESSAGE_FORMAT, "semLimit")));
        }

        final Course modelCourse = course.toModelType();
        final Semester modelSemester = Semester.valueOf(currentSemester);
        final SemLimit modelSemLimit = semLimit.toModelType();

        return new UserInfo(modelCourse, modelSemester, modelSemLimit);
    }




}
