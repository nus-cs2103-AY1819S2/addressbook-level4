package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.util.SampleCourseRequirement;
import seedu.address.testutil.Assert;

public class PrimitiveRequirementTest {
    public static final PrimitiveRequirement sampleRequirement =
            (PrimitiveRequirement) SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;
    public static final Condition sampleCondition = SampleCourseRequirement.ULR_CONDITION;
    public static final PrimitiveRequirement sampleRequirement2 =
            (PrimitiveRequirement) SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
    public static final PrimitiveRequirement getSampleRequirement3 =
            (PrimitiveRequirement) SampleCourseRequirement.IT_PROFESSIONALISM;
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement(null,
                "SomeDescription", new Condition("University Level Requirement",
                "GES", "GEH", "GET", "GER", "GEQ"), CourseReqType.GE));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("ReqName",
                null, new Condition("University Level Requirement",
                "GES", "GEH", "GET", "GER", "GEQ"), CourseReqType.GE));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
                "nonNullDescription", null, CourseReqType.GE));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
                "nonNullDescription", new Condition("nonNullName", "CS2103"),
                null));
    }

    @Test
    public void equal() {
        //same object
        assertTrue(sampleRequirement.equals(sampleRequirement));

        //not equals null
        assertFalse(sampleRequirement.equals(null));

        //not equals another object
        assertFalse(sampleRequirement.equals(5));

        //different name
        assertFalse(sampleRequirement.equals(new PrimitiveRequirement("Other name",
                sampleRequirement.getCourseReqDesc(), sampleCondition, sampleRequirement.getType())));
        //different description
        assertFalse(sampleRequirement.equals(new PrimitiveRequirement(sampleRequirement.getCourseReqName(),
                "other description", sampleCondition, sampleRequirement.getType())));

        //different condition
        assertFalse(sampleRequirement.equals(new PrimitiveRequirement(sampleRequirement.getCourseReqName(),
                sampleRequirement.getCourseReqDesc(), SampleCourseRequirement.COMPLETE_40_MODULES,
                sampleRequirement.getType())));

        //different type
        assertFalse(sampleRequirement.equals(new PrimitiveRequirement(sampleRequirement.getCourseReqName(),
                sampleRequirement.getCourseReqDesc(), sampleCondition,
                CourseReqType.UE)));

        //everything same but different object
        assertTrue(sampleRequirement.equals(new PrimitiveRequirement(sampleRequirement.getCourseReqName(),
                sampleRequirement.getCourseReqDesc(), sampleCondition,
                sampleRequirement.getType())));
    }

    @Test
    public void or() {
        assertEquals(sampleRequirement.or(sampleRequirement2), sampleRequirement.or(
                new PrimitiveRequirement(sampleRequirement2.getCourseReqName(),
                        sampleRequirement2.getCourseReqDesc(),
                        sampleRequirement2.getCondition(), sampleRequirement2.getType())));
        assertNotEquals(sampleRequirement2.or(sampleRequirement), sampleRequirement.or(sampleRequirement2));
        assertEquals(sampleRequirement2.or(getSampleRequirement3),
                getSampleRequirement3.or(sampleRequirement2));
    }

    @Test
    public void and() {
        assertEquals(sampleRequirement.and(sampleRequirement2), sampleRequirement.and(
                new PrimitiveRequirement(sampleRequirement2.getCourseReqName(),
                        sampleRequirement2.getCourseReqDesc(),
                        sampleRequirement2.getCondition(), sampleRequirement2.getType())));
        assertNotEquals(sampleRequirement2.and(sampleRequirement), sampleRequirement.and(sampleRequirement2));
        assertEquals(sampleRequirement2.and(getSampleRequirement3),
                getSampleRequirement3.and(sampleRequirement2));
    }

}
