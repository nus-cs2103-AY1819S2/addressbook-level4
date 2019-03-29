package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.format.DateTimeParseException;
import java.util.MissingFormatArgumentException;

import org.junit.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.testutil.Assert;

public class DeadlineTest {

    @Test
    public void constructor_nullValue_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void jsonConstructor_missingStatus_throwsDateTimeParseException() {
        Assert.assertThrows(
                MissingFormatArgumentException.class, () -> new Deadline(
                        CommandTestUtil.DEADLINE_JSON_INVALID_MISSINGSTATUS));
    }

    @Test
    public void jsonConstructor_invalidStatus_throwsAssertionError() {
        Assert.assertThrows(
                AssertionError.class, () -> new Deadline(CommandTestUtil.DEADLINE_JSON_INVALID_INVALIDSTATUS));
    }

    @Test
    public void jsonConstructor_missingPropertySeperator_throwsDateTimeParseException() {
        Assert.assertThrows(
                DateTimeParseException.class, () -> new Deadline(
                        CommandTestUtil.DEADLINE_JSON_INVALID_MISSINGSEPERATORPREFIX));
    }

    @Test
    public void jsonConstructor_invalidDate_throwsDateTimeParseException() {
        Assert.assertThrows(
                DateTimeParseException.class, () -> new Deadline(CommandTestUtil.DEADLINE_JSON_INVALID_INVALIDDATE));
    }

    @Test
    public void normalConstructor_invalidDate_throwsDateTimeParseException() {
        Assert.assertThrows(DateTimeParseException.class, () -> new Deadline(31, 2, 2012));
    }

    @Test
    public void jsonConstructor_validDeadline_expectTrue() {
        try {
            new Deadline(CommandTestUtil.DEADLINE_JSON_COMPLETE);
        } catch (Exception e) {
            fail(CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }
    }

    @Test
    public void normalConstructor_validDeadline_validDateParameters() {
        Deadline validDeadline = new Deadline(CommandTestUtil.DATE_1_VALID
                + Deadline.PROPERTY_SEPARATOR_PREFIX + CommandTestUtil.DEADLINE_STATUS_READY);
        assertEquals(validDeadline, new Deadline(Integer.parseInt(CommandTestUtil.DATE_1_VALID.split("-")[2]),
                Integer.parseInt(CommandTestUtil.DATE_1_VALID.split("-")[1]),
                Integer.parseInt(CommandTestUtil.DATE_1_VALID.split("-")[0])));
    }

    @Test
    public void normalConstructor_validDeadline_validDeadlineStatus() {
        Deadline validDeadline = new Deadline(CommandTestUtil.DATE_1_VALID
                + Deadline.PROPERTY_SEPARATOR_PREFIX + CommandTestUtil.DEADLINE_STATUS_COMPLETE);
        assertEquals(validDeadline, Deadline.setDone(
                new Deadline(Integer.parseInt(CommandTestUtil.DATE_1_VALID.split("-")[2]),
                Integer.parseInt(CommandTestUtil.DATE_1_VALID.split("-")[1]),
                Integer.parseInt(CommandTestUtil.DATE_1_VALID.split("-")[0]))));
    }

    @Test
    public void equals_sameDeadline_expectedTrue() {
        assertTrue(new Deadline(CommandTestUtil.DEADLINE_JSON_COMPLETE).equals(
                new Deadline(CommandTestUtil.DEADLINE_JSON_COMPLETE)));
    }

    @Test
    public void equals_differentDeadline_expectedFalse() {
        assertFalse(new Deadline(CommandTestUtil.DEADLINE_JSON_COMPLETE).equals(
                new Deadline(CommandTestUtil.DEADLINE_JSON_READY)));
    }

    @Test
    public void equals_sameDateDifferentStatus_expectedFalse() {
        Deadline d1 = new Deadline(CommandTestUtil.DATE_1_VALID
                + CommandTestUtil.PROPERTY_SEPARATOR_PREFIX + CommandTestUtil.DEADLINE_STATUS_COMPLETE);
        Deadline d2 = new Deadline(CommandTestUtil.DATE_1_VALID
                + CommandTestUtil.PROPERTY_SEPARATOR_PREFIX + CommandTestUtil.DEADLINE_STATUS_READY);
        assertFalse(d1.equals(d2));
    }

    @Test
    public void equals_differentDateSameStatus_expectedFalse() {
        Deadline d1 = new Deadline(CommandTestUtil.DATE_2_VALID
                + CommandTestUtil.PROPERTY_SEPARATOR_PREFIX + CommandTestUtil.DEADLINE_STATUS_READY);
        Deadline d2 = new Deadline(CommandTestUtil.DATE_1_VALID
                + CommandTestUtil.PROPERTY_SEPARATOR_PREFIX + CommandTestUtil.DEADLINE_STATUS_READY);
        assertFalse(d1.equals(d2));
    }

    @Test
    public void toString_noDeadline_expectedBlank() {
        assertEquals("", new Deadline().toString());
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
