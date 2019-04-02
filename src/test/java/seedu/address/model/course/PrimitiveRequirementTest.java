package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.util.SampleCourseRequirement;
import seedu.address.testutil.Assert;

public class PrimitiveRequirementTest {
//    public static final PrimitiveRequirement SAMPLE_REQUIREMENT =
//            (PrimitiveRequirement) SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;
//    public static final Condition sampleCondition = SampleCourseRequirement.ULR_CONDITION;
//    public static final PrimitiveRequirement SAMPLE_REQUIREMENT_2 =
//            (PrimitiveRequirement) SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
//    public static final PrimitiveRequirement SAMPLE_REQUIREMENT_3 =
//            (PrimitiveRequirement) SampleCourseRequirement.IT_PROFESSIONALISM;
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement(null,
//                "SomeDescription", new Condition("University Level Requirement",
//                "GES", "GEH", "GET", "GER", "GEQ"), CourseReqType.GE));
//        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("ReqName",
//                null, new Condition("University Level Requirement",
//                "GES", "GEH", "GET", "GER", "GEQ"), CourseReqType.GE));
//        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
//                "nonNullDescription", null, CourseReqType.GE));
//        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
//                "nonNullDescription", new Condition("nonNullName", "CS2103"),
//                null));
//    }
//
//    @Test
//    public void equal() {
//        //same object
//        assertTrue(SAMPLE_REQUIREMENT.equals(SAMPLE_REQUIREMENT));
//
//        //not equals null
//        assertFalse(SAMPLE_REQUIREMENT.equals(null));
//
//        //not equals another object
//        assertFalse(SAMPLE_REQUIREMENT.equals(5));
//
//        //different name
//        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement("Other name",
//                SAMPLE_REQUIREMENT.getCourseReqDesc(), sampleCondition, SAMPLE_REQUIREMENT.getType())));
//        //different description
//        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
//                "other description", sampleCondition, SAMPLE_REQUIREMENT.getType())));
//
//        //different condition
//        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
//                SAMPLE_REQUIREMENT.getCourseReqDesc(), SampleCourseRequirement.COMPLETE_40_MODULES,
//                SAMPLE_REQUIREMENT.getType())));
//
//        //different type
//        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
//                SAMPLE_REQUIREMENT.getCourseReqDesc(), sampleCondition,
//                CourseReqType.UE)));
//
//        //everything same but different object
//        assertTrue(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
//                SAMPLE_REQUIREMENT.getCourseReqDesc(), sampleCondition,
//                SAMPLE_REQUIREMENT.getType())));
//    }
//
//    @Test
//    public void or() {
//        assertEquals(SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.or(
//                new PrimitiveRequirement(SAMPLE_REQUIREMENT_2.getCourseReqName(),
//                        SAMPLE_REQUIREMENT_2.getCourseReqDesc(),
//                        SAMPLE_REQUIREMENT_2.getCondition(), SAMPLE_REQUIREMENT_2.getType())));
//        assertNotEquals(SAMPLE_REQUIREMENT_2.or(SAMPLE_REQUIREMENT), SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2));
//        assertEquals(SAMPLE_REQUIREMENT_2.or(SAMPLE_REQUIREMENT_3),
//                SAMPLE_REQUIREMENT_3.or(SAMPLE_REQUIREMENT_2));
//    }
//
//    @Test
//    public void and() {
//        assertEquals(SAMPLE_REQUIREMENT.and(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.and(
//                new PrimitiveRequirement(SAMPLE_REQUIREMENT_2.getCourseReqName(),
//                        SAMPLE_REQUIREMENT_2.getCourseReqDesc(),
//                        SAMPLE_REQUIREMENT_2.getCondition(), SAMPLE_REQUIREMENT_2.getType())));
//        assertNotEquals(SAMPLE_REQUIREMENT_2.and(SAMPLE_REQUIREMENT), SAMPLE_REQUIREMENT.and(SAMPLE_REQUIREMENT_2));
//        assertEquals(SAMPLE_REQUIREMENT_2.and(SAMPLE_REQUIREMENT_3),
//                SAMPLE_REQUIREMENT_3.and(SAMPLE_REQUIREMENT_2));
//    }

}
