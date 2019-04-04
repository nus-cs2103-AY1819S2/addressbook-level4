package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.UE;
import static seedu.address.model.util.SampleCourseRequirement.COMPLETE_40_MODULES;
import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.IT_PROFESSIONALISM;
import static seedu.address.model.util.SampleCourseRequirement.ULR_CONDITION;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import static seedu.address.testutil.TypicalCondition.ULR;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PrimitiveRequirementTest {
    public static final PrimitiveRequirement SAMPLE_REQUIREMENT = (PrimitiveRequirement) UNIVERSITY_LEVEL_REQUIREMENT;
    public static final Condition SAMPLE_CONDITION = ULR_CONDITION;
    public static final PrimitiveRequirement SAMPLE_REQUIREMENT_2 = (PrimitiveRequirement) COMPUTER_SCIENCE_FOUNDATION;
    public static final PrimitiveRequirement SAMPLE_REQUIREMENT_3 = (PrimitiveRequirement) IT_PROFESSIONALISM;
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement(null , "some description",
                GE, ULR));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("ReqName",
               null, GE, ULR));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
               "nonNullDescription", GE, null));
        Assert.assertThrows(NullPointerException.class, () -> new PrimitiveRequirement("Reqname",
                "nonNullDescription", null, ULR));
    }

    @Test
    public void equal() {
        //same object
        assertTrue(SAMPLE_REQUIREMENT.equals(SAMPLE_REQUIREMENT));
        //not equals null
        assertFalse(SAMPLE_REQUIREMENT.equals(null));
        //not equals a different object
        assertFalse(SAMPLE_REQUIREMENT.equals(5));
        //different name
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement("Other name",
            SAMPLE_REQUIREMENT.getCourseReqDesc(), SAMPLE_REQUIREMENT.getType(), SAMPLE_CONDITION)));
        //different description
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            "other description", SAMPLE_REQUIREMENT.getType(), SAMPLE_CONDITION)));
        //different condition
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            SAMPLE_REQUIREMENT.getCourseReqDesc(), SAMPLE_REQUIREMENT.getType(), COMPLETE_40_MODULES)));
        //different type
        assertFalse(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            SAMPLE_REQUIREMENT.getCourseReqDesc(), UE, SAMPLE_CONDITION)));
        //everything same but different object
        assertTrue(SAMPLE_REQUIREMENT.equals(new PrimitiveRequirement(SAMPLE_REQUIREMENT.getCourseReqName(),
            SAMPLE_REQUIREMENT.getCourseReqDesc(), SAMPLE_REQUIREMENT.getType(), SAMPLE_CONDITION)));
    }
    @Test
    public void or() {
        assertEquals(SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.or(new PrimitiveRequirement(
            SAMPLE_REQUIREMENT_2.getCourseReqName(), SAMPLE_REQUIREMENT_2.getCourseReqDesc(),
            SAMPLE_REQUIREMENT_2.getType(), SAMPLE_REQUIREMENT_2.getConditions().toArray(new Condition[0]))));
        assertNotEquals(SAMPLE_REQUIREMENT_2.or(SAMPLE_REQUIREMENT), SAMPLE_REQUIREMENT.or(SAMPLE_REQUIREMENT_2));
        assertEquals(SAMPLE_REQUIREMENT_2.or(SAMPLE_REQUIREMENT_3),
            SAMPLE_REQUIREMENT_3.or(SAMPLE_REQUIREMENT_2));
    }

    @Test
    public void and() {
        assertEquals(SAMPLE_REQUIREMENT.and(SAMPLE_REQUIREMENT_2), SAMPLE_REQUIREMENT.and(new PrimitiveRequirement(
            SAMPLE_REQUIREMENT_2.getCourseReqName(), SAMPLE_REQUIREMENT_2.getCourseReqDesc(),
            SAMPLE_REQUIREMENT_2.getType(), SAMPLE_REQUIREMENT_2.getConditions().toArray(new Condition[0]))));
        assertNotEquals(SAMPLE_REQUIREMENT_2.and(SAMPLE_REQUIREMENT), SAMPLE_REQUIREMENT.and(SAMPLE_REQUIREMENT_2));
        assertEquals(SAMPLE_REQUIREMENT_2.and(SAMPLE_REQUIREMENT_3), SAMPLE_REQUIREMENT_3.and(SAMPLE_REQUIREMENT_2));
    }
}
