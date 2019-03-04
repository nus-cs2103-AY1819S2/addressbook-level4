package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommandNew.FindModuleDescriptor;

public class FindCommandPredicate implements Predicate<Module> {
    private final FindModuleDescriptor findModuleDescriptor;

    public FindCommandPredicate(FindModuleDescriptor findModuleDescriptor) {
        requireNonNull(findModuleDescriptor);
        this.findModuleDescriptor = findModuleDescriptor;
    }

    @Override
    public boolean test(Module module) {
        requireNonNull(module);
        ModuleInfo moduleInfo = module.getModuleInfo();

        Optional<String> code = findModuleDescriptor.getCode();
        Optional<String> title = findModuleDescriptor.getTitle();
        Optional<Semester> semester = findModuleDescriptor.getSemester();
        Optional<Grade> grade = findModuleDescriptor.getGrade();

        if (code.isPresent() &&
                !(moduleInfo.getModuleCode().toString().contains(code.get()))) {
            return false;
        }
        if (title.isPresent() &&
                !(moduleInfo.getModuleTitle().toString().contains(title.get()))) {
            return false;
        }
        if (semester.isPresent() &&
                !(module.getSemester().equals(semester.get()))) {
            return false;
        };
        if (grade.isPresent() &&
                !(module.getGrade().equals(grade.get()))) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommandPredicate // instanceof handles nulls
                && findModuleDescriptor.equals(((FindCommandPredicate) other).findModuleDescriptor)); // state check
    }
}
