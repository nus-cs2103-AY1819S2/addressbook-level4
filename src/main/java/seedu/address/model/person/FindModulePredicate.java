package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
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
        requireNonNull(module);
        Name moduleInfo = module.getModuleInfo();

        Optional<String> code = findModuleDescriptor.getCode();
        Optional<String> title = findModuleDescriptor.getTitle();
        Optional<Semester> semester = findModuleDescriptor.getSemester();
        Optional<Grade> grade = findModuleDescriptor.getGrade();

        if (code.isPresent()
                && !(moduleInfo.toString().contains(code.get()))) { //to change
            return false;
        }
        if (title.isPresent()
                && !(moduleInfo.toString().contains(title.get()))) { //to change
            return false;
        }
        if (semester.isPresent()
                && !(module.getSemester().equals(semester.get()))) {
            return false;
        }
        if (grade.isPresent()
                && !(module.getExpectedMinGrade().equals(grade.get()))) {
            return false;
        } //to check whether mod has been taken

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindModulePredicate // instanceof handles nulls
                && findModuleDescriptor.equals(((FindModulePredicate) other).findModuleDescriptor)); // state check
    }
}
