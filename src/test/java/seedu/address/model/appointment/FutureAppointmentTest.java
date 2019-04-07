package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

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
        LocalDateTime futureDateTime = currentDateTime.plusSeconds(1);
        FutureAppointment futureAppointment = new FutureAppointment(1, 1,
                futureDateTime.toLocalDate(),
                futureDateTime.toLocalTime());
        LocalDateTime futureAppointmentDateTime = LocalDateTime.of(futureAppointment.getDate(),
                futureAppointment.getTime());
        boolean isInFuture = futureAppointmentDateTime.isAfter(currentDateTime);
        assertTrue(futureAppointment.isFuture());
        assertTrue(isInFuture);
    }

    @Test
    public void appointmentIsInPast_throwsAppointmentNotInFutureException() {
        thrown.expect(AppointmentNotInFutureException.class);
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime pastDateTime = currentDateTime.minusSeconds(1);
        FutureAppointment futureAppointment = new FutureAppointment(1, 1,
                pastDateTime.toLocalDate(),
                pastDateTime.toLocalTime());
        LocalDateTime futureAppointmentDateTime = LocalDateTime.of(futureAppointment.getDate(),
                futureAppointment.getTime());
        boolean isInFuture = futureAppointmentDateTime.isAfter(currentDateTime);
        assertFalse(futureAppointment.isFuture());
        assertFalse(isInFuture);
    }

    @Test
    public void equals() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime1 = currentDateTime.plusSeconds(1);
        LocalDateTime futureDateTime2 = currentDateTime.plusDays(1).plusSeconds(1);

        FutureAppointment appointment1 = new FutureAppointment(1, 1,
                futureDateTime1.toLocalDate(), futureDateTime1.toLocalTime());
        FutureAppointment appointment2 = new FutureAppointment(1, 1,
                futureDateTime2.toLocalDate(), futureDateTime2.toLocalTime());
        FutureAppointment appointment1Copy = new FutureAppointment(1, 1,
                futureDateTime1.toLocalDate(), futureDateTime1.toLocalTime());

        assertTrue(appointment1.equals(appointment1));
        assertTrue(appointment1.equals(appointment1Copy));

        assertFalse(appointment1.equals(1));
        assertFalse(appointment1.equals(appointment2));
    }
}
