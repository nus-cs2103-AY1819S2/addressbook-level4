package seedu.address.model.appointment;

import static seedu.address.testutil.TypicalAppointments.APP_A;
import static seedu.address.testutil.TypicalAppointments.APP_B;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

public class AppointmentTest {
    @Test
    public void equals() {
        LocalDate date = APP_A.getDate();
        LocalTime start = APP_A.getStart();
        LocalTime end = APP_A.getEnd();
        String comment = APP_A.getComment();
        Appointment appB;

        // test equality of same object
        Assert.assertTrue(APP_A.equals(APP_A));

        // test equality of different appointments with same values
        appB = new Appointment(ALICE, date, start, end, comment);
        Assert.assertTrue(APP_A.equals(appB));

        // test equality with null
        Assert.assertFalse(APP_A.equals(null));

        // test equality of different types
        Assert.assertFalse(APP_A.equals(date));

        // test equality of different appointments
        Assert.assertFalse(APP_A.equals(APP_B));

        // test equality of two different appointment object with different patient
        appB = new Appointment(BOB, date, start, end, comment);
        Assert.assertFalse(APP_A.equals(appB));

        // test equality of two different appointment object with different date
        appB = new Appointment(ALICE, date.minusDays(1), start, end, comment);
        Assert.assertFalse(APP_A.equals(appB));

        // test equality of two different appointment object with different start time
        appB = new Appointment(ALICE, date, start.minusHours(1), end, comment);
        Assert.assertFalse(APP_A.equals(appB));

        // test equality of two different appointment object with different end time
        appB = new Appointment(ALICE, date, start, end.plusHours(1), comment);
        Assert.assertFalse(APP_A.equals(appB));
    }
}
