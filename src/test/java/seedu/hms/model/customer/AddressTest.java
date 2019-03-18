package seedu.hms.model.customer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
<<<<<<< HEAD:src/test/java/seedu/address/model/customer/AddressTest.java
=======
    public void constructor_invalidhms_throwsIllegalArgumentException() {
        String invalidhms = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(invalidhms));
    }

    @Test
>>>>>>> f32e851bb9479d863dbfa54cb18c56bf0c85fbd6:src/test/java/seedu/hms/model/customer/AddressTest.java
    public void isValidAddress() {
        // null hms
        Assert.assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

<<<<<<< HEAD:src/test/java/seedu/address/model/customer/AddressTest.java
=======
        // invalid hmses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
>>>>>>> f32e851bb9479d863dbfa54cb18c56bf0c85fbd6:src/test/java/seedu/hms/model/customer/AddressTest.java

        // valid hmses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long hms
    }
}
