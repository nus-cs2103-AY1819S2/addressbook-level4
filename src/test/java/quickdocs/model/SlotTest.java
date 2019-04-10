package quickdocs.model;

import static quickdocs.testutil.TypicalSlots.SLOT_A;
import static quickdocs.testutil.TypicalSlots.SLOT_B;
import static quickdocs.testutil.TypicalSlots.SLOT_C;
import static quickdocs.testutil.TypicalSlots.SLOT_NULL_END;

import org.junit.Assert;
import org.junit.Test;

public class SlotTest {

    @Test
    public void compareTo() {
        int result;
        // SLOT_A date < SLOT_B date
        result = SLOT_A.compareTo(SLOT_B);
        Assert.assertEquals(result, -1);

        // SLOT_A date == SLOT_C date,
        // SLOT_A start time < SLOT_C start time
        result = SLOT_A.compareTo(SLOT_C);
        Assert.assertEquals(result, -1);

        // same date and start time
        Slot slotACopy = new Slot(SLOT_A.getDate(), SLOT_A.getStart(), SLOT_A.getEnd());
        result = SLOT_A.compareTo(slotACopy);
        Assert.assertEquals(result, 0);
    }

    @Test
    public void equals() {

        // test equality of same referenced object
        Assert.assertEquals(SLOT_A, SLOT_A);

        // test equality of object with same values
        Slot slotACopy = new Slot(SLOT_A.getDate(), SLOT_A.getStart(), SLOT_A.getEnd());
        Assert.assertEquals(SLOT_A, slotACopy);

        // test equality with null
        Assert.assertFalse(SLOT_A.equals(null));

        // test equality with different type
        Assert.assertNotEquals(SLOT_A, SLOT_A.getDate());

        // test equality of same referenced object with null end time
        Slot slotNullEndCopy = new Slot(SLOT_NULL_END.getDate(), SLOT_NULL_END.getStart(), null);
        Assert.assertEquals(SLOT_NULL_END, slotNullEndCopy);

        // test equality of two different slot objects with different date
        Slot slotDiffDate = new Slot(SLOT_A.getDate().minusDays(1), SLOT_A.getStart(), SLOT_A.getEnd());
        Assert.assertNotEquals(SLOT_A, slotDiffDate);

        // test equality of two different slot objects with different start time
        Slot slotDiffStart = new Slot(SLOT_A.getDate(), SLOT_A.getStart().minusMinutes(1), SLOT_A.getEnd());
        Assert.assertNotEquals(SLOT_A, slotDiffStart);

        // test equality of two different slot objects with different end time
        Slot slotDiffEnd = new Slot(SLOT_A.getDate(), SLOT_A.getStart(), SLOT_A.getEnd().minusMinutes(1));
        Assert.assertNotEquals(SLOT_A, slotDiffEnd);

        // test equality of two different slot objects, one with null end time
        Assert.assertNotEquals(SLOT_A, SLOT_NULL_END);
    }
}
