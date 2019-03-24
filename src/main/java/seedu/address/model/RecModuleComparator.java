package seedu.address.model;

import java.util.Comparator;
import java.util.HashMap;

import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Compares two ModuleInfos based on CourseReqType and ModuleInfoCode.
 */
public class RecModuleComparator implements Comparator<ModuleInfo> {

    private final Course course;

    public RecModuleComparator(Course course) {
        this.course = course;
    }

    @Override
    public int compare(ModuleInfo first, ModuleInfo second) {
        HashMap<ModuleInfoCode, CourseReqType> codeToReqMap = course.getCodeToReqMap();
        CourseReqType firstReqType = codeToReqMap.get(first.getModuleInfoCode());
        CourseReqType secondReqType = codeToReqMap.get(second.getModuleInfoCode());

        // different req type -> compare priority of req type
        if (!firstReqType.equals(secondReqType)) {
            return firstReqType.compareTo(secondReqType);
        }

        // same req type -> compare lexicographical order of code
        return first.getCodeString().compareTo(second.getCodeString());
    }
}
