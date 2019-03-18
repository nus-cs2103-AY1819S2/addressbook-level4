package seedu.hms.model.customer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.testutil.Assert;

public class DateOfBirthTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void isValidDob() {
        // null dob
        Assert.assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDob(null));


        // invalid dob
        assertFalse(DateOfBirth.isValidDob("dob")); // non-numeric
        assertFalse(DateOfBirth.isValidDob("32/13/1999")); // date out of bounds
        assertFalse(DateOfBirth.isValidDob("01/13/1999")); // month out of bounds
        assertFalse(DateOfBirth.isValidDob("29/02/1999")); // not a leap year
        assertFalse(DateOfBirth.isValidDob("30/02/2000")); // february is 28 days only
        assertFalse(DateOfBirth.isValidDob("01/11/2020")); // year can't be equal or greater than current year
        assertFalse(DateOfBirth.isValidDob("01/11/11")); // yy not taken


        // valid dob
        assertTrue(DateOfBirth.isValidDob(""));
        assertTrue(DateOfBirth.isValidDob("29/02/2000"));
        assertTrue(DateOfBirth.isValidDob("1/12/1999")); // date is in correct format
        assertTrue(DateOfBirth.isValidDob("28/05/1999")); // exact order
        assertTrue(DateOfBirth.isValidDob("01/12/1999"));
        assertTrue(DateOfBirth.isValidDob("12/10/1999"));
    }
}
