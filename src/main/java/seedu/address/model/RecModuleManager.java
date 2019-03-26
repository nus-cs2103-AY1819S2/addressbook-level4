package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * A class for managing the module recommendation feature.
 */
public class RecModuleManager {

    private final RecModulePredicate predicate;
    private final RecModuleComparator comparator;
    private final HashMap<ModuleInfoCode, CourseReqType> codeToReqMap;

    public RecModuleManager(Course course, VersionedGradTrak versionedGradTrak) {
        requireAllNonNull(course, versionedGradTrak);
        codeToReqMap = new HashMap<>();
        predicate = new RecModulePredicate(course, versionedGradTrak, codeToReqMap);
        comparator = new RecModuleComparator(codeToReqMap);
    }

    public HashMap<ModuleInfoCode, CourseReqType> getCodeToReqMap() {
        return codeToReqMap;
    }

    public RecModulePredicate getRecModulePredicate() {
        return predicate;
    }

    public RecModuleComparator getRecModuleComparator() {
        return comparator;
    }
}
