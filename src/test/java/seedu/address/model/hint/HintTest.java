package seedu.address.model.hint;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class HintTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Hint(null));
    }

    @Test
    public void constructor_invalidHintValue_throwsIllegalArgumentException() {
        String invalidHintValue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Hint(invalidHintValue));
    }

    @Test
    public void isValidHintName() {
        // null hint name
        Assert.assertThrows(NullPointerException.class, () -> Hint.isValidHintName(null));
    }

}
