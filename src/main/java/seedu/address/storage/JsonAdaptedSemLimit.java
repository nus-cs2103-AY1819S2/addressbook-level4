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
    private final String minTutorialHour;
    private final String maxTutorialHour;
    private final String minLabHour;
    private final String maxLabHour;
    private final String minProjectHour;
    private final String maxProjectHour;
    private final String minPreparationHour;
    private final String maxPreparationHour;


    /**
     * Constructs a {@code JsonAdaptedSemLimit} with the given details.
     */
    @JsonCreator
    public JsonAdaptedSemLimit(@JsonProperty("minGrade") String minGrade,
                               @JsonProperty("maxGrade") String maxGrade,
                               @JsonProperty("minLectureHour") String minLectureHour,
                               @JsonProperty("maxLectureHour") String maxLectureHour,
                               @JsonProperty("minTutorialHour") String minTutorialHour,
                               @JsonProperty("maxTutorialHour") String maxTutorialHour,
                               @JsonProperty("minLabHour") String minLabHour,
                               @JsonProperty("maxLabHour") String maxLabHour,
                               @JsonProperty("minProjectHour") String minProjectHour,
                               @JsonProperty("maxProjectHour") String maxProjectHour,
                               @JsonProperty("minPreparationHour") String minPreparationHour,
                               @JsonProperty("maxPreparationHour") String maxPreparationHour) {
        this.minGrade = minGrade;
        this.maxGrade = maxGrade;
        this.minLectureHour = minLectureHour;
        this.maxLectureHour = maxLectureHour;
        this.minTutorialHour = minTutorialHour;
        this.maxTutorialHour = maxTutorialHour;
        this.minLabHour = minLabHour;
        this.maxLabHour = maxLabHour;
        this.minProjectHour = minProjectHour;
        this.maxProjectHour = maxProjectHour;
        this.minPreparationHour = minPreparationHour;
        this.maxPreparationHour = maxPreparationHour;
    }

    /**
     * Converts a give {@code SemLimit} into this class for Jackson use.
     */
    public JsonAdaptedSemLimit(SemLimit semLimit) {
        this.minGrade = semLimit.getMinCap().toString();
        this.maxGrade = semLimit.getMaxCap().toString();
        this.minLectureHour = semLimit.getMinLectureHour().toString();
        this.maxLectureHour = semLimit.getMaxLectureHour().toString();
        this.minTutorialHour = semLimit.getMinTutorialHour().toString();
        this.maxTutorialHour = semLimit.getMaxTutorialHour().toString();
        this.minLabHour = semLimit.getMinLabHour().toString();
        this.maxLabHour = semLimit.getMaxLabHour().toString();
        this.minProjectHour = semLimit.getMinProjectHour().toString();
        this.maxProjectHour = semLimit.getMaxProjectHour().toString();
        this.minPreparationHour = semLimit.getMinPreparationHour().toString();
        this.maxPreparationHour = semLimit.getMaxPreparationHour().toString();
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

        if (minTutorialHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minTutorialHour"));
        }

        if (maxTutorialHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "maxTutorialHour"));
        }

        if (minLabHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minLabHour"));
        }

        if (maxLabHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "maxLabHour"));
        }

        if (minProjectHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minProjectHour"));
        }

        if (maxProjectHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "maxProjectHour"));
        }

        if (minPreparationHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minPreparationHour"));
        }

        if (maxPreparationHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "maxPreparationHour"));
        }
        return new SemLimit(new CapAverage(0.0), new CapAverage(0.0), new Hour(minLectureHour),
                new Hour(maxLectureHour), new Hour(minTutorialHour), new Hour(maxTutorialHour),
                new Hour(minLabHour), new Hour(maxLabHour), new Hour(minProjectHour),
                new Hour(maxProjectHour), new Hour(minPreparationHour), new Hour(maxPreparationHour));

    }
}
