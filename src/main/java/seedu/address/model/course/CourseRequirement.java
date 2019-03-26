package seedu.address.model.course;

import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfoCode;

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
     * @param moduleInfoCodes List of module codes to check whether modules satisfy the course requirement
     * @return true if module codes satisfy course requirement
     */
    boolean isFulfilled(List<ModuleInfoCode> moduleInfoCodes);

    /**
     * Returns true if module can be used to satisfy course requirement
     * @param moduleInfoCode module code to check whether can fulfil course requirement
     * @return true if module code can be used to satisfy course requirement
     */
    boolean canFulfill(ModuleInfoCode moduleInfoCode);

    /**
     * Returns a percentage (in the range of [0,1]) of the degree of completion of the course requirement
     * @param moduleInfoCodes module codes to check degree of completion of course requirement
     * @return a double in the range of [0, 1]
     */
    double getFulfilledPercentage(List<ModuleInfoCode> moduleInfoCodes);

    /**
     * Returns a formatted string of the aspects of course requirements
     * unfulfilled by the list of module codes
     * @param moduleInfoCodes module codes to check the aspects of course requirements unfulfilled
     * @return a formatted string in the form
     */
    String getUnfulfilled(List<ModuleInfoCode> moduleInfoCodes);

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
