package seedu.address.model.moduletaken;

import java.util.List;
import java.util.function.BiPredicate;

import seedu.address.model.course.CourseRequirement;
import seedu.address.model.moduleinfo.ModuleInfoCode;

public class IsUnfulfilledPredicate implements BiPredicate<CourseRequirement, List<ModuleInfoCode>> {

    private final boolean displaysAll;
    private final boolean displaysUnfulfilled;

    public IsUnfulfilledPredicate(boolean displaysAll, boolean displaysUnfulfilled) {
        this.displaysAll = displaysAll;
        this.displaysUnfulfilled = displaysUnfulfilled;
    }

    @Override
    public boolean test(CourseRequirement courseRequirement, List<ModuleInfoCode> moduleInfoCodes) {
        return displaysAll || (courseRequirement.isFulfilled(moduleInfoCodes) != displaysUnfulfilled);
    }
}
