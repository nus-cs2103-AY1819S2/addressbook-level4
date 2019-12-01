package seedu.address.model.medicalhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ValidDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ValidDate(null));
    }

    @Test
    public void constructor_invalidValidDate_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ValidDate(invalidName));
    }

    @Test
    public void isValidName() {
        // null medical history
        Assert.assertThrows(NullPointerException.class, () -> ValidDate.isValidDate(null));

        // invalid medical history
        assertFalse(ValidDate.isValidDate("")); // empty string
        assertFalse(ValidDate.isValidDate("jhjhsabjdhb")); // random string
        assertFalse(ValidDate.isValidDate("535300-25-10")); //not a date
        assertFalse(ValidDate.isValidDate("2020-06-06")); // a future date
        assertFalse(ValidDate.isValidDate("2019-02-29")); // not in calender
        assertFalse(ValidDate.isValidDate("2019/02/02")); // date with incorrect form

        // valid name
        assertTrue(ValidDate.isValidDate("2019-02-28"));
        assertTrue(ValidDate.isValidDate("2016-02-29"));
        assertTrue(ValidDate.isValidDate("2019-02-02"));
    }

}
