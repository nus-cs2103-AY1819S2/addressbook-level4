package seedu.address.model.patient;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(onlySpace));

        String emptyAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(emptyAddress));

        String onlySymbols = "@\\/3n()3";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(onlySymbols));
    }
}
