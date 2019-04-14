package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

public class TaskCopyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void copy_oneTask_success() {
        Task taskToCopy = expectedModel.getFilteredTaskList().get(0).copy();
        expectedModel.addTask(taskToCopy);
        String expectedMessage = String.format(TaskCopyCommand.MESSAGE_SUCCESS, taskToCopy);
        assertCommandSuccess(new TaskCopyCommand(INDEX_FIRST_PERSON, 1),
                model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void copy_twoTasks_success() {
        Task taskToCopy1 = expectedModel.getFilteredTaskList().get(0).copy();
        Task taskToCopy2 = expectedModel.getFilteredTaskList().get(0).copy();
        expectedModel.addTask(taskToCopy1);
        expectedModel.addTask(taskToCopy2);
        String expectedMessage = String.format(TaskCopyCommand.MESSAGE_SUCCESS, taskToCopy1);
        assertCommandSuccess(new TaskCopyCommand(INDEX_FIRST_PERSON, 2),
                model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TaskCopyCommand taskCopyCommand = new TaskCopyCommand(outOfBoundIndex, 1);

        assertCommandFailure(taskCopyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
