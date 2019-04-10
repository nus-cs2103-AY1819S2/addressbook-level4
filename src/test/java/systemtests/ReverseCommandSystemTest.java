package systemtests;

import static seedu.finance.testutil.TypicalRecords.AMY;
import static seedu.finance.testutil.TypicalRecords.KEYWORD_MATCHING_DONUT;

import org.junit.Test;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.ReverseCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.logic.parser.comparator.RecordAmountComparator;
import seedu.finance.logic.parser.comparator.RecordCategoryComparator;
import seedu.finance.logic.parser.comparator.RecordDateComparator;
import seedu.finance.logic.parser.comparator.RecordNameComparator;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordUtil;

public class ReverseCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void reverse() {
        final Model defaultModel = getModel();

        /* Case: Reverse list in finance tracker -> reversed */
        String command = ReverseCommand.COMMAND_WORD;
        String expectedResultMessage = ReverseCommand.MESSAGE_SUCCESS;
        Model expectedModel = getModel();
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: undo reversing of list -> original finance tracker restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);


        /* Case: redo reversing of list -> reversed */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: selects first card in record list and reverses list -> reversed */
        selectRecord(Index.fromOneBased(1));
        command = ReverseCommand.COMMAND_WORD;
        expectedResultMessage = ReverseCommand.MESSAGE_SUCCESS;
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        assertSelectedCardDeselected();


        /* Case: filters the record list before reversing -> reversed */
        showRecordsWithName(KEYWORD_MATCHING_DONUT);
        command = ReverseCommand.COMMAND_WORD;
        expectedResultMessage = ReverseCommand.MESSAGE_SUCCESS;
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);

        /* Case: Mixed case command word -> reversed */
        command = "rEvErsE";
        expectedResultMessage = ReverseCommand.MESSAGE_SUCCESS;
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: Execute reverse command with command alias: rev -> reversed */
        command = ReverseCommand.COMMAND_ALIAS;
        expectedResultMessage = ReverseCommand.MESSAGE_SUCCESS;
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: Add a record then reverse list -> reversed */
        expectedModel.addRecord(AMY);
        command = RecordUtil.getSpendCommand(AMY);
        executeCommand(command);
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_SUCCESS, expectedModel);


        /* Case: delete a record then reverse list -> reversed */
        executeCommand("delete 1");
        Record targetedRecord = expectedModel.getFilteredRecordList().get(0);
        expectedModel.deleteRecord(targetedRecord);
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_SUCCESS, expectedModel);


        /* Case: sorts the records by name (default ordering) then reverse list -> reversed */
        executeCommand("sort -name");
        expectedModel.sortFilteredRecordList(new RecordNameComparator());
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_SUCCESS, expectedModel);


        /* Case: sorts the records by amount then reverse list -> reversed */
        executeCommand("sort -amount");
        expectedModel.sortFilteredRecordList(new RecordAmountComparator());
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_SUCCESS, expectedModel);


        /* Case: sorts the records by date then reverse list -> reversed */
        executeCommand("sort -date");
        expectedModel.sortFilteredRecordList(new RecordDateComparator());
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_SUCCESS, expectedModel);


        /* Case: sorts the records by category then reverse list -> reversed */
        executeCommand("sort -cat");
        expectedModel.sortFilteredRecordList(new RecordCategoryComparator());
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_SUCCESS, expectedModel);


        /* Case: Clears the finance tracker then reverse list -> show list is empty message */
        deleteAllRecords();
        Model emptyModel = new ModelManager();
        assertCommandFailure(ReverseCommand.COMMAND_WORD, ReverseCommand.MESSAGE_EMPTY_LIST, emptyModel);


    }

    /**
     * Executes {@code Command} and verifies that the command box displays an empty string,
     * the result display box displays {@code expectedResultMessage}
     * and the model related components equal to {@code expectedModel}.
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, Model model) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, model);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
