package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.course.Course;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.person.Person;

public class RecModulePredicate implements Predicate<ModuleInfo> {

    private Course course;
    private VersionedAddressBook versionedAddressBook;

    public RecModulePredicate(Course course, VersionedAddressBook versionedAddressBook) {
        this.course = course;
        this.versionedAddressBook = versionedAddressBook;
    }

    @Override
    public boolean test(ModuleInfo module) {
        /* module cannot be taken */
        if (!(new EligibleModulePredicate().test(module))) {
            return false;
        }

        /* module can be taken */
        if (versionedAddressBook.hasPlannedModule(module.getCodeString())) {
            // module already in plan
            return false;
        }
        //TODO: check if module's req type has already been fully satisfied

        return true;
    }
}
