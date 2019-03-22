package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * Represents a CourseRequirement that at least one of the conditions
 * are completely satisfied.
 */
public class CompletedAll implements CourseRequirement {

    private final List<Condition> conditions;
    private final String courseReqName;
    private final String courseReqDesc;

    public CompletedAll(String courseReqName, String courseReqDesc, Condition... conditions) {
        requireAllNonNull(courseReqName, courseReqDesc, conditions);
        requireAllNonNull((Object []) conditions);
        this.courseReqName = courseReqName;
        this.courseReqDesc = courseReqDesc;
        this.conditions = List.of(conditions);
    }

    @Override
    public String getCourseReqDesc() {
        return courseReqDesc;
    }

    @Override
    public boolean isFulfilled(List<ModuleInfo> moduleInfos) {
        return conditions.stream().anyMatch(condition -> condition.isSatisfied(moduleInfos));
    }

    @Override
    public boolean canFulfill(ModuleInfo moduleInfo) {
        return conditions.stream().anyMatch(condition -> condition.canSatisfy(moduleInfo));
    }

    @Override
    public double getFulfilledPercentage(List<ModuleInfo> moduleInfos) {
        return conditions.stream().mapToDouble(condition -> condition.getPercentageCompleted(moduleInfos))
                .max().getAsDouble();
    }

    @Override
    public String getUnfulfilled(List<ModuleInfo> moduleInfos) {
        //might return a list of modules instead
        return conditions.stream()
                .map(condition -> condition.getUnsatisfied(moduleInfos))
                .reduce((x, y) -> x + "\n + OR \n" + y).get();
    }
}
