package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.SemLimit;
import seedu.address.model.moduletaken.CapAverage;
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
        this.minGrade = semLimit.getMinCap().toString();
        this.maxGrade = semLimit.getMaxCap().toString();
        this.minLectureHour = semLimit.getMinLectureHour().toString();
        this.maxLectureHour = semLimit.getMaxLectureHour().toString();
    }

    public String getMinGrade() {
        return minGrade;
    }

    public String getMaxGrade() {
        return maxGrade;
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
        return new SemLimit(new CapAverage(0.0), new CapAverage(0.0), new Hour(minLectureHour),
                new Hour(maxLectureHour), new Hour("0"), new Hour("0"), new Hour("0"), new Hour("0"), new Hour("0"),
                new Hour("0"), new Hour("0"), new Hour("0"));

    }
}
