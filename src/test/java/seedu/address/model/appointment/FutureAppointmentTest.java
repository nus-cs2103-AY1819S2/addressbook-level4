package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_APPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.exceptions.AppointmentNotInFutureException;

public class FutureAppointmentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void appointmentIsInFuture() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusDays(1).withHour(9).withMinute(0);
        FutureAppointment futureAppointment = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(futureDateTime.toLocalDate().toString()),
                new AppointmentTime(futureDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        LocalDateTime futureAppointmentDateTime = LocalDateTime.of(futureAppointment.getDate().date,
                futureAppointment.getTime().time);
        boolean isInFuture = futureAppointmentDateTime.isAfter(currentDateTime);
        assertTrue(futureAppointment.isFuture());
        assertTrue(isInFuture);
    }

    @Test
    public void appointmentIsInPast_throwsAppointmentNotInFutureException() {
        thrown.expect(AppointmentNotInFutureException.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime pastDateTime = currentDateTime.minusDays(1).withHour(9).withMinute(0);
        FutureAppointment futureAppointment = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(pastDateTime.toLocalDate().toString()),
                new AppointmentTime(pastDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))));
        LocalDateTime futureAppointmentDateTime = LocalDateTime.of(futureAppointment.getDate().date,
                futureAppointment.getTime().time);
        boolean isInFuture = futureAppointmentDateTime.isAfter(currentDateTime);
        assertFalse(futureAppointment.isFuture());
        assertFalse(isInFuture);
    }

    @Test
    public void equals() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime1 = currentDateTime.plusDays(1).withHour(10).withMinute(0);

        FutureAppointment appointment1 = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(VALID_START_TIME));
        FutureAppointment appointment2 = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(futureDateTime1.toLocalDate().toString()),
                new AppointmentTime(futureDateTime1.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))));;
        FutureAppointment appointment1Copy = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(VALID_START_TIME));;
        assertTrue(appointment1.equals(appointment1));
        assertTrue(appointment1.equals(appointment1Copy));

        assertFalse(appointment1.equals(1));
        assertFalse(appointment1.equals(appointment2));
    }
}
