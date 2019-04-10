package systemtests;

import org.junit.Test;
import seedu.finance.model.Model;

public class SortCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void sort(){
        Model defaultModel = getModel();

        /* Case: Sort list by name in descending order -> sorted */


        /* Case: Undo sorting -> original finance tracker restored */

        /* Case: Redo sorting -> sorted */

        /* Case: sort list by name with default ordering (ascending -> sorted */


        /* Case: sort list by amount in ascending order -> sorted */

        /* Case: sort list by amount with default ordering (descending) -> sorted */


        /* Case: add a record then sort list by date in ascending order */

        /* Case: delete a record then sort list by date in default ordering (descending) -> sorted */


        /* Case: Mixed case command word -> sorted */

        /* Case: filters the record before sorting by category in descending order -> sorted */


        /* Case: Select first card then sort by category in default ordering (ascending) -> sorted */

        /* Case: reverse list then sort by name in ascending order -> sorted */


        /* Case: Clears the finance tracker then sort list -> show list is empty message */
    }

    /**
     * Executes {@code Command} and vertifies that the command box displays an empty string,
     * the result display box displays {@code expectedResultMessage}
     * and the model related components equals to {@code expectedModel}.
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
