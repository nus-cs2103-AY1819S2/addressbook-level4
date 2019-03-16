package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContainsAtLeastTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new ContainsAtLeast(null, 0, "CS2101"));
        String[] strings = {null, null};
        Assert.assertThrows(NullPointerException.class, ()
                -> new ContainsAtLeast(null, 0, null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new ContainsAtLeast("Lorem ipsum dolor sit amet", 0, strings));
    }

    @Test
    public void isValidNumber() {

        //invalid number
        assertFalse(ContainsAtLeast.isValidNumber(-2));
        assertFalse(ContainsAtLeast.isValidNumber(-123198732));

        //valid number
        assertTrue(ContainsAtLeast.isValidNumber(4));
        assertTrue(ContainsAtLeast.isValidNumber(0));
    }
}
