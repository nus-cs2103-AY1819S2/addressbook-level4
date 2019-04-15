package seedu.pdf.model.pdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.pdf.logic.commands.CommandTestUtil.DATE_1_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.DATE_2_VALID;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_INVALIDSTATUS;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_INVALID_DATE;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_MISSINGSEPERATORPREFIX;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_INVALID_MISSINGSTATUS;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_JSON_NOT_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_STATUS_DONE;
import static seedu.pdf.logic.commands.CommandTestUtil.DEADLINE_STATUS_NOTDONE;
import static seedu.pdf.logic.commands.CommandTestUtil.MESSAGE_UNEXPECTEDEXCEPTION_VALIDINPUT;
import static seedu.pdf.logic.commands.CommandTestUtil.PROPERTY_SEPARATOR_PREFIX;
import static seedu.pdf.model.pdf.Deadline.STATUS_DONE_PREFIX;
import static seedu.pdf.model.pdf.Deadline.STATUS_NONE_PREFIX;
import static seedu.pdf.model.pdf.Deadline.STATUS_ONGOING_PREFIX;
import static seedu.pdf.model.pdf.Deadline.TOSTRING_HEADER_PREFIX;

import java.time.format.DateTimeParseException;
import java.util.MissingFormatArgumentException;

import org.junit.Test;

import seedu.pdf.testutil.Assert;

public class DeadlineTest {

    @Test
    public void jsonConstructor() {

        // missing status -> expected to throw MissingFormatArgumentException
        Assert.assertThrows(
                MissingFormatArgumentException.class, () -> new Deadline(DEADLINE_JSON_INVALID_MISSINGSTATUS));

        // invalid status -> expected to throw AssertionError
        Assert.assertThrows(
                AssertionError.class, () -> new Deadline(DEADLINE_JSON_INVALID_INVALIDSTATUS));

        // missing property seperator -> expected to throw DateTimeParseException
        Assert.assertThrows(
                MissingFormatArgumentException.class, () -> new Deadline(DEADLINE_JSON_INVALID_MISSINGSEPERATORPREFIX));

        // invalid date -> expected to throw dateTimeParseException
        Assert.assertThrows(
                DateTimeParseException.class, () -> new Deadline(DEADLINE_JSON_INVALID_INVALID_DATE));

        // valid date -> expected to Not throw any Exceptions
        try {
            new Deadline(DEADLINE_JSON_DONE);
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
        assertTrue(new Deadline(DEADLINE_JSON_DONE).equals(new Deadline(DEADLINE_JSON_DONE)));

        // different values -> expected false
        assertFalse(new Deadline(DEADLINE_JSON_DONE).equals(new Deadline(DEADLINE_JSON_NOT_DONE)));

        // same dates different status -> expected false
        assertFalse(new Deadline(DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_DONE)
                .equals(new Deadline(DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_NOTDONE)));

        // different date same status -> expected false
        assertFalse(new Deadline(DATE_2_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_DONE)
                .equals(new Deadline(DATE_1_VALID + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_DONE)));
    }

    @Test
    public void toStringTest() {
        // no deadline -> expected None prefix
        assertEquals(new StringBuilder(TOSTRING_HEADER_PREFIX).append(STATUS_NONE_PREFIX).toString(),
                new Deadline().toString());

        // existing deadline complete prefix
        assertEquals(new Deadline(DEADLINE_JSON_DONE).toString(), new StringBuilder(TOSTRING_HEADER_PREFIX)
                .append(String.format(DATE_2_VALID, "dd-mm-yyyy")).append(STATUS_DONE_PREFIX).toString());

        // existing deadline ongoing prefix
        assertEquals(new Deadline(DEADLINE_JSON_NOT_DONE).toString(), new StringBuilder(TOSTRING_HEADER_PREFIX)
                .append(String.format(DATE_1_VALID, "dd-mm-yyyy")).append(STATUS_ONGOING_PREFIX).toString());
    }

    @Test
    public void toJsonString() {

        // input to output conversion -> expect same result
        assertEquals(new Deadline(DEADLINE_JSON_NOT_DONE).toJsonString(), DEADLINE_JSON_NOT_DONE);

        // different date -> expected fail
        assertNotEquals(new Deadline(DEADLINE_JSON_NOT_DONE).toJsonString(), DATE_2_VALID
                + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_NOTDONE);

        // different status -> expected fail
        assertNotEquals(new Deadline(DEADLINE_JSON_NOT_DONE).toJsonString(), DATE_1_VALID
                + PROPERTY_SEPARATOR_PREFIX + DEADLINE_STATUS_DONE);
    }

    @Test
    public void exists() {
        // blank deadline -> expected false
        assertFalse(new Deadline().exists());

        //existing deadline value, status not done -> expected true
        assertTrue(new Deadline(DEADLINE_JSON_NOT_DONE).exists());

        //existing deadline value, status done -> expected true
        assertTrue(new Deadline(DEADLINE_JSON_DONE).exists());
    }

    @Test
    public void setDone() {
        // set done expected to work on valid deadline
        assertTrue(Deadline.setDone(new Deadline()).isDone());

        // set done expected to work on deadline that is not done
        assertTrue(Deadline.setDone(new Deadline(DEADLINE_JSON_NOT_DONE)).isDone());

        // set done expected to work on deadline that is already done
        assertTrue(Deadline.setDone(new Deadline(DEADLINE_JSON_DONE)).isDone());
    }

    @Test
    public void setRemove() {
        // set remove expected to work on empty deadline
        assertFalse(Deadline.setRemove(new Deadline()).exists());

        // set remove expected to work on deadline that is done
        assertFalse(Deadline.setRemove(new Deadline(DEADLINE_JSON_DONE)).exists());

        // set remove expected to work on deadline that is not done
        assertFalse(Deadline.setRemove(new Deadline(DEADLINE_JSON_NOT_DONE)).exists());
    }

}
