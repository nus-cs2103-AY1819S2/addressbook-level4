package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Tests if a module can be recommended to the user.
 */
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

        List<ModuleInfoCode> passedModuleList = versionedAddressBook.getPassedModuleList();
        List<CourseReqType> reqTypeList = course.getCourseReqTypeOf(module.getModuleInfoCode());
        for (CourseReqType reqType : reqTypeList) { // starting from most important requirement
            if (!course.isReqFulfilled(reqType, passedModuleList)) {
                // module can contribute towards unfulfilled requirement
                course.putCodeToReqMap(module.getModuleInfoCode(), reqType);
                return true;
            }
        }

        return false; // all course requirements fulfilled
    }
}
