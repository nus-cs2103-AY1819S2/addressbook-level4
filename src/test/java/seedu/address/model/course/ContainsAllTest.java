package seedu.address.model.course;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ContainsAllTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, ()
            -> new ContainsAll(null, "CS2101"));
        String[] strings = {null, null};
        Assert.assertThrows(NullPointerException.class, ()
                -> new ContainsAll("Lorem ipsum dolor sit amet", null));
        Assert.assertThrows(NullPointerException.class, ()
            -> new ContainsAll("Lorem ipsum dolor sit amet", strings));
    }

}
