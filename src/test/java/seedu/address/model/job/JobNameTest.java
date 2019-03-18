package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.JobName;
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
        assertFalse(JobName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(JobName.isValidName("peter jack")); // alphabets only
        assertTrue(JobName.isValidName("12345")); // numbers only
        assertTrue(JobName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(JobName.isValidName("Capital Tan")); // with capital letters
        assertTrue(JobName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
