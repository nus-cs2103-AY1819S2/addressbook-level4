package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.ClearTableCommand;
import seedu.address.model.Model;

public class ClearTableCommandSystemTest extends RestOrRantSystemTest {

    @Test
    public void clear() {
        Model model = getModel();

        /* Case: clear non-empty restaurant, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        clearOccupancy();
        assertCommandSuccess("   " + ClearTableCommand.COMMAND_WORD + " ab12   ");

        /* Case: clear empty restaurant -> rejected */
        assertCommandFailure(ClearTableCommand.COMMAND_WORD, ClearTableCommand.MESSAGE_FAILURE);

        /* Case: clear occupied restaurant -> rejected */
        occupyTables();
        assertCommandFailure(ClearTableCommand.COMMAND_WORD, ClearTableCommand.INVALID_RESTAURANT_STATE);

        /* Case: mixed case command word -> rejected */
        clearOccupancy();
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearTableCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearTableCommand.MESSAGE_SUCCESS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearTableCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        expectedModel.setTables(new ArrayList<>());
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see RestOrRantSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
