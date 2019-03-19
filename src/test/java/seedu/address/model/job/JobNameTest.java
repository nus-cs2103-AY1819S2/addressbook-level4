package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class JobNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> JobName.isValidName(null));

        // invalid name
        assertFalse(JobName.isValidName("")); // empty string
        assertFalse(JobName.isValidName(" ")); // spaces only
        assertFalse(JobName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(JobName.isValidName("vampire*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobName.isValidName("search engineer")); // alphabets only
        assertTrue(JobName.isValidName("12345")); // numbers only
        assertTrue(JobName.isValidName("2nd Sergent")); // alphanumeric characters
        assertTrue(JobName.isValidName("Capital Tan")); // with capital letters
        assertTrue(JobName.isValidName("Her Royal Majesty the Queen of England")); // long names
    }
}
