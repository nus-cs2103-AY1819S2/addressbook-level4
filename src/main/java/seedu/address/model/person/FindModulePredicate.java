package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.model.moduleinfo.ModuleInfoCode;
/**
 * Tests that a module matches all description given.
 */
public class FindModulePredicate implements Predicate<ModuleTaken> {

    private final FindModuleDescriptor findModuleDescriptor;

    public FindModulePredicate(FindModuleDescriptor findModuleDescriptor) {
        requireNonNull(findModuleDescriptor);
        this.findModuleDescriptor = findModuleDescriptor;
    }

    @Override
    public boolean test(ModuleTaken module) {
        requireNonNull(module);
        ModuleInfoCode moduleCode = module.getModuleInfo();

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
                && (module.isFinished() != isFinished.get())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModulePredicate // instanceof handles nulls
                && findModuleDescriptor.equals(((FindModulePredicate) other).findModuleDescriptor)); // state check
    }
}
