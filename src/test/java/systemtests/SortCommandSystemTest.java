package systemtests;

import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_ASCENDING;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DESCENDING;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;
import static seedu.finance.testutil.TypicalRecords.AMY;

import org.junit.Test;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.SortCommand;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.logic.parser.comparator.RecordAmountComparator;
import seedu.finance.logic.parser.comparator.RecordDateComparator;
import seedu.finance.logic.parser.comparator.RecordNameComparator;
import seedu.finance.model.Model;
import seedu.finance.testutil.RecordUtil;

public class SortCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void sort(){
        Model defaultModel = getModel();


        /* Case: Sort list by name in descending order -> sorted */
        Model expectedModel = getModel();
        String command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + COMMAND_FLAG_DESCENDING;
        String expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordNameComparator().reversed());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: Undo sorting -> original finance tracker restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);



        /* Case: Redo sorting -> sorted */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: sort list by name with default ordering (ascending -> sorted */
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordNameComparator());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: sort list by amount in ascending order -> sorted */
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_AMOUNT + " " + COMMAND_FLAG_ASCENDING;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordAmountComparator().reversed());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: sort list by amount with default ordering (descending) -> sorted */
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_AMOUNT;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordAmountComparator());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: add a record then sort list by date in ascending order */
        expectedModel.addRecord(AMY);
        command = RecordUtil.getSpendCommand(AMY);
        assertCommandSuccess(command, String.format(SpendCommand.MESSAGE_SUCCESS, AMY), expectedModel);
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_DATE + " " + COMMAND_FLAG_ASCENDING;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordDateComparator().reversed());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);



        /* Case: delete a record then sort list by date in default ordering (descending) -> sorted */
        


        /* Case: Mixed case command word -> sorted */

        /* Case: filters the record before sorting by category in descending order -> sorted */


        /* Case: Select first card then sort by category in default ordering (ascending) -> sorted */

        /* Case: reverse list then sort by name in ascending order -> sorted */


        /* Case: Clears the finance tracker then sort list -> show list is empty message */


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: Invalid flag -> rejected */

        /* Case: Invalid order -> rejected */

        /* Case: INvalid arguments (extra argument) -> rejected */

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

    /**
     * Executes {@code Command} and in addition, <br>
     * 1. Asserts that the command box displays {@code Command}. <br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}. <br>
     * 3. Asserts that selected card and status bar remains unchanged. <br>
     * 4. Asserts that the command box has the error style. <br>
     * Verifications 1 and 2 are performed by
     * {@code FinanceTrackerSystemTet#assertApplicationDisplaysExpected(String, String, Model)}. <br>
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
