package seedu.address.model.tag;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Condition(null));
    }

    @Test
    public void constructor_invalidConditionName_throwsIllegalArgumentException() {
        String invalidConditionName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(invalidConditionName));
    }

    @Test
    public void isValidConditionName() {
        // null condition name
        Assert.assertThrows(NullPointerException.class, () -> Condition.isValidConditionName(null));
    }

}
