package seedu.address.model.moduletaken;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.ClassForPrinting;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.tag.Tag;

/**
 * Represents a ModuleTaken in GradTrak.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ModuleTaken implements ClassForPrinting {

    private final ModuleInfoCode moduleInfoCode;
    private final Semester semester;

    // Data fields
    private final GradeRange gradeRange;
    private Workload workload;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null. workload information is filled based on module
     */
    public ModuleTaken(ModuleInfoCode moduleInfoCode, Semester semester, Grade expectedMinGrade, Grade expectedMaxGrade,
                       Set<Tag> tags) {
        requireAllNonNull(moduleInfoCode, semester, expectedMinGrade, expectedMaxGrade, tags);
        this.moduleInfoCode = moduleInfoCode;
        this.semester = semester;
        this.gradeRange = new GradeRange(expectedMinGrade, expectedMaxGrade);
        this.workload = new Workload();
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public ModuleTaken(ModuleInfoCode moduleInfoCode, Semester semester, Grade expectedMinGrade, Grade expectedMaxGrade,
                       Workload workload, Set<Tag> tags) {
        requireAllNonNull(moduleInfoCode, semester, expectedMinGrade, expectedMaxGrade, tags);
        this.moduleInfoCode = moduleInfoCode;
        this.semester = semester;
        this.gradeRange = new GradeRange(expectedMinGrade, expectedMaxGrade);
        this.workload = workload;
        this.tags.addAll(tags);
    }

    public void setWorkload(Workload workload) {
        this.workload = workload;
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

    public Hour getTutorialHour() {
        return workload.getTutorialHour();
    }

    public Hour getLabHour() {
        return workload.getLabHour();
    }

    public Hour getProjectHour() {
        return workload.getProjectHour();
    }

    public Hour getPreparationHour() {
        return workload.getPreparationHour();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Checks if this {@code ModuleTaken} has been finished given the current {@code Semester}.
     * @param currentSemester The current {@code Semester}.
     * @return true if the {@code ModuleTaken} has been finished, false otherwise.
     */
    public boolean isFinished(Semester currentSemester) {
        return semester.compareTo(currentSemester) < 0;
    }

    /**
     * Checks if this {@code ModuleTaken} has been passed (must be finished) given the current {@code Semester}.
     * @param currentSemester The current {@code Semester}.
     * @return true if this {@code ModuleTaken} has been passed, false otherwise.
     */
    public boolean isPassed(Semester currentSemester) {
        return isFinished(currentSemester) && getExpectedMaxGrade().isPassingGrade();
    }

    /**
     * Checks if this {@code ModuleTaken} has been failed (must be finished) given the current {@code Semester}.
     * @param currentSemester The current {@code Semester}.
     * @return true if this {@code ModuleTaken} has been failed, false otherwise.
     */
    public boolean isFailed(Semester currentSemester) {
        return isFinished(currentSemester) && !getExpectedMaxGrade().isPassingGrade();
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
                && otherModuleTaken.getTutorialHour().equals(getTutorialHour())
                && otherModuleTaken.getLabHour().equals(getLabHour())
                && otherModuleTaken.getProjectHour().equals(getProjectHour())
                && otherModuleTaken.getPreparationHour().equals(getPreparationHour())
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
                .append("\nSemester: ")
                .append(getSemester())
                .append("\nExpected Min Grade: ")
                .append(getExpectedMinGrade())
                .append("\nExpected Max Grade: ")
                .append(getExpectedMaxGrade())
                .append("\nLecture Hour: ")
                .append(getLectureHour())
                .append("\nTutorial Hour: ")
                .append(getTutorialHour())
                .append("\nLab Hour: ")
                .append(getLabHour())
                .append("\nProject Hour: ")
                .append(getProjectHour())
                .append("\nPreparation Hour: ")
                .append(getPreparationHour())
                .append("\nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public String getPrintable() {
        return this.toString();
    }
}
