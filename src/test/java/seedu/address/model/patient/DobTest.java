package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DobTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Dob(null));
    }

    @Test
    public void constructor_invalidDob_throwsIllegalArgumentException() {
        String onlySpace = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(onlySpace));

        String emptyAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(emptyAddress));

        String onlySymbols = "@@-##-$$$$";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(onlySymbols));

        String wrongFormat = "19-09-1999";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(wrongFormat));

        String no31Days = "31-4-2002";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(no31Days));

        String invalidLeapYear = "29-2-2007";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(invalidLeapYear));

        String invalidFebDate = "30-2-2000";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(invalidFebDate));

        String invalidDay = "32-2-2000";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(invalidDay));

        String invalidMonth = "01-13-2000";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Dob(invalidMonth));

    }

    @Test
    public void equals() {
        Dob dob1 = new Dob("1999-09-09");
        assertTrue(dob1.equals(dob1));

        Dob dob2 = new Dob("1999-09-09");
        Dob dob3 = new Dob("1999-09-08");
        assertFalse(dob1.equals(dob3));
        assertTrue(dob1.toString().equals(dob2.toString()));
    }

}
