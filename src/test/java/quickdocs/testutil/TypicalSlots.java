package quickdocs.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import quickdocs.model.Slot;

/**
 * A utility class containing a list of {@code Slot} objects to be used in tests.
 */
public class TypicalSlots {
    public static final Slot SLOT_A = new Slot(
            LocalDate.parse("2019-05-22"),
            LocalTime.parse("12:00"),
            LocalTime.parse("16:00")
    );

    public static final Slot SLOT_B = new Slot(
            LocalDate.parse("2019-05-23"),
            LocalTime.parse("13:00"),
            LocalTime.parse("17:00")
    );

    public static final Slot SLOT_C = new Slot(
            LocalDate.parse("2019-05-23"),
            LocalTime.parse("14:00"),
            LocalTime.parse("15:00")
    );

    public static final Slot SLOT_NULL_END = new Slot(
            LocalDate.parse("2019-10-26"),
            LocalTime.parse("09:00"),
            null
    );
}
