package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.util.SampleCourse;

/**
 *  Represents course of the user that is enrolled in
 */
public class Course {

    public static final String MESSAGE_REQ_COMPLETED = "All course requirements have been completed.";

    private final CourseName courseName;
    private final CourseDescription courseDescription;
    private final List<CourseRequirement> courseRequirements;

    public Course(CourseName courseName, CourseDescription courseDescription,
                  CourseRequirement... courseRequirements) {
        requireAllNonNull(courseName, courseDescription, courseRequirements);
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseRequirements = Arrays.asList(courseRequirements);
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public CourseDescription getCourseDescription() {
        return courseDescription;
    }

    public List<CourseRequirement> getCourseRequirements() {
        return courseRequirements;
    }

    public static Course getCourseByName(CourseName name) {
        return SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
    }

    /**
     * Returns a list of req types satisfied by the given module code.
     * @param moduleInfoCode The module code.
     * @return A list of req types.
     */
    public List<CourseReqType> getCourseReqTypeOf(ModuleInfoCode moduleInfoCode) {
        List<CourseReqType> reqTypeList = new ArrayList<>();

        for (CourseRequirement courseReq : courseRequirements) {
            if (courseReq.canFulfill(moduleInfoCode)) {
                reqTypeList.add(courseReq.getType());
            }
        }
        Collections.sort(reqTypeList); // sort according to enum ordering

        return reqTypeList;
    }

    /**
     * Checks if the list of module codes satisfy the given req type.
     * @param reqType The req type to be checked against.
     * @param passedModuleList The list of module codes passed.
     * @return
     */
    public boolean isReqFulfilled(CourseReqType reqType, List<ModuleInfoCode> passedModuleList) {
        for (CourseRequirement courseReq : courseRequirements) {
            if (courseReq.getType().equals(reqType)) {
                return courseReq.isFulfilled(passedModuleList);
            }
        }

        return false; // should not reach here
    }
}
