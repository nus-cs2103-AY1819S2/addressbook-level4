package seedu.address.logic.commands;

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

import seedu.address.logic.CommandHistory;
import seedu.address.model.appointment.AppointmentDate;
import seedu.address.model.appointment.AppointmentDoctorId;
import seedu.address.model.appointment.AppointmentPatientId;
import seedu.address.model.appointment.AppointmentTime;
import seedu.address.model.appointment.FutureAppointment;


public class AddAppointmentCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusSeconds(100);
        FutureAppointment futureAppointment1 = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(VALID_START_TIME));;

        FutureAppointment futureAppointment2 = new FutureAppointment(
                new AppointmentPatientId(VALID_PATIENT_ID),
                new AppointmentDoctorId(VALID_DOCTOR_ID),
                new AppointmentDate(VALID_DATE_OF_APPT),
                new AppointmentTime(futureDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))));

        AddAppointmentCommand command1 = new AddAppointmentCommand(futureAppointment1);
        AddAppointmentCommand command2 = new AddAppointmentCommand(futureAppointment2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        AddAppointmentCommand command1Copy = new AddAppointmentCommand(futureAppointment1);
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different appointment -> returns false
        assertFalse(command1.equals(command2));
    }
}
