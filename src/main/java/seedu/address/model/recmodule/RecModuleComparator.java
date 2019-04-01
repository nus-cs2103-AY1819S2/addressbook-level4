package seedu.address.model.recmodule;

import java.util.Comparator;

import seedu.address.model.course.CourseReqType;

/**
 * Compares two {@code RecModule} based on {@code CourseReqType} and {@code ModuleInfoCode}.
 */
public class RecModuleComparator implements Comparator<RecModule> {

    @Override
    public int compare(RecModule first, RecModule second) {
        // req type satisfied by first and second must be present at time of comparison
        assert(first.getCourseReqType().isPresent() && second.getCourseReqType().isPresent());
        CourseReqType firstReqType = first.getCourseReqType().get();
        CourseReqType secondReqType = second.getCourseReqType().get();

        // different req type -> compare priority of req type
        if (!firstReqType.equals(secondReqType)) {
            return firstReqType.compareTo(secondReqType);
        }

        // same req type -> compare lexicographical order of code
        return first.getModuleInfoCode().toString().compareTo(second.getModuleInfoCode().toString());
    }
}
