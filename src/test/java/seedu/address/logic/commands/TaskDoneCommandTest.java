package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Priority;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TaskDoneCommand}.
 */
public class TaskDoneCommandTest {

    private static final String MESSAGE_PATIENT_NOT_FOUND = "\n Linked Patient not found. Record not added.";
    private static final String MESSAGE_NO_LINKED_PATIENT = "\n Task not linked to any patients. No Records are added.";
    private static final String MESSAGE_RECORD_ADDED = "\n Added Record to Patient: %s ( %s )";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToDone = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(INDEX_FIRST_TASK);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Task completedTask = new Task(taskToDone, true);
        completedTask.setPriorityComplete();
        String expectedMessage = String.format(TaskDoneCommand.MESSAGE_TASK_DONE_SUCCESS + MESSAGE_NO_LINKED_PATIENT,
                completedTask);
        expectedModel.setTask(taskToDone, completedTask);
        expectedModel.commitAddressBook();
        assertCommandSuccess(taskDoneCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()).getPriority(),
                Priority.COMPLETED);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(outOfBoundIndex);

        assertCommandFailure(taskDoneCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDone = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(INDEX_FIRST_TASK);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Task completedTask = new Task(taskToDone, true);
        completedTask.setPriorityComplete();
        String expectedMessage = String.format(TaskDoneCommand.MESSAGE_TASK_DONE_SUCCESS + MESSAGE_NO_LINKED_PATIENT,
                completedTask);
        expectedModel.setTask(taskToDone, completedTask);
        expectedModel.commitAddressBook();
        showTaskAtIndex(expectedModel, INDEX_FIRST_TASK);
        assertCommandSuccess(taskDoneCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()).getPriority(),
                Priority.COMPLETED);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(outOfBoundIndex);

        assertCommandFailure(taskDoneCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Task taskToDone = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Task completedTask = new Task(taskToDone, true);
        completedTask.setPriorityComplete();
        expectedModel.setTask(taskToDone, completedTask);
        expectedModel.commitAddressBook();

        // done -> first task completed
        taskDoneCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered task list to show all tasks
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        //task should be set to previous priority
        assertEquals(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()).getPriority().getPriorityType(),
                "high");

        // redo -> same task completed again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        assertEquals(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()).getPriority().getPriorityType(),
                "completed");
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(taskDoneCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Checks that a completed task cannot be completed again
     */
    @Test
    public void execute_invalidTaskAlreadyCompleted_failure() {
        Task taskToDone = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        TaskDoneCommand taskDoneCommand = new TaskDoneCommand(INDEX_FIRST_TASK);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Task completedTask = new Task(taskToDone, true);
        completedTask.setPriorityComplete();
        String expectedMessage = String.format(TaskDoneCommand.MESSAGE_TASK_DONE_SUCCESS + MESSAGE_NO_LINKED_PATIENT,
                completedTask);
        expectedModel.setTask(taskToDone, completedTask);
        expectedModel.commitAddressBook();
        assertCommandSuccess(taskDoneCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()).getPriority(),
                Priority.COMPLETED);
        String expectedFailMessage = String.format(TaskDoneCommand.MESSAGE_TASK_ALREADY_COMPLETED);
        assertCommandFailure(taskDoneCommand, model, commandHistory, expectedFailMessage);
    }

    @Test
    public void equals() {
        TaskDoneCommand doneFirstCommand = new TaskDoneCommand(INDEX_FIRST_TASK);
        TaskDoneCommand doneSecondCommand = new TaskDoneCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        TaskDoneCommand doneFirstCommandCopy = new TaskDoneCommand(INDEX_FIRST_TASK);
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }

}
