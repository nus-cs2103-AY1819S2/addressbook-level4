package seedu.address.testutil;

import static seedu.address.testutil.TypicalModules.CS1010;
import static seedu.address.testutil.TypicalModules.CS2030;
import static seedu.address.testutil.TypicalModules.CS2040;
import static seedu.address.testutil.TypicalModules.CS2103T;
import static seedu.address.testutil.TypicalModules.LSM1301;
import static seedu.address.testutil.TypicalModules.MA1512;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.course.CourseReqCredits;
import seedu.address.model.course.CourseReqDesc;
import seedu.address.model.course.CourseReqName;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.module.Module;

/**
 * Class that stores typical course requirements
 */
public class TypicalCourseRequirements {

    public static final CourseRequirement TOTAL_CREDITS = new CourseRequirement(
            new CourseReqName("Total Credits Count"), new CourseReqDesc("Clear 160 MCs"),
            new CourseReqCredits(160), new Module[0]);

    public static final CourseRequirement CS_FOUNDATIONS = new CourseRequirement(
            new CourseReqName("Computer Science Foundation"),
            new CourseReqDesc("Pass all of CS1010, CS2030, CS2040 and CS2103T"),
            new CourseReqCredits(16), CS1010, CS2030, CS2040, CS2103T);

    public static final CourseRequirement SCIENCE_REQUIREMENTS = new CourseRequirement(
            new CourseReqName("Science Module"),
            new CourseReqDesc("Complete at least 1 Science Module"),
            new CourseReqCredits(4), MA1512, LSM1301);

    private TypicalCourseRequirements() { }


    public static List<CourseRequirement> getTypicalModules() {
        return Arrays.asList(TOTAL_CREDITS, CS_FOUNDATIONS, SCIENCE_REQUIREMENTS);
    }
}
