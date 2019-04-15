package seedu.address.testutil;

import seedu.address.model.limits.SemesterLimit;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * A utility class to help with building ModuleTaken objects.
 */
public class SemesterLimitBuilder {

    public static final double DEFAULT_MIN_CAP = 3;
    public static final double DEFAULT_MAX_CAP = 4;
    public static final String DEFAULT_MIN_LECTURE_HOUR = "0";
    public static final String DEFAULT_MAX_LECTURE_HOUR = "10";
    public static final String DEFAULT_MIN_TUTORIAL_HOUR = "0";
    public static final String DEFAULT_MAX_TUTORIAL_HOUR = "10";
    public static final String DEFAULT_MIN_LAB_HOUR = "0";
    public static final String DEFAULT_MAX_LAB_HOUR = "10";
    public static final String DEFAULT_MIN_PROJECT_HOUR = "0";
    public static final String DEFAULT_MAX_PROJECT_HOUR = "10";
    public static final String DEFAULT_MIN_PREPARATION_HOUR = "0";
    public static final String DEFAULT_MAX_PREPARATION_HOUR = "10";

    private CapAverage minCap;
    private CapAverage maxCap;
    private Hour minLectureHour;
    private Hour maxLectureHour;
    private Hour minTutorialHour;
    private Hour maxTutorialHour;
    private Hour minLabHour;
    private Hour maxLabHour;
    private Hour minProjectHour;
    private Hour maxProjectHour;
    private Hour minPreparationHour;
    private Hour maxPreparationHour;

    public SemesterLimitBuilder() {
        minCap = new CapAverage(DEFAULT_MIN_CAP);
        maxCap = new CapAverage(DEFAULT_MAX_CAP);
        minLectureHour = new Hour(DEFAULT_MIN_LECTURE_HOUR);
        maxLectureHour = new Hour(DEFAULT_MAX_LECTURE_HOUR);
        minTutorialHour = new Hour(DEFAULT_MIN_TUTORIAL_HOUR);
        maxTutorialHour = new Hour(DEFAULT_MAX_TUTORIAL_HOUR);
        minLabHour = new Hour(DEFAULT_MIN_LAB_HOUR);
        maxLabHour = new Hour(DEFAULT_MAX_LAB_HOUR);
        minProjectHour = new Hour(DEFAULT_MIN_PROJECT_HOUR);
        maxProjectHour = new Hour(DEFAULT_MAX_PROJECT_HOUR);
        minPreparationHour = new Hour(DEFAULT_MIN_PREPARATION_HOUR);
        maxPreparationHour = new Hour(DEFAULT_MAX_PREPARATION_HOUR);
    }

    /**
     * Initializes the SemesterLimitBuilder with the data of {@code semesterLimitToCopy}.
     */
    public SemesterLimitBuilder(SemesterLimit semesterLimitToCopy) {
        minCap = semesterLimitToCopy.getMinCap();
        maxCap = semesterLimitToCopy.getMaxCap();
        minLectureHour = semesterLimitToCopy.getMinLectureHour();
        maxLectureHour = semesterLimitToCopy.getMaxLectureHour();
        minTutorialHour = semesterLimitToCopy.getMinTutorialHour();
        maxTutorialHour = semesterLimitToCopy.getMaxTutorialHour();
        minLabHour = semesterLimitToCopy.getMinLabHour();
        maxLabHour = semesterLimitToCopy.getMaxLabHour();
        minProjectHour = semesterLimitToCopy.getMinProjectHour();
        maxProjectHour = semesterLimitToCopy.getMaxProjectHour();
        minPreparationHour = semesterLimitToCopy.getMinPreparationHour();
        maxPreparationHour = semesterLimitToCopy.getMaxPreparationHour();
    }

    /**
     * Sets the {@code minCap} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMinCap(double minCap) {
        this.minCap = new CapAverage(minCap);
        return this;
    }

    /**
     * Sets the {@code maxCap} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMaxCap(double maxCap) {
        this.maxCap = new CapAverage(maxCap);
        return this;
    }

    /**
     * Sets the {@code minLectureHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMinLectureHour(String minLectureHour) {
        this.minLectureHour = new Hour(minLectureHour);
        return this;
    }

    /**
     * Sets the {@code maxLectureHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMaxLectureHour(String maxLectureHour) {
        this.maxLectureHour = new Hour(maxLectureHour);
        return this;
    }

    /**
     * Sets the {@code minTutorialHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMinTutorialHour(String minTutorialHour) {
        this.minTutorialHour = new Hour(minTutorialHour);
        return this;
    }

    /**
     * Sets the {@code maxTutorialHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMaxTutorialHour(String maxTutorialHour) {
        this.maxTutorialHour = new Hour(maxTutorialHour);
        return this;
    }

    /**
     * Sets the {@code minLabHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMinLabHour(String minLabHour) {
        this.minLabHour = new Hour(minLabHour);
        return this;
    }

    /**
     * Sets the {@code maxLabHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMaxLabHour(String maxLabHour) {
        this.maxLabHour = new Hour(maxLabHour);
        return this;
    }

    /**
     * Sets the {@code minProjectHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMinProjectHour(String minProjectHour) {
        this.minProjectHour = new Hour(minProjectHour);
        return this;
    }

    /**
     * Sets the {@code maxProjectHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMaxProjectHour(String maxProjectHour) {
        this.maxProjectHour = new Hour(maxProjectHour);
        return this;
    }

    /**
     * Sets the {@code minPreparationHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMinPreparationHour(String minPreparationHour) {
        this.minPreparationHour = new Hour(minPreparationHour);
        return this;
    }

    /**
     * Sets the {@code maxPreparationHour} of the {@code SemesterLimit} being built.
     */
    public SemesterLimitBuilder withMaxPreparationHour(String maxPreparationHour) {
        this.maxPreparationHour = new Hour(maxPreparationHour);
        return this;
    }

    /**
     * Builds a semester limit with all the fields in the {@code SemesterLimitBuilder}
     */
    public SemesterLimit build() {
        return new SemesterLimit(minCap, maxCap, minLectureHour, maxLectureHour, minTutorialHour, maxTutorialHour,
                minLabHour, maxLabHour, minProjectHour, maxProjectHour, minPreparationHour, maxPreparationHour);
    }

}
