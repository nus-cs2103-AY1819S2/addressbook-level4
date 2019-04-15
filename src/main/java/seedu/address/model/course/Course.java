package seedu.address.model.course;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 *  Represents course of the user that is enrolled in
 */
public class Course {
    //TODO: Remove some of the classes such as Course Description
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

    /**
     * Returns a {@code List} of {@code CourseReqType} satisfied by the given {@code ModuleInfoCode}.
     * @param moduleInfoCode The given {@code ModuleInfoCode}.
     * @return A {@code List} of {@code CourseReqType} satisfied by the given {@code ModuleInfoCode}.
     */
    public List<CourseReqType> getCourseReqTypeOf(ModuleInfoCode moduleInfoCode) {
        List<CourseReqType> reqTypeList = new ArrayList<>();

        for (CourseRequirement courseReq : courseRequirements) {
            if (courseReq.getType().equals(CourseReqType.UE)) {
                continue;
            }
            if (courseReq.canFulfill(moduleInfoCode) && !reqTypeList.contains(courseReq.getType())) {
                reqTypeList.add(courseReq.getType());
            }
        }
        Collections.sort(reqTypeList); // sort according to enum ordering
        return reqTypeList;
    }

    /**
     * Checks if the given {@code ModuleInfoCode} contributes to the given {@code CourseReqType} based on
     * the given {@code List} of non-failed {@code ModuleInfoCode}.
     * @param reqType The {@code CourseReqType} to be checked against.
     * @param nonFailedCodeList The {@code List} of non-failed {@code ModuleInfoCode} to be checked against.
     * @param moduleInfoCode The {@code ModuleInfoCode} to be checked.
     * @return true if the given {@code ModuleInfoCode} contributes to the given {@code CourseReqType} based on
     * the given {@code List} of non-failed {@code ModuleInfoCode}, false otherwise.
     */
    public boolean isCodeContributing(CourseReqType reqType, List<ModuleInfoCode> nonFailedCodeList,
                                  ModuleInfoCode moduleInfoCode) {
        for (CourseRequirement courseReq : courseRequirements) {
            if (!courseReq.getType().equals(reqType)) {
                continue;
            }
            if (courseReq.isFulfilled(nonFailedCodeList)) {
                return false;
            }
            List<String> unfulfilledRegexList = courseReq.getUnfulfilled(nonFailedCodeList);
            if (unfulfilledRegexList.stream().anyMatch(regex -> moduleInfoCode.toString().matches(regex))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Course)) {
            return false;
        }
        Course other = (Course) obj;
        return this.courseName.equals(other.courseName)
                && this.courseDescription.equals(other.courseDescription)
                && this.courseRequirements.equals(other.courseRequirements);
    }
}
