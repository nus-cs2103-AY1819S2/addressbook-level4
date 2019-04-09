package systemtests;

import static seedu.equipment.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.equipment.logic.commands.DeleteWorkListCommand.MESSAGE_DELETE_WORKLIST_SUCCESS;
import static seedu.equipment.logic.commands.DeleteWorkListCommand.MESSAGE_INVALID_WORKLIST_DISPLAYED_INDEX;
import static seedu.equipment.testutil.TestUtil.getWorkList;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_WORKLIST;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.commands.AddWorkListCommand;
import seedu.equipment.logic.commands.DeleteWorkListCommand;
import seedu.equipment.logic.parser.CliSyntax;
import seedu.equipment.model.Model;
import seedu.equipment.model.WorkList;

public class DeleteWorkListCommandSystemTest extends EquipmentManagerSystemTest {

    private static final String MESSAGE_INVALID_DELETE_WORKLIST_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteWorkListCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        String addCommand = " " + AddWorkListCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_DATE + "09-09-2019 "
                + CliSyntax.PREFIX_ASSIGNEE + "Alice "
                + CliSyntax.PREFIX_ID + "2 ";

        executeCommand(addCommand);

        /* Case: delete the first equipment in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     "
                + DeleteWorkListCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_WORKLIST.getOneBased() + "       ";
        WorkList deletedWorkList = removeWorkList(expectedModel, INDEX_FIRST_WORKLIST);
        String expectedResultMessage = String.format(MESSAGE_DELETE_WORKLIST_SUCCESS, deletedWorkList.getId().value);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteWorkListCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_WORKLIST_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteWorkListCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_WORKLIST_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getEquipmentManager().getWorkListList().size() + 1);
        command = DeleteWorkListCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_WORKLIST_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteWorkListCommand.COMMAND_WORD
                + " abc", MESSAGE_INVALID_DELETE_WORKLIST_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteWorkListCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_WORKLIST_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE-W 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code WorkList} at the specified {@code index} in {@code model}'s equipment manager.
     * @return the removed WorkList
     */
    private WorkList removeWorkList(Model model, Index index) {
        WorkList targetWorkList = getWorkList(model, index);
        model.deleteWorkList(targetWorkList);
        return targetWorkList;
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteWorkListCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see EquipmentManagerSystemTest#assertSelectedCardChanged(Index)
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
     * {@code EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see EquipmentManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
