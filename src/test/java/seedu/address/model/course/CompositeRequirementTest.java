package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.course.CompositeRequirement.LogicalConnector.AND;
import static seedu.address.model.course.CompositeRequirement.LogicalConnector.OR;
import static seedu.address.model.course.CourseReqType.CORE;
import static seedu.address.model.course.CourseReqType.GE;
import static seedu.address.model.course.CourseReqType.UE;
import static seedu.address.model.util.SampleCourseRequirement.COMPUTER_SCIENCE_FOUNDATION;
import static seedu.address.model.util.SampleCourseRequirement.SCIENCE_REQUIREMENT;
import static seedu.address.model.util.SampleCourseRequirement.UNIVERSITY_LEVEL_REQUIREMENT;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CompositeRequirementTest {
    public static final CompositeRequirement SAMPLE_REQUIREMENT = new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
            COMPUTER_SCIENCE_FOUNDATION, AND, CORE);
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(null ,
                UNIVERSITY_LEVEL_REQUIREMENT, AND, GE));
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                null, AND, GE));
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                UNIVERSITY_LEVEL_REQUIREMENT, null, GE));
        Assert.assertThrows(NullPointerException.class, () -> new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                UNIVERSITY_LEVEL_REQUIREMENT, OR, null));
    }

    @Test
    public void equal() {
        //same object
        assertTrue(SAMPLE_REQUIREMENT.equals(SAMPLE_REQUIREMENT));
        //not equals null
        assertFalse(SAMPLE_REQUIREMENT.equals(null));
        //not equals a different object
        assertFalse(SAMPLE_REQUIREMENT.equals(5));
        //different first requirement
        assertFalse(SAMPLE_REQUIREMENT.equals(new CompositeRequirement(SCIENCE_REQUIREMENT, COMPUTER_SCIENCE_FOUNDATION,
                AND, CORE)));
        //different second requirement
        assertFalse(SAMPLE_REQUIREMENT.equals(new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                SCIENCE_REQUIREMENT, AND, CORE)));
        //different type
        assertFalse(SAMPLE_REQUIREMENT.equals(new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                COMPUTER_SCIENCE_FOUNDATION, AND, UE)));
        //different connector
        assertFalse(SAMPLE_REQUIREMENT.equals(new CompositeRequirement(UNIVERSITY_LEVEL_REQUIREMENT,
                COMPUTER_SCIENCE_FOUNDATION, OR, UE)));
        //everything same but different order
        assertTrue(SAMPLE_REQUIREMENT.equals(new CompositeRequirement(COMPUTER_SCIENCE_FOUNDATION,
                UNIVERSITY_LEVEL_REQUIREMENT, AND, CORE)));
    }
}
