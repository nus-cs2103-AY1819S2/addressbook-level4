package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModelStub;

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
    public void execute_lessonClosedByModel_closeSuccessful() throws CommandException {
        MgtModelStub modelStub = new MgtModelStub();
        CommandResult expected = new CommandResult(ReloadLessonsCommand.MESSAGE_SUCCESS,
            CommandResult.UpdateStorage.LOAD);
        ReloadLessonsCommand command = new ReloadLessonsCommand();

        assertEquals(expected, command.execute(modelStub, null));
    }

    @Test
    public void execute_modelWithOpenedLesson_closeUnsuccessful() throws CommandException {
        MgtModelStubWithOpenedLesson modelStub = new MgtModelStubWithOpenedLesson();

        // attempt to reload lessons but there is an opened lesson ->
        // ask user to close opened lesson first
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_LESSON_VIEW_COMMAND);
        new ReloadLessonsCommand().execute(modelStub, null);
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

    /**
     * A ManagementModel stub which always accept reload lessons.
     */
    private class MgtModelStub extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return false;
        }
    }

    /**
     * A ManagementModel stub which always rejects reload lessons.
     */
    private class MgtModelStubWithOpenedLesson extends ManagementModelStub {
        @Override
        public boolean isThereOpenedLesson() {
            return true;
        }
    }
}
