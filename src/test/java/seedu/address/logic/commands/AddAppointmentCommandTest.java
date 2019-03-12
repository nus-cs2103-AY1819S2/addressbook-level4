package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;


public class AddAppointmentCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void testThrowException() throws CommandException {
        thrown.expect(CommandException.class);
        new AddAppointmentCommand(Index.fromOneBased(1), new Appointment("test")).execute(null, commandHistory);
    }

    @Test
    public void equals() {
        AddAppointmentCommand command1 = new AddAppointmentCommand(Index.fromOneBased(1),
                new Appointment("appointment1"));
        AddAppointmentCommand command2 = new AddAppointmentCommand(Index.fromOneBased(2),
                new Appointment("appointment2"));

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        AddAppointmentCommand command1Copy = new AddAppointmentCommand(Index.fromOneBased(1),
                new Appointment("appointment1"));
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different person -> returns false
        assertFalse(command1.equals(command2));
    }
}
