package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommandNew.FindModuleDescriptor;

/**
 * Tests that a module matches all of the description given.
 */
public class FindModulePredicate implements Predicate<Person> {
    private final FindModuleDescriptor findModuleDescriptor;

    public FindModulePredicate(FindModuleDescriptor findModuleDescriptor) {
        requireNonNull(findModuleDescriptor);
        this.findModuleDescriptor = findModuleDescriptor;
    }

    @Override
    public boolean test(Person module) {
        /*requireNonNull(module);
        ModuleInfo moduleInfo = module.getModuleInfo();

        Optional<String> code = findModuleDescriptor.getCode();
        Optional<String> title = findModuleDescriptor.getTitle();
        Optional<Semester> semester = findModuleDescriptor.getSemester();
        Optional<Grade> grade = findModuleDescriptor.getGrade();

        if (code.isPresent()
                && !moduleInfo.getModuleInfoCode().toString().contains(code.get())) {
            return false;
        }
        if (title.isPresent()
                && !moduleInfo.getModuleInfoTitle().toString().contains(title.get())) {
            return false;
        }
        if (semester.isPresent()
                && !module.getSemester().equals(semester.get())) {
            return false;
        }
        if (grade.isPresent()
                && !module.getGradeRange().isWithinRange(grade.get())) {
            return false;
        }*/
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModulePredicate // instanceof handles nulls
                && findModuleDescriptor.equals(((FindModulePredicate) other).findModuleDescriptor)); // state check
    }
}
