package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Hour;
import seedu.address.model.person.ModuleTaken;
import seedu.address.model.person.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building ModuleTaken objects.
 */
public class ModuleTakenBuilder {

    public static final String DEFAULT_MODULE_INFO_CODE = "CS2103T";
    public static final String DEFAULT_SEMESTER = "Y4S2";
    public static final String DEFAULT_EXPECTED_MIN_GRADE = "F";
    public static final String DEFAULT_EXPECTED_MAX_GRADE = "A_PLUS";
    public static final String DEFAULT_LECTURE_HOUR = "0";

    private ModuleInfoCode moduleInfoCode;
    private Semester semester;
    private Grade expectedMinGrade;
    private Grade expectedMaxGrade;
    private Hour lectureHour;
    private Set<Tag> tags;

    public ModuleTakenBuilder() {
        moduleInfoCode = new ModuleInfoCode(DEFAULT_MODULE_INFO_CODE);
        semester = Semester.valueOf(DEFAULT_SEMESTER);
        expectedMinGrade = Grade.valueOf(DEFAULT_EXPECTED_MIN_GRADE);
        expectedMaxGrade = Grade.valueOf(DEFAULT_EXPECTED_MAX_GRADE);
        lectureHour = new Hour(DEFAULT_LECTURE_HOUR);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ModuleTakenBuilder with the data of {@code moduleTakenToCopy}.
     */
    public ModuleTakenBuilder(ModuleTaken moduleTakenToCopy) {
        moduleInfoCode = moduleTakenToCopy.getModuleInfo();
        semester = moduleTakenToCopy.getSemester();
        expectedMinGrade = moduleTakenToCopy.getExpectedMinGrade();
        expectedMaxGrade = moduleTakenToCopy.getExpectedMaxGrade();
        lectureHour = moduleTakenToCopy.getLectureHour();
        tags = new HashSet<>(moduleTakenToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code ModuleTaken} that we are building.
     */
    public ModuleTakenBuilder withModuleInfoCode(String moduleInfoCode) {
        this.moduleInfoCode = new ModuleInfoCode(moduleInfoCode);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code ModuleTaken} that we are building.
     */
    public ModuleTakenBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code ModuleTaken} that we are building.
     */
    public ModuleTakenBuilder withExpectedMaxGrade(String expectedMaxGrade) {
        this.expectedMaxGrade = Grade.valueOf(expectedMaxGrade);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code ModuleTaken} that we are building.
     */
    public ModuleTakenBuilder withSemester(String semester) {
        this.semester = Semester.valueOf(semester);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code ModuleTaken} that we are building.
     */
    public ModuleTakenBuilder withExpectedMinGrade(String expectedMinGrade) {
        this.expectedMinGrade = Grade.valueOf(expectedMinGrade);
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code ModuleTaken} that we are building.
     */
    public ModuleTakenBuilder withLectureHour(String lectureHour) {
        this.lectureHour = new Hour(lectureHour);
        return this;
    }

    public ModuleTaken build() {
        return new ModuleTaken(moduleInfoCode, semester, expectedMinGrade, expectedMaxGrade, lectureHour, tags);
    }

}
