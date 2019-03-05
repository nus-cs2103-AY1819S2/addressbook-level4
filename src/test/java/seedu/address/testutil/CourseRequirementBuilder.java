package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.course.CourseReqCredits;
import seedu.address.model.course.CourseReqDesc;
import seedu.address.model.course.CourseReqName;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.module.Module;

/**
 * Helper class to build course requirement for testing
 */
public class CourseRequirementBuilder {

    public static final String DEFAULT_COURSEREQNAME = "Computer Science Foundations";
    public static final String DEFAULT_COURSEREQDESC = "Pass all of: "
            + "CS1101S Programming Methodology"
            + "CS1231 Discrete Structures "
            + "CS2030 Programming Methodology II "
            + "CS2040 Data Structures and Algorithms "
            + "CS2100 Computer Organisation "
            + "CS2103T Software Engineering "
            + "CS2105 Introduction to Computer Networks "
            + "CS2106 Introduction to Operating Systems "
            + "CS3230 Design and Analysis of Algorithms";
    public static final int DEFAULT_COURSEREQCREDIT = 20;
    public static final List<Module> DEFAULT_MODULES = Arrays.asList(TypicalModules.CS2030,
                    TypicalModules.CS2040, TypicalModules.CS2040C,
                    TypicalModules.CS1010, TypicalModules.CS2103T);

    private CourseReqName courseReqName;
    private CourseReqDesc courseReqDesc;
    private CourseReqCredits courseReqCredits;
    private List<Module> modules;

    public CourseRequirementBuilder() {
        this.courseReqName = new CourseReqName(DEFAULT_COURSEREQNAME);
        this.courseReqCredits = new CourseReqCredits(DEFAULT_COURSEREQCREDIT);
        this.courseReqDesc = new CourseReqDesc(DEFAULT_COURSEREQDESC);
        this.modules = DEFAULT_MODULES;
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public CourseRequirementBuilder(CourseRequirement courseRequirement) {
        courseReqName = courseRequirement.getCourseReqName();
        courseReqDesc = courseRequirement.getCourseReqDesc();
        courseReqDesc = courseRequirement.getCourseReqDesc();
        modules = courseRequirement.getModules();
        courseReqCredits = courseRequirement.getCourseReqCredits();
    }

    /**
     * Sets the {@code courseReqName} of the {@code Module} that we are building
     */
    public CourseRequirementBuilder withCourseReqName(String courseReqName) {
        this.courseReqName = new CourseReqName(courseReqName);
        return this;
    }

    /**
     * Sets the {@code courseReqDesc} of the {@code Module} that we are building.
     */
    public CourseRequirementBuilder withCourseReqDesc(String courseReqDesc) {
        this.courseReqDesc = new CourseReqDesc(courseReqDesc);
        return this;
    }

    /**
     * Sets the {@code courseReqCredits} of the {@code Module} that we are building
     */
    public CourseRequirementBuilder withCourseReqCredits(int credit) {
        this.courseReqCredits = new CourseReqCredits(credit);
        return this;
    }


    /**
     * Sets the {@code modules} of the {@code Module} that we are building.
     */
    public CourseRequirementBuilder withModules(Module... modules) {
        this.modules = Arrays.asList(modules);
        return this;
    }

    /**
     * Returns a CourseRequirement with courseReqName, courseReqDesc,
     * courseReqCredits, modules decided by test.
     */
    public CourseRequirement build() {
        return new CourseRequirement(courseReqName, courseReqDesc,
                courseReqCredits, modules.toArray(new Module[0]));
    }

}
