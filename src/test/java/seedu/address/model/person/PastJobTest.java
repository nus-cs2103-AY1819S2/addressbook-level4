package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PastJobTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new PastJob(null));
    }

    @Test
    public void constructor_invalidPastJob_throwsIllegalArgumentException() {
        String invalidPastJob = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new PastJob(invalidPastJob));
    }

    @Test
    public void isValidPastJob() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> PastJob.isValidPastJob(null));

        // invalid addresses
        assertFalse(PastJob.isValidPastJob("")); // empty string
        assertFalse(PastJob.isValidPastJob(" ")); // spaces only

        // valid addresses
        assertTrue(PastJob.isValidPastJob("Software Development Engineer"));
        assertTrue(PastJob.isValidPastJob("-")); // one character
        assertTrue(PastJob.isValidPastJob("SDE")); // short job title
    }
}
