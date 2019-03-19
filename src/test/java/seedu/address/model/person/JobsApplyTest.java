package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class JobsApplyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new JobsApply(null));
    }

    @Test
    public void constructor_invalidJobsApply_throwsIllegalArgumentException() {
        String invalidJobsApply = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new JobsApply(invalidJobsApply));
    }

    @Test
    public void isValidJobsApply() {
        // null jobs apply
        Assert.assertThrows(NullPointerException.class, () -> JobsApply.isValidJobsApply(null));

        // invalid jobs apply
        assertFalse(JobsApply.isValidJobsApply("")); // empty string
        assertFalse(JobsApply.isValidJobsApply(" ")); // spaces only

        // valid jobs apply
        assertTrue(JobsApply.isValidJobsApply("Software Development Engineer"));
        assertTrue(JobsApply.isValidJobsApply("I")); // one character
        assertTrue(JobsApply.isValidJobsApply("SDE")); // short job title
    }
}
