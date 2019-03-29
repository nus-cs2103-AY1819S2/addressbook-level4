package seedu.address.model.moduletaken;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.tag.Tag;

/**
 * Represents a ModuleTaken in GradTrak.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleTaken {

    private final ModuleInfoCode moduleInfoCode;
    private final Semester semester;

    // Data fields
    private final GradeRange gradeRange;
    private final Workload workload;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public ModuleTaken(ModuleInfoCode moduleInfoCode, Semester semester, Grade expectedMinGrade, Grade expectedMaxGrade,
                       Hour lectureHour, Set<Tag> tags) {
        requireAllNonNull(moduleInfoCode, semester, expectedMinGrade, expectedMaxGrade, tags);
        this.moduleInfoCode = moduleInfoCode;
        this.semester = semester;
        this.gradeRange = new GradeRange(expectedMinGrade, expectedMaxGrade);
        this.workload = new Workload(lectureHour, new Hour("0"),
                new Hour("0"), new Hour("0"), new Hour("0")); //to be populated based on module info
        this.tags.addAll(tags);
    }

    public ModuleInfoCode getModuleInfoCode() {
        return moduleInfoCode;
    }

    public Semester getSemester() {
        return semester;
    }

    GradeRange getGradeRange() {
        return gradeRange;
    }

    public Grade getExpectedMinGrade() {
        return gradeRange.getMin();
    }

    public Grade getExpectedMaxGrade() {
        return gradeRange.getMax();
    }

    public Hour getLectureHour() {
        return workload.getLectureHour();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if this moduleTaken has been finished given the current Semester.
     * @return true if the moduleTaken has been finished, false otherwise.
     */
    public boolean isFinished(Semester currentSemester) {
        return semester.compareTo(currentSemester) < 0;
    }

    /**
     * Checks if this module has been passed.
     * @return true if this module has been passed, false otherwise.
     */
    public boolean isPassed(Semester currentSemester) {
        return isFinished(currentSemester) && getExpectedMaxGrade().isPassingGrade();
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameModuleTaken(ModuleTaken otherModuleTaken) {
        if (otherModuleTaken == this) {
            return true;
        }

        return otherModuleTaken != null
                && otherModuleTaken.getModuleInfoCode().equals(getModuleInfoCode())
                && (otherModuleTaken.getSemester().equals(getSemester()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModuleTaken)) {
            return false;
        }

        ModuleTaken otherModuleTaken = (ModuleTaken) other;
        return otherModuleTaken.getModuleInfoCode().equals(getModuleInfoCode())
                && otherModuleTaken.getSemester().equals(getSemester())
                && otherModuleTaken.getExpectedMinGrade().equals(getExpectedMinGrade())
                && otherModuleTaken.getExpectedMaxGrade().equals(getExpectedMaxGrade())
                && otherModuleTaken.getLectureHour().equals(getLectureHour())
                && otherModuleTaken.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleInfoCode, semester, gradeRange, workload, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleInfoCode())
                .append(" Semester: ")
                .append(getSemester())
                .append(" Expected Min Grade: ")
                .append(getExpectedMinGrade())
                .append(" Expected Max Grade: ")
                .append(getExpectedMaxGrade())
                .append(" Lecture Hour: ")
                .append(getLectureHour())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
