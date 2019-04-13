package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MatricNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MatricNumber(null));
    }

    @Test
    public void constructor_invalidMatricNumber_throwsIllegalArgumentException() {
        String invalidMatricNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MatricNumber(invalidMatricNumber));
    }

    @Test
    public void isValidMatricNumber() {
        // null MatricNumber
        Assert.assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        // invalid MatricNumber
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber(" ")); // spaces only
        assertFalse(MatricNumber.isValidMatricNumber("A01234567L")); // more than 7 digits
        assertFalse(MatricNumber.isValidMatricNumber("B0123456J")); // does not start with A
        assertFalse(MatricNumber.isValidMatricNumber("A0123456H")); // invalid checksum
        assertFalse(MatricNumber.isValidMatricNumber("12345678")); // all digits

        // valid MatricNumber
        assertTrue(MatricNumber.isValidMatricNumber("A0123456J"));
    }
}
