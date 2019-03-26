package seedu.address.model.card;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Option(null));
    }

    @Test
    public void constructor_invalidOptionValue_throwsIllegalArgumentException() {
        String invalidOptionValue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Option(invalidOptionValue));
    }

    @Test
    public void isValidOptionValue() {
        // null option value
        Assert.assertThrows(NullPointerException.class, () -> Option.isValidOption(null));
    }
}
