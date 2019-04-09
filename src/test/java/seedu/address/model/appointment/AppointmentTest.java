package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null,
                null, null, null));
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
        Appointment appointment1 = new Appointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(VALID_START_TIME));
        Appointment appointment2 = new Appointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime("10:00"));
        Appointment appointment1Copy = new Appointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(VALID_START_TIME));

        assertTrue(appointment1.equals(appointment1));
        assertTrue(appointment1.equals(appointment1Copy));

        assertFalse(appointment1.equals(1));
        assertFalse(appointment1.equals(appointment2));
    }
}
