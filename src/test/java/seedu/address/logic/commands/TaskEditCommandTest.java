package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKWITHNOPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TASKWITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_WITHPATIENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalData.AMY;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.TaskEditCommand.EditTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for TaskEditCommand.
 */
public class TaskEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask.getTitle());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitAddressBook();

        assertCommandSuccess(taskEditCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withTitle(VALID_TITLE_WITHPATIENT)
                .withPriority(VALID_PRIORITY_WITHPATIENT).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_WITHPATIENT)
                .withPriority(VALID_PRIORITY_WITHPATIENT).build();
        TaskEditCommand taskEditCommand = new TaskEditCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask.getTitle());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);
        expectedModel.commitAddressBook();

        assertCommandSuccess(taskEditCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        Task editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask.getTitle());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(taskEditCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Task taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withTitle(VALID_TITLE_WITHPATIENT).build();
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_WITHPATIENT).build());

        String expectedMessage = String.format(TaskEditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask.getTitle());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        expectedModel.commitAddressBook();
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        assertCommandSuccess(taskEditCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        descriptor.setPatientIndex(INDEX_FIRST_PERSON);
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_SECOND_TASK, descriptor);

        assertCommandFailure(taskEditCommand, model, commandHistory, MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        model.addPerson(AMY);
        // edit task in filtered list into a duplicate in address book
        Task taskInList = model.getAddressBook().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(taskInList).build();
        descriptor.setPatientIndex(Index.fromZeroBased(model.getFilteredPersonList().size()));
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_FIRST_TASK, descriptor);

        assertCommandFailure(taskEditCommand, model, commandHistory, MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_WITHPATIENT).build();
        TaskEditCommand taskEditCommand = new TaskEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(taskEditCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        TaskEditCommand taskEditCommand = new TaskEditCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_WITHPATIENT).build());

        assertCommandFailure(taskEditCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_FIRST_TASK, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setTask(taskToEdit, editedTask);
        expectedModel.commitAddressBook();

        // edit -> first task edited
        taskEditCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered task list to show all tasks
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTitle(VALID_TITLE_WITHPATIENT).build();
        TaskEditCommand taskEditCommand = new TaskEditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(taskEditCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Task} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited task in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the task object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTaskEdited() throws Exception {
        Task editedTask = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        descriptor.setPatientIndex(INDEX_FIRST_PERSON);
        TaskEditCommand taskEditCommand = new TaskEditCommand(INDEX_FIRST_TASK, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showTaskAtIndex(model, INDEX_SECOND_TASK);
        Task taskToEdit = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        expectedModel.setTask(taskToEdit, editedTask);
        expectedModel.commitAddressBook();

        // edit -> edits second task in unfiltered task list / first task in filtered task list
        taskEditCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered task list to show all tasks
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()), taskToEdit);
        // redo -> edits same second task in unfiltered task list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final TaskEditCommand standardCommand = new TaskEditCommand(INDEX_FIRST_TASK, DESC_TASKWITHNOPATIENT);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_TASKWITHNOPATIENT);
        TaskEditCommand commandWithSameValues = new TaskEditCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new TaskEditCommand(INDEX_SECOND_TASK, DESC_TASKWITHNOPATIENT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new TaskEditCommand(INDEX_FIRST_TASK, DESC_TASKWITHPATIENT)));
    }

}
