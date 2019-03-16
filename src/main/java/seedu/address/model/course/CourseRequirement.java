package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Checks whether a list of module fulfils course requirement
 */
public class CourseRequirement {

    protected final String description;
    protected final Predicate<List<ModuleInfo>> isFulfilled;
    protected final Function<List<ModuleInfo>, String> getUnfulfilled;

    public CourseRequirement (String description,
                              Predicate<List<ModuleInfo>> isFulfilled,
                              Function<List<ModuleInfo>, String> getUnfulfilled) {
        requireAllNonNull(description, isFulfilled, getUnfulfilled);
        this.description = description;
        this.isFulfilled = isFulfilled;
        this.getUnfulfilled = getUnfulfilled;
    }

    /**
     * Returns description of course requirement
     */
<<<<<<< HEAD
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
=======
    public String getDescription() {
        return description;
    }
>>>>>>> 37add1c4ec7b6bee8c90bcefd83f26830b0d0546

    /**
     * Returns regular expressions that are not satisfied
     */
    public String getUnfulfilled(List<ModuleInfo> moduleInfos) {
        return getUnfulfilled.apply(moduleInfos);
    };

    public boolean test(List<ModuleInfo> moduleInfos) {
        return isFulfilled.test(moduleInfos);
    };

    /**
     * Returns Course Requirement that has test method such that it returns true only if both
     * this.test() and other.test() returns true. Description, and getUnfulfilled is concatenated.
     * @param other
     * @return Returns Course Requirement where its test method returns true only if this.test()
     * and other.test() is true;
     */
    public CourseRequirement and(CourseRequirement other) {
        return new CourseRequirement(description + "\n AND \n" + other.description,
            isFulfilled.and(other.isFulfilled), (
                    List<ModuleInfo> x) -> getUnfulfilled(x) + "\n AND \n" + other.getUnfulfilled(x));
    }


    /**
     * Returns Course Requirement that has test method such that it returns true only at least one of
     * this.test() or other.test() returns true. Description, and getUnfulfilled is concatenated.
     * @param other
     * @return Returns Course Requirement where its test method returns true only if at least one of
     * this.test() or other.test() is true;
     */
    public CourseRequirement or(CourseRequirement other) {
        return new CourseRequirement(description + "\n OR \n" + other.description,
            isFulfilled.or(other.isFulfilled), (
                    List<ModuleInfo> x) -> getUnfulfilled(x) + "\n OR \n" + other.getUnfulfilled(x));
    }
}
