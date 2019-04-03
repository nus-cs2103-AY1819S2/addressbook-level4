package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(1,
                1, null, null));
    }

    /*@Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAppointment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }*/

    @Test
    public void isValidAppointment() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Appointment.isValidAppointment(null));

        // invalid addresses
        assertFalse(Appointment.isValidAppointment("")); // empty string
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only

        // valid addresses
        assertTrue(Appointment.isValidAppointment("appointment1"));
    }

    @Test
    public void equals() {
        Appointment appointment1 = new Appointment(1, 1,
                LocalDate.parse("2019-06-01"), LocalTime.parse("09:00"));
        Appointment appointment2 = new Appointment(1, 1,
                LocalDate.parse("2019-06-01"), LocalTime.parse("10:00"));
        Appointment appointment1Copy = new Appointment(1, 1,
                LocalDate.parse("2019-06-01"), LocalTime.parse("09:00"));

        assertTrue(appointment1.equals(appointment1));
        assertTrue(appointment1.equals(appointment1Copy));

        assertFalse(appointment1.equals(1));
        assertFalse(appointment1.equals(appointment2));
    }
}
