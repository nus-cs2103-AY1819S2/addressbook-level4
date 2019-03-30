package seedu.address.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class SlotTest {

    @Test
    public void equals() {
        LocalDate dateA = LocalDate.parse("2019-05-22");
        LocalDate dateB = LocalDate.parse("2019-05-23");
        LocalTime startA = LocalTime.parse("12:00");
        LocalTime startB = LocalTime.parse("13:00");
        LocalTime endA = LocalTime.parse("16:00");
        LocalTime endB = LocalTime.parse("17:00");

        Slot slotA = new Slot(dateA, startA, endA);

        // test equality of same referenced object
        Slot slotB = new Slot(dateA, startA, endA);
        Assert.assertEquals(slotA, slotB);

        // test equality with null
        Assert.assertFalse(slotA.equals(null));

        // test equality with different type
        Assert.assertNotEquals(slotA, dateA);

        // test equality of same referenced object with null end time
        slotB = new Slot(dateA, startA, null);
        Slot slotBCopy = new Slot(dateA, startA, null);
        Assert.assertEquals(slotB, slotBCopy);

        // test equality of two different slot objects with different date
        slotB = new Slot(dateB, startA, endA);
        Assert.assertNotEquals(slotA, slotB);

        // test equality of two different slot objects with different start time
        slotB = new Slot(dateA, startB, endA);
        Assert.assertNotEquals(slotA, slotB);

        // test equality of two different slot objects with different end time
        slotB = new Slot(dateA, startA, endB);
        Assert.assertNotEquals(slotA, slotB);

        // test equality of two different slot objects, one with null end time
        slotB = new Slot(dateA, startA, null);
        Assert.assertNotEquals(slotA, slotB);
    }
}
