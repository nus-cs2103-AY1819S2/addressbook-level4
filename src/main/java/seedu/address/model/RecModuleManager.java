package seedu.address.model;

import java.util.HashMap;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * A class for managing the module recommendation feature.
 */
public class RecModuleManager {

    private final Course course;
    private final VersionedGradTrak versionedAddressBook;
    private final HashMap<ModuleInfoCode, CourseReqType> codeToReqMap;

    public RecModuleManager(Course course, VersionedGradTrak versionedAddressBook) {
        this.course = course;
        this.versionedAddressBook = versionedAddressBook;
        codeToReqMap = new HashMap<>();
    }

    public HashMap<ModuleInfoCode, CourseReqType> getCodeToReqMap() {
        return codeToReqMap;
    }

    public RecModulePredicate getRecModulePredicate() {
        return new RecModulePredicate(course, versionedAddressBook, codeToReqMap);
    }

    public RecModuleComparator getRecModuleComparator() {
        return new RecModuleComparator(codeToReqMap);
    }
}
