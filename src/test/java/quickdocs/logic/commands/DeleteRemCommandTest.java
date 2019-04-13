package quickdocs.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import static quickdocs.logic.commands.CommandTestUtil.assertCommandFailure;
import static quickdocs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static quickdocs.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static quickdocs.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;
import static quickdocs.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import org.junit.Test;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;
import quickdocs.model.reminder.Reminder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteRemCommand}.
 */
public class DeleteRemCommandTest {

    private QuickDocs quickDocs = getTypicalRemindersQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexFilteredList_success() {
        Reminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_REMINDER.getZeroBased());
        DeleteRemCommand deleteRemCommand = new DeleteRemCommand(INDEX_FIRST_REMINDER);

        String expectedMessage = String.format(DeleteRemCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(getTypicalRemindersQuickDocs(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteRemCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        DeleteRemCommand deleteRemCommand = new DeleteRemCommand(outOfBoundIndex);

        assertCommandFailure(deleteRemCommand, model, commandHistory, DeleteRemCommand.MESSAGE_INVALID_REMINDER_INDEX);
    }

    @Test
    public void equals() {
        DeleteRemCommand deleteFirstCommand = new DeleteRemCommand(INDEX_FIRST_REMINDER);
        DeleteRemCommand deleteSecondCommand = new DeleteRemCommand(INDEX_SECOND_REMINDER);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteRemCommand deleteFirstCommandCopy = new DeleteRemCommand(INDEX_FIRST_REMINDER);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(deleteFirstCommand, 1);

        // null -> returns false
        assertNotEquals(deleteFirstCommand, null);

        // different index -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }
}
