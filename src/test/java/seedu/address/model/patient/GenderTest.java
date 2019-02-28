package seedu.address.model.patient;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(onlySpace));

        String emptyGender = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(emptyGender));

        String onlySymbols = "@";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(onlySymbols));

        String longForm = "Male";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(longForm));

        String invalidGenderCode = "N";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGenderCode));
    }
}
