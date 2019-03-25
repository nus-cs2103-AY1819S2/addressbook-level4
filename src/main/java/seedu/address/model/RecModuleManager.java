package seedu.address.model;

import java.util.HashMap;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * A class for managing the module recommendation command.
 */
public class RecModuleManager {

    private final Course course;
    private final VersionedAddressBook versionedAddressBook;
    private final HashMap<ModuleInfoCode, CourseReqType> codeToReqMap;

    public RecModuleManager(Course course, VersionedAddressBook versionedAddressBook) {
        this.course = course;
        this.versionedAddressBook = versionedAddressBook;
        codeToReqMap = new HashMap<>();
    }

    public RecModulePredicate getRecModulePredicate() {
        return new RecModulePredicate(course, versionedAddressBook, codeToReqMap);
    }

    public RecModuleComparator getRecModuleComparator() {
        return new RecModuleComparator(codeToReqMap);
    }
}
