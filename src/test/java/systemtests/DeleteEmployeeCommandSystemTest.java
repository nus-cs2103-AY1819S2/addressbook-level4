package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.DeleteEmployeeCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS;
import static seedu.address.testutil.TestUtil.getEmployee;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalEmployees.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEmployeeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

public class DeleteEmployeeCommandSystemTest extends PocketProjectSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteEmployeeCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first employee in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteEmployeeCommand.COMMAND_WORD + "      " + " employee "
                                + INDEX_FIRST_EMPLOYEE.getOneBased() + "       ";
        Employee deletedEmployee = removeEmployee(expectedModel, INDEX_FIRST_EMPLOYEE);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, deletedEmployee);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last employee in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastEmployeeIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastEmployeeIndex);

        /* Case: undo deleting the last employee in the list -> last employee restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last employee in the list -> last employee deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeEmployee(modelBeforeDeletingLast, lastEmployeeIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle employee in the list -> deleted */
        Index middleEmployeeIndex = getMidIndex(getModel());
        assertCommandSuccess(middleEmployeeIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered employee list, delete index within bounds of pocket project and employee list -> deleted */
        showEmployeesWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_EMPLOYEE;
        assertTrue(index.getZeroBased() < getModel().getFilteredEmployeeList().size());
        assertCommandSuccess(index);

        /* Case: filtered employee list, delete index within bounds of pocket project but out of bounds of employee list
         * -> rejected
         */
        showEmployeesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getPocketProject().getEmployeeList().size();
        command = DeleteEmployeeCommand.COMMAND_WORD + " employee " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a employee card is selected ---------------------- */

        /* Case: delete the selected employee -> employee list panel selects the employee before the deleted employee */
        showAllEmployees();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        viewEmployee(selectedIndex);
        command = DeleteEmployeeCommand.COMMAND_WORD + " employee " + selectedIndex.getOneBased();
        deletedEmployee = removeEmployee(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, deletedEmployee);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteEmployeeCommand.COMMAND_WORD + " employee 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteEmployeeCommand.COMMAND_WORD + " employee -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getPocketProject().getEmployeeList().size() + 1);
        command = DeleteEmployeeCommand.COMMAND_WORD + " employee " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteEmployeeCommand.COMMAND_WORD
                + " employee abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteEmployeeCommand.COMMAND_WORD
                + " employee 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

    }

    /**
     * Removes the {@code Employee} at the specified {@code index} in {@code model}'s pocket project.
     * @return the removed employee
     */
    private Employee removeEmployee(Model model, Index index) {
        Employee targetEmployee = getEmployee(model, index);
        model.deleteEmployee(targetEmployee);
        return targetEmployee;
    }

    /**
     * Deletes the employee at {@code toDelete} by
     * creating a default {@code DeleteEmployeeCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteEmployeeCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Employee deletedEmployee = removeEmployee(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_EMPLOYEE_SUCCESS, deletedEmployee);

        assertCommandSuccess(
                DeleteEmployeeCommand.COMMAND_WORD + " employee " + toDelete.getOneBased(),
                                expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteEmployeeCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see PocketProjectSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
