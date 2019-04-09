package systemtests;

import static seedu.finance.testutil.TypicalRecords.KEYWORD_MATCHING_DONUT;

import org.junit.Test;

import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.ReverseCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.model.Model;

public class ReverseCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void reverse(){
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
