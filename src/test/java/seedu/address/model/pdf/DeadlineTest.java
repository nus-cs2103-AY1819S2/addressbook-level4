package seedu.address.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.DATE_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DATE_2_VALID;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_COMPLETE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_INVALIDDATE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_INVALIDSTATUS;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_MISSINGSEPERATORPREFIX;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_MISSINGSTATUS;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_JSON_READY;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_STATUS_COMPLETE;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_STATUS_READY;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT;
import static seedu.address.logic.commands.CommandTestUtil.PROPERTY_SEPARATOR_PREFIX;

import java.time.format.DateTimeParseException;
import java.util.MissingFormatArgumentException;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeadlineTest {

    @Test
    public void jsonConstructor() {
        // null value -> expected to throw NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> new Deadline(null));

        // missing status -> expected to throw MissingFormatArgumentException
        Assert.assertThrows(
                MissingFormatArgumentException.class, () -> new Deadline(DEADLINE_JSON_INVALID_MISSINGSTATUS));

        // invalid status -> expected to throw AssertionError
        Assert.assertThrows(
                AssertionError.class, () -> new Deadline(DEADLINE_JSON_INVALID_INVALIDSTATUS));

        // missing property seperator -> expected to throw DateTimeParseException
        Assert.assertThrows(
                DateTimeParseException.class, () -> new Deadline(DEADLINE_JSON_INVALID_MISSINGSEPERATORPREFIX));

        // invalid date -> expected to throw dateTimeParseException
        Assert.assertThrows(
                DateTimeParseException.class, () -> new Deadline(DEADLINE_JSON_INVALID_INVALIDDATE));

        // valid date -> expected to Not throw any Exceptions
        try {
            new Deadline(DEADLINE_JSON_COMPLETE);
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }
    }

    @Test
    public void normalConstructor() {
        // invalid date -> expected to throw DateTimeParseException
        Assert.assertThrows(DateTimeParseException.class, () -> new Deadline(31, 2, 2012));

        // valid date -> expected to Not throw any Exceptions
        try {
            new Deadline(Integer.parseInt(DATE_1_VALID.split("-")[2]),
                    Integer.parseInt(DATE_1_VALID.split("-")[1]),
                    Integer.parseInt(DATE_1_VALID.split("-")[0]));
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }

        // valid deadline with no parameters -> expected to Not throw any Exceptions
        try {
            new Deadline();
        } catch (Exception e) {
            fail(MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT);
        }
    }

    @Test
    public void equals() {
        // same deadline value, different objects -> expect true
        assertTrue(new Deadline(DEADLINE_JSON_COMPLETE).equals(new Deadline(DEADLINE_JSON_COMPLETE)));

        // different values -> expected false
        assertFalse(new Deadline(DEADLINE_JSON_COMPLETE).equals(new Deadline(DEADLINE_JSON_READY)));

        // same dates different status -> expected false
        assertFalse(new Deadline(DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_COMPLETE)
                .equals(new Deadline(DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_READY)));

        // different date same status -> expected false
        assertFalse(new Deadline(DATE_2_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_READY)
                .equals(new Deadline(DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_READY)));
    }

    @Test
    public void toStringTest() {
        // no deadline -> expected blank
        assertEquals("", new Deadline().toString());
    }
}
