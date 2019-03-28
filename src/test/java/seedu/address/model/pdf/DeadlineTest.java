package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeadlineTest {

    @Test
    public void constructor_nullValue_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void jsonConstructor_missingStatus_throwsNullPointerException() {
        Assert.assertThrows(DateTimeParseException.class, () -> new Deadline("22-12-2012"));
    }

    @Test
    public void jsonConstructor_invalidDate_throwsNullPointerException() {
        Assert.assertThrows(DateTimeParseException.class, () -> new Deadline("22-13-2012"));
    }

    @Test
    public void normalConstructor_invalidDate_throwsNullPointerException() {
        Assert.assertThrows(DateTimeParseException.class, () -> new Deadline(31, 2, 2012));
    }

    @Test
    public void jsonConstructor_validDeadline_expectTrue() {
        String validDeadline = "2012-12-12" + Deadline.PROPERTY_SEPARATOR_PREFIX + DeadlineStatus.READY;
        assertTrue(Deadline.isValidDeadline(validDeadline));
    }

    @Test
    public void normalConstructor_validDeadline_validDateParameters() {
        Deadline validDeadline = new Deadline("2012-12-12"
                + Deadline.PROPERTY_SEPARATOR_PREFIX + DeadlineStatus.READY);
        assertEquals(validDeadline, new Deadline(12, 12, 2012));
    }

    @Test
    public void normalConstructor_validDeadline_validDeadlineStatus() {
        Deadline validDeadline = new Deadline("2012-12-12"
                + Deadline.PROPERTY_SEPARATOR_PREFIX + DeadlineStatus.READY);
        assertEquals(validDeadline, new Deadline(12, 12, 2012, DeadlineStatus.READY));
    }

    @Test
    public void normalConstructor_validDeadline_noParameter() {
        try {
            Deadline d = new Deadline();
            d = null;
        } catch (Exception e) {
            fail("No exception should be thrown");
        }
    }

}
