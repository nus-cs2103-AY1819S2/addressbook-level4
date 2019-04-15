package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteEmployeeCommand}.
 */
public class DeleteEmployeeCommandTest {

    private Model model = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteEmployeeCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        ModelManager expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);
        expectedModel.commitPocketProject();

        assertCommandSuccess(deleteEmployeeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        assertCommandFailure(deleteEmployeeCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteEmployeeCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        Model expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);
        expectedModel.commitPocketProject();
        showNoEmployee(expectedModel);

        assertCommandSuccess(deleteEmployeeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Index outOfBoundIndex = INDEX_SECOND_EMPLOYEE;
        // ensures that outOfBoundIndex is still in bounds of pocket project list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPocketProject().getEmployeeList().size());

        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        assertCommandFailure(deleteEmployeeCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);
        Model expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
        expectedModel.deleteEmployee(employeeToDelete);
        expectedModel.commitPocketProject();

        // delete -> first employee deleted
        deleteEmployeeCommand.execute(model, commandHistory);

        // undo -> reverts pocket project back to previous state and filtered employee list to show all employees
        expectedModel.undoPocketProject();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first employee deleted again
        expectedModel.redoPocketProject();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(outOfBoundIndex);

        // execution failed -> pocket project state not added into model
        assertCommandFailure(deleteEmployeeCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        // single pocket project state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Employee} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted employee in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the employee object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEmployeeDeleted() throws Exception {
        DeleteEmployeeCommand deleteEmployeeCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);
        Model expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());

        showEmployeeAtIndex(model, INDEX_SECOND_EMPLOYEE);
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        expectedModel.deleteEmployee(employeeToDelete);
        expectedModel.commitPocketProject();

        // delete -> deletes second employee in unfiltered employee list / first employee in filtered employee list
        deleteEmployeeCommand.execute(model, commandHistory);

        expectedModel.undoPocketProject();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(employeeToDelete, model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased()));
        // redo -> deletes same second employee in unfiltered employee list
        expectedModel.redoPocketProject();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteEmployeeCommand deleteFirstCommand = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);
        DeleteEmployeeCommand deleteSecondCommand = new DeleteEmployeeCommand(INDEX_SECOND_EMPLOYEE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteEmployeeCommand deleteFirstCommandCopy = new DeleteEmployeeCommand(INDEX_FIRST_EMPLOYEE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEmployee(Model model) {
        model.updateFilteredEmployeeList(p -> false);

        assertTrue(model.getFilteredEmployeeList().isEmpty());
    }
}
