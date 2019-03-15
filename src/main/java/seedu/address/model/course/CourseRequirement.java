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
<<<<<<< HEAD
    public boolean isFulfilled(List<Module> modulesTaken) {
        double satisfied = modulesTaken.stream()
                                    .filter(x -> modules.contains(x))
                                    .mapToDouble(x -> x.getModuleInfo().getCredits())
                                    .sum();
        return satisfied >= courseReqCredits.getCourseReqCredits();
    }
=======
    String getDescription();
>>>>>>> 3d2b0fdedd70c3e794a0bc7fa1c3f9905fb97b6d

    /**
     * Returns regular expressions that are not satisfied
     */
    String getUnfulfilled(List<ModuleInfo> moduleInfos);

    @Override
    boolean test(List<ModuleInfo> moduleInfos);
}
