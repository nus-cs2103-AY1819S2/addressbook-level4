package seedu.hms.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.logic.CommandHistory;

public class AddBookingCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddBookingCommand(null);
    }
}
