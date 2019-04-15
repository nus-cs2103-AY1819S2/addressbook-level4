package seedu.address.model.course;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.course.CourseReqType.BD;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.UE;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_AI;
import static seedu.address.model.util.SampleCourse.COMPUTER_SCIENCE_ALGORITHMS;
import static seedu.address.model.util.SampleCourseRequirement.getTypicalRequirements;
import static seedu.address.testutil.TypicalModuleTaken.getTypicalModulesInfoCodes;

import java.util.List;

import org.junit.Test;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.testutil.Assert;

public class CourseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        CourseRequirement[] nullArray = null;
        Assert.assertThrows(NullPointerException.class, () -> new Course(null,
            new CourseDescription("Course Description"), getTypicalRequirements().toArray(new CourseRequirement[0])));
        Assert.assertThrows(NullPointerException.class, () -> new Course(new CourseName("CourseName"),
                null, getTypicalRequirements().toArray(new CourseRequirement[0])));
        Assert.assertThrows(NullPointerException.class, () -> new Course(new CourseName("CourseName"),
                new CourseDescription("CourseDescription"), nullArray));
    }

    @Test
    public void equal() {
        Course algorithms = COMPUTER_SCIENCE_ALGORITHMS;
        Course ai = COMPUTER_SCIENCE_AI;
        assertEquals(algorithms, algorithms);
        Course diffCourse = new Course(algorithms.getCourseName(), algorithms.getCourseDescription(),
                algorithms.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertEquals(algorithms, diffCourse);

        assertNotEquals(algorithms, 0);
        assertNotEquals(algorithms, null);
        diffCourse = new Course(ai.getCourseName(), algorithms.getCourseDescription(),
                algorithms.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertNotEquals(diffCourse, algorithms);
        diffCourse = new Course(algorithms.getCourseName(), ai.getCourseDescription(),
                algorithms.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertNotEquals(diffCourse, algorithms);
        diffCourse = new Course(algorithms.getCourseName(), algorithms.getCourseDescription(),
                ai.getCourseRequirements().toArray(new CourseRequirement[0]));
        assertNotEquals(diffCourse, algorithms);
    }

    @Test
    public void getCourseReqTypeOf_typeCoreBdUe() {
        ModuleInfoCode cs3230 = new ModuleInfoCode("CS3230");
        assertEquals(List.of(CORE, BD), COMPUTER_SCIENCE_ALGORITHMS.getCourseReqTypeOf(cs3230));
    }

    @Test
    public void isCodeContributing_geCompletedGeExpectUe_returnTrue() {
        ModuleInfoCode get1100 = new ModuleInfoCode("GET1100");
        List<ModuleInfoCode> moduleInfoCodeList = getTypicalModulesInfoCodes();
        assertTrue(COMPUTER_SCIENCE_ALGORITHMS.isCodeContributing(UE, moduleInfoCodeList, get1100));
    }

    @Test
    public void isCodeContributing_geCompletedGeExpectGe_returnFalse() {
        ModuleInfoCode get1100 = new ModuleInfoCode("GET1100");
        List<ModuleInfoCode> moduleInfoCodeList = getTypicalModulesInfoCodes();
        assertFalse(COMPUTER_SCIENCE_ALGORITHMS.isCodeContributing(GE, moduleInfoCodeList, get1100));
    }
}
