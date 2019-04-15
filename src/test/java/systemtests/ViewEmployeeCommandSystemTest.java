package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.ViewEmployeeCommand.MESSAGE_VIEW_EMPLOYEE_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalEmployees.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewEmployeeCommand;
import seedu.address.model.Model;

public class ViewEmployeeCommandSystemTest extends PocketProjectSystemTest {
    @Test
    public void view() {
        /* ------------------------ Perform view operations on the shown unfiltered list -------------------------- */

        /* Case: view the first card in the employee list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD + " "
                + INDEX_FIRST_EMPLOYEE.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_EMPLOYEE);

        /* Case: view the last card in the employee list -> selected */
        Index personCount = getLastIndex(getModel());
        command = ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD + " "
                + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: view the middle card in the employee list -> selected */
        Index middleIndex = getMidIndex(getModel());
        command = ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD + " "
                + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: view the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* ------------------------ Perform view operations on the shown filtered list ---------------------------- */

        /* Case: filtered employee list, view index within bounds of pocket project but out of bounds of employee list
         * -> rejected
         */
        showEmployeesWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getPocketProject().getEmployeeList().size();
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                + " " + invalidIndex, MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        /* Case: filtered employee list, view index within bounds of pocket project and employee list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assertTrue(validIndex.getZeroBased() < getModel().getFilteredEmployeeList().size());
        command = ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD + " "
                + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* ----------------------------------- Perform invalid view operations ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                        + " " + 0, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmployeeCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                        + " " + -1, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmployeeCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredEmployeeList().size() + 1;
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                + " " + invalidIndex, MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                        + " abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmployeeCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                        + " 1 abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmployeeCommand.MESSAGE_USAGE));
        /* Case: view from empty pocket project -> rejected */
        deleteAllEmployees();
        assertCommandFailure(ViewCommand.COMMAND_WORD + " " + ViewEmployeeCommand.VIEW_EMPLOYEE_KEYWORD
                        + " " + INDEX_FIRST_EMPLOYEE.getOneBased(), MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing view command with the
     * {@code expectedSelectedCardIndex} of the selected employee.<br>
     * 4. {@code Storage} and {@code EmployeeListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see PocketProjectSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see PocketProjectSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_VIEW_EMPLOYEE_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getEmployeeListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code EmployeeListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
