package seedu.equipment.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.logic.CommandHistory;

public class PutCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullWorkListID_nullSerialNumber_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new PutCommand(null, null);
    }

}
