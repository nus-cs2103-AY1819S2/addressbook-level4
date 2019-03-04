package seedu.address.model.customer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IdentificationNoTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IdentificationNo(null));
    }

    @Test
    public void constructorInvalidIdThrowsIllegalArgumentException() {
        String invalidIdentificationNo = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IdentificationNo(invalidIdentificationNo));
    }

    @Test
    public void isValidIdNum() {
        // null identification number
        Assert.assertThrows(NullPointerException.class, () -> IdentificationNo.isValidIdNum(null));

        // invalid identification numbers
        assertFalse(IdentificationNo.isValidIdNum("")); // empty string
        assertFalse(IdentificationNo.isValidIdNum(" ")); // spaces only
        assertFalse(IdentificationNo.isValidIdNum("91")); // less than 3 numbers
        assertFalse(IdentificationNo.isValidIdNum("idnum")); // non-numeric
        assertFalse(IdentificationNo.isValidIdNum("9011p041")); // alphabets within digits
        assertFalse(IdentificationNo.isValidIdNum("9312 1534")); // spaces within digits

        // valid identification numbers
        assertTrue(IdentificationNo.isValidIdNum("911")); // exactly 3 numbers
        assertTrue(IdentificationNo.isValidIdNum("93121534"));
        assertTrue(IdentificationNo.isValidIdNum("124293842033123")); // long identification numbers
    }
}
