package quickdocs.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.testutil.TypicalSlots.SLOT_A;
import static quickdocs.testutil.TypicalSlots.SLOT_B;
import static quickdocs.testutil.TypicalSlots.SLOT_C;
import static quickdocs.testutil.TypicalSlots.SLOT_NULL_END;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

/**
 * Contains unit tests for {@code Slot}.
 */
public class SlotTest {

    @Test
    public void compareTo() {
        int result;
        // SLOT_A date < SLOT_B date
        result = SLOT_A.compareTo(SLOT_B);
        assertEquals(result, -1);

        // SLOT_A date == SLOT_C date,
        // SLOT_A start time < SLOT_C start time
        result = SLOT_A.compareTo(SLOT_C);
        assertEquals(result, -1);

        // same date and start time
        Slot slotACopy = new Slot(SLOT_A.getDate(), SLOT_A.getStart(), SLOT_A.getEnd());
        result = SLOT_A.compareTo(slotACopy);
        assertEquals(result, 0);
    }

    @Test
    public void equals() {
        LocalDate date = SLOT_A.getDate();
        LocalTime start = SLOT_A.getStart();
        LocalTime end = SLOT_A.getEnd();

        // test equality of same referenced object -> returns true
        assertEquals(SLOT_A, SLOT_A);

        // test equality of object with same values -> returns true
        Slot slotACopy = new Slot(date, start, end);
        assertEquals(SLOT_A, slotACopy);

        // test equality with null -> returns false
        assertNotEquals(SLOT_A, null);

        // test equality with different type -> returns false
        assertNotEquals(SLOT_A, date);

        // test equality of same referenced object with null end time -> returns true
        Slot slotNullEndCopy = new Slot(SLOT_NULL_END.getDate(), SLOT_NULL_END.getStart(), null);
        assertEquals(SLOT_NULL_END, slotNullEndCopy);

        // test equality of two different slot objects with different date -> returns false
        Slot slotB = new Slot(date.minusDays(1), start, end);
        assertNotEquals(SLOT_A, slotB);

        // test equality of two different slot objects with different start time -> returns false
        slotB = new Slot(date, start.minusMinutes(1), end);
        assertNotEquals(SLOT_A, slotB);

        // test equality of two different slot objects with different end time -> returns false
        slotB = new Slot(date, start, end.plusHours(1));
        assertNotEquals(SLOT_A, slotB);

        // test equality with null end time -> returns false
        assertNotEquals(SLOT_A, SLOT_NULL_END);
    }
}
