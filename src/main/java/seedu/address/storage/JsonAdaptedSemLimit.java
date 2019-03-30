package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.SemLimit;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;

/**
 * Jackson-friendly version of {@link SemLimit}
 */
public class JsonAdaptedSemLimit {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "SemLimit's %s field is missing!";

    private final String minGrade;
    private final String maxGrade;
    private final String minLectureHour;
    private final String maxLectureHour;

    /**
     * Constructs a {@code JsonAdaptedSemLimit} with the given details.
     */
    @JsonCreator
    public JsonAdaptedSemLimit(@JsonProperty("minGrade") String minGrade,
                               @JsonProperty("maxGrade") String maxGrade,
                               @JsonProperty("minLectureHour") String minLectureHour,
                               @JsonProperty("maxLectureHour") String maxLectureHour) {
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
        this.minLectureHour = minLectureHour;
        this.maxLectureHour = maxLectureHour;
    }

    /**
     * Converts a give {@code SemLimit} into this class for Jackson use.
     */
    public JsonAdaptedSemLimit(SemLimit semLimit) {
        this.minGrade = semLimit.getMinGrade().name();
        this.maxGrade = semLimit.getMaxGrade().name();
        this.minLectureHour = semLimit.getMinLectureHour().toString();
        this.maxLectureHour = semLimit.getMaxLectureHour().toString();
    }

    /**
     * Converts this Jackson-friendly adapted SemLimit object into model's {@code SemLimit} object
     *
     * @throws IllegalValueException if there are any data constraints violated in adapted SemLimit
     */
    public SemLimit toModelType() throws IllegalValueException {
        if (minGrade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minGrade"));
        }

        if (maxGrade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "maxGrade"));
        }

        if (minLectureHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "minLectureHour"));
        }

        if (maxLectureHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "maxLectureHour"));
        }
        return new SemLimit(Grade.getGrade(minGrade), Grade.getGrade(maxGrade),
                            new Hour(minLectureHour), new Hour(maxLectureHour));

    }
}
