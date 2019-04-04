package seedu.address.model.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.ConditionBuilder;
import seedu.address.testutil.TypicalCondition;

public class ConditionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        String[] nullArray = null;
        Assert.assertThrows(NullPointerException.class, () -> new Condition(null));
        Assert.assertThrows(NullPointerException.class, () -> new Condition(1, null));
    }

    @Test
    public void constructor_invalidRegex_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(1, "(([]})"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition("(InvalidRegex}"));
    }

    @Test
    public void constructor_invalidMinToSatisfy_throwsInvalidArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(-1, "valid regex"));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Condition(0, "another valid regex"));
    }

    @Test
    public void equal() {
        Condition ulr = TypicalCondition.ULR;
        assertEquals(ulr, ulr);
        //not equals null
        assertNotEquals(null, ulr);
        //not equals other type
        assertNotEquals(ulr, 0);

        //not equals when different min
        Condition modifiedUlr = new ConditionBuilder().withMin(3).build();
        assertNotEquals(modifiedUlr, ulr);
        //alternate constructor for Condition
        modifiedUlr = new ConditionBuilder().withMin(1).build();
        Condition modifiedUlr2 = new Condition(ConditionBuilder.DEFAULT_PATTERN.toString());
        assertEquals(modifiedUlr, modifiedUlr2);
    }
}
