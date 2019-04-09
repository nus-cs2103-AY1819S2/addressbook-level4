package quickdocs.model.patient;

import org.junit.Test;

import quickdocs.testutil.Assert;

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

    @Test
    public void equals() {
        Address address1 = new Address("1 Simei Road");
        org.junit.Assert.assertTrue(address1.equals(address1));
        Address address2 = new Address("1 Simei Road");
        org.junit.Assert.assertTrue(address1.equals(address2));

        Address address3 = new Address("1 Simei Road");
        Address address4 = new Address("1 SIMEI Road");
        org.junit.Assert.assertFalse(address3.equals(address4));
    }
}
