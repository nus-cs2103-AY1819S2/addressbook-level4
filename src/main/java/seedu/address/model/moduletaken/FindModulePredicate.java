package seedu.address.model.moduletaken;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.model.moduleinfo.ModuleInfoCode;
/**
 * Tests that a {@code ModuleTaken} matches all description given.
 */
public class FindModulePredicate implements Predicate<ModuleTaken> {

    private final FindModuleDescriptor findModuleDescriptor;
    private final Semester currentSemester;
    private final Logger logger;

    public FindModulePredicate(FindModuleDescriptor findModuleDescriptor, Semester currentSemester) {
        requireAllNonNull(findModuleDescriptor, currentSemester);
        this.findModuleDescriptor = findModuleDescriptor;
        this.currentSemester = currentSemester;
        this.logger = LogsCenter.getLogger(getClass());
    }

    @Override
    public boolean test(ModuleTaken module) {
        requireNonNull(module);
        ModuleInfoCode moduleCode = module.getModuleInfoCode();

        Optional<String> subCode = findModuleDescriptor.getSubCode();
        Optional<Semester> semester = findModuleDescriptor.getSemester();
        Optional<Grade> grade = findModuleDescriptor.getGrade();
        Optional<Boolean> isFinished = findModuleDescriptor.isFinished();

        if (subCode.isPresent()
                && !moduleCode.toString().toLowerCase().contains(subCode.get())) {
            return false;
        }
        if (semester.isPresent()
                && !module.getSemester().equals(semester.get())) {
            return false;
        }
        if (grade.isPresent()
                && !grade.get().isWithin(module.getGradeRange())) {
            return false;
        }
        if (isFinished.isPresent()
                && (module.isFinished(currentSemester) != isFinished.get())) {
            return false;
        }
        logger.fine(moduleCode.toString() + " found");

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModulePredicate // instanceof handles nulls
                && findModuleDescriptor.equals(((FindModulePredicate) other).findModuleDescriptor)
                && currentSemester.equals(((FindModulePredicate) other).currentSemester)); // state check
    }
}
