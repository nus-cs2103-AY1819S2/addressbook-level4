package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandResult;

/**
 * Unit tests for the {@link ReloadLessonsCommand}.
 */
public class ReloadLessonsCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Basic test that the command comes out as expected. Implementation is tested in LogicManager, as it is actually
     * implemented in LogicManager.
     */
    @Test
    public void execute_lessonClosedByModel_closeSuccessful() {
        CommandResult expected = new CommandResult(ReloadLessonsCommand.MESSAGE_SUCCESS,
            CommandResult.UpdateStorage.LOAD);
        ReloadLessonsCommand command = new ReloadLessonsCommand();

        assertEquals(expected, command.execute(null, null));
    }

    @Test
    public void equals() {
        ReloadLessonsCommand reloadLessonsCommand = new ReloadLessonsCommand();

        // same object -> returns true
        assertEquals(reloadLessonsCommand, reloadLessonsCommand);

        // all ReloadLessonsCommand objects are the same -> returns true
        ReloadLessonsCommand reloadLessonsCommand2 = new ReloadLessonsCommand();
        assertEquals(reloadLessonsCommand, reloadLessonsCommand2);

        // different types -> returns false
        assertNotEquals(reloadLessonsCommand, 1);

        // null -> returns false
        assertNotEquals(reloadLessonsCommand, null);
    }
}
