package seedu.address.model.course;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Checks whether a list of module fulfils course requirement
 */
public interface CourseRequirement extends Predicate<List<ModuleInfo>> {

    /**
     * Returns description of course requirement
     */
    String getDescription();

    /**
     * Returns regular expressions that are not satisfied
     */
    String getUnfulfilled(List<ModuleInfo> moduleInfos);

    @Override
    boolean test(List<ModuleInfo> moduleInfos);
}
