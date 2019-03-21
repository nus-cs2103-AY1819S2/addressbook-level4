package seedu.address.model.course;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Checks number of modules that
 */
public class CompletedAtLeast implements CourseRequirement {

    @Override
    public String getCourseReqDesc() {
        return null;
    }

    @Override
    public boolean isFulfilled(List<ModuleInfo> moduleInfos) {
        return false;
    }

    @Override
    public boolean canFulfill(ModuleInfo moduleInfo) {
        return false;
    }

    @Override
    public double getFulfilledPercentage(List<ModuleInfo> moduleInfos) {
        return 0;
    }

    @Override
    public String getUnfulfilled(List<ModuleInfo> moduleInfos) {
        return null;
    }
}
