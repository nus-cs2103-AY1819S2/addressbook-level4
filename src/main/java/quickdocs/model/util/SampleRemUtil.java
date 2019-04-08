package quickdocs.model.util;

import java.time.LocalDate;
import java.time.LocalTime;

import quickdocs.model.reminder.Reminder;

/**
 * Sample {@code Reminder} data to initialise model
 */
public class SampleRemUtil {
    public static Reminder[] getSampleReminders() {
        return new Reminder[] {
            new Reminder("Refill Medicine ABC", "URGENT", LocalDate.of(2019, 05, 22),
                    LocalTime.of(12, 00), null),
            new Reminder("Refill Medicine XYZ", null, LocalDate.of(2019, 6, 23),
                    LocalTime.of(13, 0), null),
            new Reminder("Update quickdocs v1.2", "Implement sidebar features", LocalDate.of(2019, 3, 10),
                    LocalTime.of(12, 0), LocalTime.of(23, 0))
        };
    }
}
