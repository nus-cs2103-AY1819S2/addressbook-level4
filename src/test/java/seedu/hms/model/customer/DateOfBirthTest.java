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
        assertFalse(DateOfBirth.isValidDob("dob").getKey()); // non-numeric
        assertFalse(DateOfBirth.isValidDob("32/13/1999").getKey()); // date out of bounds
        assertFalse(DateOfBirth.isValidDob("01/13/1999").getKey()); // month out of bounds
        assertFalse(DateOfBirth.isValidDob("29/02/1999").getKey()); // not a leap year
        assertFalse(DateOfBirth.isValidDob("30/02/2000").getKey()); // february is 28 days only
        assertFalse(DateOfBirth.isValidDob("01/11/2020").getKey()); // year can't be greater than current year
        assertFalse(DateOfBirth.isValidDob("01/11/11").getKey()); // yy not taken
        assertFalse(DateOfBirth.isValidDob("31/04/11").getKey()); //this month can't have 31 days
        assertFalse(DateOfBirth.isValidDob("31/09/11").getKey());
        assertFalse(DateOfBirth.isValidDob("31/11/11").getKey());
        assertFalse(DateOfBirth.isValidDob("15/05/2019").getKey());


        // valid dob
        assertTrue(DateOfBirth.isValidDob("").getKey());
        assertTrue(DateOfBirth.isValidDob("29/02/2000").getKey());
        assertTrue(DateOfBirth.isValidDob("1/12/1999").getKey()); // date is in correct format
        assertTrue(DateOfBirth.isValidDob("28/05/1999").getKey()); // exact order
        assertTrue(DateOfBirth.isValidDob("01/12/1999").getKey());
        assertTrue(DateOfBirth.isValidDob("12/10/1999").getKey());
        assertTrue(DateOfBirth.isValidDob("").getKey());
    }


}
