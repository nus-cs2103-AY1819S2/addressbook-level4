package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.appointment.Appointment;


public class AddAppointmentCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        AddAppointmentCommand command1 = new AddAppointmentCommand(new Appointment(1, 1,
                LocalDate.parse("2019-06-01"), LocalTime.parse("09:00")));
        AddAppointmentCommand command2 = new AddAppointmentCommand(new Appointment(1, 1,
                LocalDate.parse("2019-06-01"), LocalTime.parse("10:00")));

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        AddAppointmentCommand command1Copy = new AddAppointmentCommand(new Appointment(1, 1,
                LocalDate.parse("2019-06-01"), LocalTime.parse("09:00")));
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different appointment -> returns false
        assertFalse(command1.equals(command2));
    }
}
