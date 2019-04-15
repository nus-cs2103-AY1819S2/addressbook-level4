package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
    public void copy_oneTask_success() throws Exception {
        Task taskToCopy = expectedModel.getFilteredTaskList().get(0).copy();
        expectedModel.addTask(taskToCopy);
        String expectedMessage = String.format(TaskCopyCommand.MESSAGE_SUCCESS, taskToCopy);
        CommandResult commandResult = new TaskCopyCommand(INDEX_FIRST_PERSON, 1)
                .execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(model.getFilteredTaskList().toString(),
                expectedModel.getFilteredTaskList().toString());
    }

    @Test
    public void copy_twoTasks_success() throws Exception {
        Task taskToCopy1 = expectedModel.getFilteredTaskList().get(0).copy();
        Task taskToCopy2 = expectedModel.getFilteredTaskList().get(0).copy();
        expectedModel.addTask(taskToCopy1);
        expectedModel.addTask(taskToCopy2);
        String expectedMessage = String.format(TaskCopyCommand.MESSAGE_SUCCESS, taskToCopy1);
        CommandResult commandResult = new TaskCopyCommand(INDEX_FIRST_PERSON, 2)
                .execute(model, commandHistory);
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(model.getFilteredTaskList().toString(),
                expectedModel.getFilteredTaskList().toString());
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TaskCopyCommand taskCopyCommand = new TaskCopyCommand(outOfBoundIndex, 1);

        assertCommandFailure(taskCopyCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
