package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PATIENT_ID;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.appointment.FutureAppointment;


public class AddAppointmentCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusSeconds(1);
        FutureAppointment futureAppointment1 = new FutureAppointment(Integer.parseInt(VALID_PATIENT_ID),
                Integer.parseInt(VALID_DOCTOR_ID),
                futureDateTime.toLocalDate(),
                futureDateTime.toLocalTime());

        FutureAppointment futureAppointment2 = new FutureAppointment(Integer.parseInt(VALID_PATIENT_ID),
                Integer.parseInt(VALID_DOCTOR_ID),
                futureDateTime.plusDays(1).toLocalDate(),
                futureDateTime.plusDays(1).toLocalTime());

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
