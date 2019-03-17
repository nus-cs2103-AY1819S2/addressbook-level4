package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.Assert;

public class JsonAdaptedReminderTest {
    private static final String INVALID_DATE = "2019-20-20";
    private static final String INVALID_START = "25:00";
    private static final String INVALID_END = "20:60";

    private static final String VALID_TITLE = "Refill Medicine ABC";
    private static final String VALID_DATE = "2019-10-23";
    private static final String VALID_START = "16:00";
    private static final String VALID_END = "17:00";
    private static final String VALID_COMMENT = "Urgent";

    private static final Reminder VALID_REMINDER = new Reminder(
            VALID_TITLE, VALID_COMMENT, LocalDate.parse(VALID_DATE),
            LocalTime.parse(VALID_START), LocalTime.parse(VALID_END));

    @Test
    public void toModelType_validReminderDetails_returnsReminder() throws Exception {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(VALID_REMINDER);
        assertEquals(VALID_REMINDER, reminder.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalArgumentException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(
                VALID_TITLE, VALID_COMMENT, INVALID_DATE, VALID_START, VALID_END);
        String expectedMessage = "Date format: YYYY-MM-DD";
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_invalidStart_throwsIllegalArgumentException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(
                VALID_TITLE, VALID_COMMENT, VALID_DATE, INVALID_START, VALID_END);
        String expectedMessage = "Time format: HH:MM";
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, reminder::toModelType);
    }

    @Test
    public void toModelType_invalidEnd_throwsIllegalArgumentException() {
        JsonAdaptedReminder reminder = new JsonAdaptedReminder(
                VALID_TITLE, VALID_COMMENT, VALID_DATE, VALID_START, INVALID_END);
        String expectedMessage = "Time format: HH:MM";
        Assert.assertThrows(IllegalArgumentException.class, expectedMessage, reminder::toModelType);
    }
}
