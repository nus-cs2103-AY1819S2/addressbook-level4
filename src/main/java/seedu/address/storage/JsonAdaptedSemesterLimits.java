package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.limits.SemesterLimit;
import seedu.address.model.moduleinfo.ModuleInfoCredits;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * Class to extract information form JSON file and convert it into SemesterLimit Objects
 */
public class JsonAdaptedSemesterLimits {

    private final double minCap;
    private final double maxCap;
    private final double minLectureHour;
    private final double maxLectureHour;
    private final double minTutorialHour;
    private final double maxTutorialHour;
    private final double minLabHour;
    private final double maxLabHour;
    private final double minProjectHour;
    private final double maxProjectHour;
    private final double minPreparationHour;
    private final double maxPreparationHour;

    /**
     * Constructs a {@code JsonAdaptedSemesterLimits} with the given SemesterLimit details from JSON file.
     */
    @JsonCreator
    public JsonAdaptedSemesterLimits(@JsonProperty("MinCap") double minCap,
                                 @JsonProperty("MaxCap") double maxCap,
                                 @JsonProperty("MinLectureHour") double minLectureHour,
                                 @JsonProperty("MaxLectureHour") double maxLectureHour,
                                 @JsonProperty("MinTutorialHour") double minTutorialHour,
                                 @JsonProperty("MaxTutorialHour") double maxTutorialHour,
                                 @JsonProperty("MinLabHour") double minLabHour,
                                 @JsonProperty("MaxLabHour") double maxLabHour,
                                 @JsonProperty("MinProjectHour") double minProjectHour,
                                 @JsonProperty("MaxProjectHour") double maxProjectHour,
                                 @JsonProperty("MinPreparationHour") double minPreparationHour,
                                 @JsonProperty("MaxPreparationHour") double maxPreparationHour) {
        this.minCap = minCap;
        this.maxCap = maxCap;
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
     * Converts a given {@code SemesterLimit} into this class for Jackson use.
     */
    public JsonAdaptedSemesterLimits(SemesterLimit source) {
        minCap = source.getMinCap().getCapLimit();
        maxCap = source.getMaxCap().getCapLimit();
        minLectureHour = source.getMinLectureHour().getHour();
        maxLectureHour = source.getMaxLectureHour().getHour();
        minTutorialHour = source.getMinTutorialHour().getHour();
        maxTutorialHour = source.getMaxTutorialHour().getHour();
        minLabHour = source.getMinLabHour().getHour();
        maxLabHour = source.getMaxLabHour().getHour();
        minProjectHour = source.getMinProjectHour().getHour();
        maxProjectHour = source.getMaxProjectHour().getHour();
        minPreparationHour = source.getMinPreparationHour().getHour();
        maxPreparationHour = source.getMaxPreparationHour().getHour();
    }

    /**
     * Converts this Jackson-friendly adapted SemesterLimit object into the model's {@code SemesterLimit} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted SemesterLimit.
     */
    public SemesterLimit toModelType() throws IllegalValueException {
        CapAverage minCap = new CapAverage();
        minCap.addWeightedGrade(this.minCap, new ModuleInfoCredits(CapAverage.SINGLE_CREDIT));
        CapAverage maxCap = new CapAverage();
        maxCap.addWeightedGrade(this.maxCap, new ModuleInfoCredits(CapAverage.SINGLE_CREDIT));
        Hour minLectureHour = new Hour(String.valueOf(this.minLectureHour));
        Hour maxLectureHour = new Hour(String.valueOf(this.maxLectureHour));
        Hour minTutorialHour = new Hour(String.valueOf(this.minTutorialHour));
        Hour maxTutorialHour = new Hour(String.valueOf(this.maxTutorialHour));
        Hour minLabHour = new Hour(String.valueOf(this.minLabHour));
        Hour maxLabHour = new Hour(String.valueOf(this.maxLabHour));
        Hour minProjectHour = new Hour(String.valueOf(this.minProjectHour));
        Hour maxProjectHour = new Hour(String.valueOf(this.maxProjectHour));
        Hour minPreparationHour = new Hour(String.valueOf(this.minPreparationHour));
        Hour maxPreparationHour = new Hour(String.valueOf(this.maxPreparationHour));
        return new SemesterLimit(minCap, maxCap, minLectureHour, maxLectureHour, minTutorialHour, maxTutorialHour,
                minLabHour, maxLabHour, minProjectHour, maxProjectHour, minPreparationHour, maxPreparationHour);
    }
}
