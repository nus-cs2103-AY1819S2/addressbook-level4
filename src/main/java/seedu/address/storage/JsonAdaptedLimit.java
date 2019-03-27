package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link ModuleTaken}.
 */
class JsonAdaptedLimit {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ModuleTaken's %s field is missing!";

    private final String moduleInfoCode;
    private final String semester;
    private final String expectedMinGrade;
    private final String expectedMaxGrade;
    private final String lectureHour;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedLimit} with the given moduleTaken details.
     */
    @JsonCreator
    public JsonAdaptedLimit(@JsonProperty("moduleInfoCode") String moduleInfoCode,
                                  @JsonProperty("semester") String semester,
                                  @JsonProperty("expectedMinGrade") String expectedMinGrade,
                                  @JsonProperty("expectedMaxGrade") String expectedMaxGrade,
                                  @JsonProperty("lectureHour") String lectureHour,
                                  @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.moduleInfoCode = moduleInfoCode;
        this.semester = semester;
        this.expectedMinGrade = expectedMinGrade;
        this.expectedMaxGrade = expectedMaxGrade;
        this.lectureHour = lectureHour;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code ModuleTaken} into this class for Jackson use.
     */
    public JsonAdaptedLimit(ModuleTaken source) {
        moduleInfoCode = source.getModuleInfoCode().toString();
        semester = source.getSemester().name();
        expectedMinGrade = source.getExpectedMinGrade().name();
        expectedMaxGrade = source.getExpectedMaxGrade().name();
        lectureHour = source.getLectureHour().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted moduleTaken object into the model's {@code ModuleTaken} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted moduleTaken.
     */
    public ModuleTaken toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (moduleInfoCode == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleInfoCode.class.getSimpleName()));
        }
        if (!ModuleInfoCode.isValidModuleInfoCode(moduleInfoCode)) {
            throw new IllegalValueException(ModuleInfoCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleInfoCode modelName = new ModuleInfoCode(moduleInfoCode);

        if (semester == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Semester.class.getSimpleName()));
        }
        if (!Semester.isValidSemester(semester)) {
            throw new IllegalValueException(Semester.MESSAGE_CONSTRAINTS);
        }
        final Semester modelSemester = Semester.valueOf(semester);

        if (expectedMinGrade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(expectedMinGrade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        final Grade modelExpectedMinGrade = Grade.getGrade(expectedMinGrade);

        if (expectedMaxGrade == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(expectedMaxGrade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }
        final Grade modelExpectedMaxGrade = Grade.getGrade(expectedMaxGrade);

        if (lectureHour == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Hour.class.getSimpleName()));
        }
        if (!Hour.isValidHour(lectureHour)) {
            throw new IllegalValueException(Hour.MESSAGE_CONSTRAINTS);
        }
        final Hour modelLectureHour = new Hour(lectureHour);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new ModuleTaken(modelName, modelSemester, modelExpectedMinGrade,
                modelExpectedMaxGrade, modelLectureHour, modelTags);
    }

}
