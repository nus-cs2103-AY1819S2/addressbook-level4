package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.QuickDocs;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalReminders {

    public static final Reminder REM_A = new Reminder(
            "Appointment with Peter Tan",
            "Weekly Checkup",
            LocalDate.parse("2019-10-23"),
            LocalTime.parse("12:00"),
            LocalTime.parse("13:00")
    );

    public static final Reminder REM_B = new Reminder(
            "Refill Medicine B",
            "URGENT",
            LocalDate.parse("2019-10-24"),
            LocalTime.parse("13:00")
    );

    public static final Reminder REM_C = new Reminder(
            "Refill Medicine C",
            LocalDate.parse("2019-10-25"),
            LocalTime.parse("14:00")
    );

    public static final Reminder REM_D = new Reminder(
            "Check financial records",
            LocalDate.parse("2019-10-26"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00")
    );

    /**
     * Returns a {@code QuickDocs} with all the typical reminders.
     */
    public static QuickDocs getTypicalRemindersQuickDocs() {
        QuickDocs qd = new QuickDocs();

        for (Reminder reminders : getTypicalReminders()) {
            qd.getReminderManager().addReminder(reminders);
        }
        return qd;
    }

    public static List<Reminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(REM_A, REM_B, REM_C, REM_D));
    }
}
