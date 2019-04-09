package systemtests;

import org.junit.Test;

import seedu.finance.model.Model;

public class ReverseCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void reverse(){
        final Model defaultModel = getModel();

        /* Case: Reverse list in finance tracker -> reversed */

        /* Case: undo reversing of list -> original finance tracker restored */

        /* Case: redo reversing of list -> reversed */

        /* Case: selects first card in record list and reverses list -> reversed */

        /* Case: filters the record list before reversing -> reversed */

        /* Mixed case command word -> reversed */

        /* Execute reverse command with command alias: rev -> reversed */

    }

    /**
     * Executes {@code Command} and verifies that the command box displays an empty string,
     * the result display box displays {@code expectedResultMessage}
     * and the model related components equal to {@code expectedModel}.
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @param command
     * @param expectedResultMessage
     * @param expectedModel
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

}
