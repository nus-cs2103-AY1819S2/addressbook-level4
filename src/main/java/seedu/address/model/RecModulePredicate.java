package seedu.address.model;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Tests if a module can be recommended to the user.
 */
public class RecModulePredicate implements Predicate<ModuleInfoCode> {

    private Course course;
    private VersionedGradTrak versionedAddressBook;
    private HashMap<ModuleInfoCode, CourseReqType> codeToReqMap;

    public RecModulePredicate(Course course, VersionedGradTrak versionedAddressBook,
                              HashMap<ModuleInfoCode, CourseReqType> codeToReqMap) {
        this.course = course;
        this.versionedAddressBook = versionedAddressBook;
        this.codeToReqMap = codeToReqMap;
    }

    @Override
    public boolean test(ModuleInfoCode moduleInfoCode) {
        /* module cannot be taken */
        if (!(new EligibleModulePredicate().test(moduleInfoCode))) {
            return false;
        }

        /* module can be taken */
        if (versionedAddressBook.hasPlannedModule(moduleInfoCode.toString())) {
            // module already in plan
            return false;
        }

        List<ModuleInfoCode> passedModuleList = versionedAddressBook.getPassedModuleList();
        List<CourseReqType> reqTypeList = course.getCourseReqTypeOf(moduleInfoCode);
        for (CourseReqType reqType : reqTypeList) { // starting from most important requirement
            if (!course.isReqFulfilled(reqType, passedModuleList)) {
                // module can contribute towards unfulfilled requirement
                codeToReqMap.put(moduleInfoCode, reqType);
                return true;
            }
        }

        return false; // all course requirements fulfilled
    }
}
