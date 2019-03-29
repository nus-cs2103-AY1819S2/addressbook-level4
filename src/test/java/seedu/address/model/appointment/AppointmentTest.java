package seedu.address.model.appointment;

import static seedu.address.testutil.TypicalAppointments.APP_A;
import static seedu.address.testutil.TypicalAppointments.APP_B;
import static seedu.address.testutil.TypicalPatients.BOB;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.model.patient.Patient;

public class AppointmentTest {
    private Patient patientA = APP_A.getPatient();
    private Patient patientB = BOB;
    private LocalDate dateA = APP_A.getDate();
    private LocalDate dateB = LocalDate.parse("2019-12-23");
    private LocalTime startA = APP_A.getStart();
    private LocalTime startB = LocalTime.parse("09:00");
    private LocalTime endA = APP_A.getEnd();
    private LocalTime endB = LocalTime.parse("18:00");
    private String comment = APP_A.getComment();

    @Test
    public void equals() {
        Appointment appB;

        // test equality of same object
        Assert.assertTrue(APP_A.equals(APP_A));

        // test equality of different appointments with same values
        appB = new Appointment(patientA, dateA, startA, endA, comment);
        Assert.assertTrue(APP_A.equals(appB));

        // test equality with null
        Assert.assertFalse(APP_A.equals(null));

        // test equality of different types
        Assert.assertFalse(APP_A.equals(dateA));

        // test equality of different appointments
        Assert.assertFalse(APP_A.equals(APP_B));

        // test equality of two different appointment object with different patient
        appB = new Appointment(patientB, dateB, startA, endA, comment);
        Assert.assertFalse(APP_A.equals(appB));

        // test equality of two different appointment object with different date
        appB = new Appointment(patientA, dateB, startA, endA, comment);
        Assert.assertFalse(APP_A.equals(appB));

        // test equality of two different appointment object with different start time
        appB = new Appointment(patientA, dateA, startB, endA, comment);
        Assert.assertFalse(APP_A.equals(appB));

        // test equality of two different appointment object with different end time
        appB = new Appointment(patientA, dateA, startA, endB, comment);
        Assert.assertFalse(APP_A.equals(appB));
    }
}
