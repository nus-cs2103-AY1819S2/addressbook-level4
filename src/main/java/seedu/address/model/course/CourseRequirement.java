package seedu.address.model.course;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfo;
/**
 * API of CourseRequirement
 */
public interface CourseRequirement {


    /**
     * Returns CourseReqType of a CourseRequirement
     * @return CourseReqType of CourseRequirement interface object
     */
    CourseReqType getType();

    /**
     * Returns name of course requirement
     * @return name of course requirement
     */
    String getCourseReqName();

    /**
     * Returns description of course requirement
     * @return description of course requirement
     */
    String getCourseReqDesc();

    /**
     * Returns true if list in modules satisfy the course requirement
     * @param moduleInfos List of moduleInfo to check whether modules satisfy the course requirement
     * @return true if moduleInfos satisfy course requirement
     */
    boolean isFulfilled(List<ModuleInfo> moduleInfos);

    /**
     * Returns true if module can be used to satisfy course requirement
     * @param moduleInfo moduleInfo to check whether can fulfil course requirement
     * @return true if moduleInfo can be used to satisfy course requirement
     */
    boolean canFulfill(ModuleInfo moduleInfo);

    /**
     * Returns a percentage (in the range of [0,1]) of the degree of completion of the course requirement
     * @param moduleInfos moduleInfos to check degree of completion of course requirement
     * @return a double in the range of [0, 1]
     */
    double getFulfilledPercentage(List<ModuleInfo> moduleInfos);

    /**
     * Returns a formatted string of the aspects of course requirements
     * unfulfilled by the list of module infos
     * @param moduleInfos moduleInfos to check the aspects of course requirements unfulfilled
     * @return a formatted string in the form
     */
    String getUnfulfilled(List<ModuleInfo> moduleInfos);

    /**
     * Returns a composite CourseRequirement whose boolean methods returns
     * first.booleanMethod() && second.booleanMethod()
     * for instance: first.And(second).canFulfill(moduleInfo)
     * returns the same value as first.canFulfill(moduleInfo) && second.canFulfill(moduleInfo)
     * @param other CourseRequirement to combine
     * @return new CourseRequirement whose boolean method returns first.booleanMethod() && second.booleanMethod()
     */
    CourseRequirement and(CourseRequirement other);


    /**
     * Returns a composite CourseRequirement whose boolean methods returns
     * first.booleanMethod() || second.booleanMethod()
     * for instance: first.Or(second).canFulfill(moduleInfo)
     * is returns the same value as first.canFulfill(moduleInfo) || second.canFulfill(moduleInfo)
     * @param other CourseRequirement to combine
     * @return new CourseRequirement whose boolean method returns first.booleanMethod() || second.booleanMethod()
     */
    CourseRequirement or(CourseRequirement other);


}
