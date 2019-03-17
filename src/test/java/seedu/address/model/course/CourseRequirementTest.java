package seedu.address.model.course;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CourseRequirementTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new CourseRequirement(null, x -> true, x -> "String"));
        Assert.assertThrows(NullPointerException.class, ()
            -> new CourseRequirement("Lorem ipsum dolor sit amet", null, x -> "String"));
        Assert.assertThrows(NullPointerException.class, ()
            -> new CourseRequirement("Lorem ipsum dolor sit amet", x -> false, null));
    }
}
