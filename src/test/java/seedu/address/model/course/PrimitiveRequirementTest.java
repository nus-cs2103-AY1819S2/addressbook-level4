package seedu.address.model.course;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PrimitiveRequirementTest {

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
}
