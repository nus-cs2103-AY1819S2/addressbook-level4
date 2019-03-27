package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;

import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Compares two ModuleInfoCodes based on CourseReqType and lexicographical order.
 */
public class RecModuleComparator implements Comparator<ModuleInfoCode> {

    private final HashMap<ModuleInfoCode, CourseReqType> codeToReqMap;

    public RecModuleComparator(HashMap<ModuleInfoCode, CourseReqType> codeToReqMap) {
        requireNonNull(codeToReqMap);
        this.codeToReqMap = codeToReqMap;
    }

    @Override
    public int compare(ModuleInfoCode first, ModuleInfoCode second) {
        CourseReqType firstReqType = codeToReqMap.get(first);
        CourseReqType secondReqType = codeToReqMap.get(second);
        assert (firstReqType != null && secondReqType != null);

        // different req type -> compare priority of req type
        if (!firstReqType.equals(secondReqType)) {
            return firstReqType.compareTo(secondReqType);
        }

        // same req type -> compare lexicographical order of code
        return first.toString().compareTo(second.toString());
    }
}
