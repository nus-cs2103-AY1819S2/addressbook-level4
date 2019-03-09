package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Represents a module in Graduation Tracker
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    private final ModuleInfo moduleInfo;
    private final Semester takenSem;
    private final GradeRange gradeRange;
    private final Grade obtainedGrade;

    public Module(ModuleInfo moduleInfo, Semester semester, GradeRange gradeRange,
                  Grade obtainedGrade) {
        requireAllNonNull(moduleInfo, semester);
        this.moduleInfo = moduleInfo;
        this.takenSem = semester;
        this.gradeRange = gradeRange;
        this.obtainedGrade = obtainedGrade;
    }

    public ModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    public Semester getTakenSem() {
        return takenSem;
    }

    public Optional<GradeRange> getGradeRange() {
        return Optional.ofNullable(gradeRange);
    }

    public Optional<Grade> getObtainedGrade() {
        return Optional.ofNullable(obtainedGrade);
    }

    public boolean isTaken() {
        return obtainedGrade != null;
    }

    public boolean isPassed() {
        return isTaken() && obtainedGrade.isPassingGrade();
    }

    /**
     * Returns true if both modules have same module code or same module title.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module other) {
        if (other == this) {
            return true;
        }

        return other != null
                && (other.getModuleInfo().getCode().equals(getModuleInfo().getCode())
                || other.getModuleInfo().getTitle().equals(getModuleInfo().getTitle()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleInfo, takenSem);
    }
}
