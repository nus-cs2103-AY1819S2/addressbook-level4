package systemtests;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_AMOUNT;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_ASCENDING;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DESCENDING;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;
import static seedu.finance.testutil.TypicalRecords.AMY;
import static seedu.finance.testutil.TypicalRecords.KEYWORD_MATCHING_DONUT;

import org.junit.Test;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.DeleteCommand;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.ReverseCommand;
import seedu.finance.logic.commands.SortCommand;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.logic.parser.comparator.RecordAmountComparator;
import seedu.finance.logic.parser.comparator.RecordCategoryComparator;
import seedu.finance.logic.parser.comparator.RecordDateComparator;
import seedu.finance.logic.parser.comparator.RecordNameComparator;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordUtil;

public class SortCommandSystemTest extends FinanceTrackerSystemTest {

    private static final String MESSAGE_INVALID_SORT_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
    @Test
    public void sort() {
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
        Record targetedRecord = expectedModel.getFilteredRecordList().get(0);
        expectedModel.deleteRecord(targetedRecord);
        assertCommandSuccess("delete 1", String.format(DeleteCommand.MESSAGE_DELETE_RECORD_SUCCESS,
                targetedRecord), expectedModel);
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_DATE;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordDateComparator());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: Mixed case command word -> sorted */
        command = "soRt " + COMMAND_FLAG_NAME;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordNameComparator());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: filters the record before sorting by category in descending order -> sorted */
        showRecordsWithName(KEYWORD_MATCHING_DONUT);
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_CATEGORY + " " + COMMAND_FLAG_DESCENDING;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordCategoryComparator().reversed());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: Select first card then sort by category in default ordering (ascending) -> sorted */
        selectRecord(Index.fromOneBased(1));
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_CATEGORY;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordCategoryComparator());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);


        /* Case: reverse list then sort by name in ascending order -> sorted */
        command = ReverseCommand.COMMAND_WORD;
        expectedResultMessage = ReverseCommand.MESSAGE_SUCCESS;
        expectedModel.reverseFilteredRecordList();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME;
        expectedResultMessage = SortCommand.MESSAGE_SUCCESS;
        expectedModel.sortFilteredRecordList(new RecordNameComparator());
        assertCommandSuccess(command, expectedResultMessage, expectedModel);



        /* --------------------------------- Performing invalid delete operation ------------------------------------ */


        /* Case: Clears the finance tracker then sort list -> show list is empty message */
        deleteAllRecords();
        Model emptyModel = new ModelManager();
        assertCommandFailure(SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME,
                SortCommand.MESSAGE_EMPTY_LIST);


        /* Case: Missing arguments -> rejected */
        command = SortCommand.COMMAND_WORD;
        assertCommandFailure(command, MESSAGE_INVALID_SORT_COMMAND_FORMAT);


        /* Case: Invalid flag -> rejected */
        command = SortCommand.COMMAND_WORD + " 123";
        assertCommandFailure(command, MESSAGE_INVALID_SORT_COMMAND_FORMAT);


        /* Case: Invalid order -> rejected */
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "-inc";
        assertCommandFailure(command, MESSAGE_INVALID_SORT_COMMAND_FORMAT);


        /* Case: Invalid arguments (extra argument) -> rejected */
        command = SortCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + COMMAND_FLAG_ASCENDING
                + " " + COMMAND_FLAG_DATE;
        assertCommandFailure(command, MESSAGE_INVALID_SORT_COMMAND_FORMAT);

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
