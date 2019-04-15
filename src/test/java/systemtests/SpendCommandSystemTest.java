package systemtests;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.AMOUNT_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static seedu.finance.logic.commands.CommandTestUtil.CATEGORY_DESC_HUSBAND;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.DATE_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_AMOUNT_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.finance.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.finance.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.finance.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.finance.testutil.TypicalRecords.AMY;
import static seedu.finance.testutil.TypicalRecords.BOB;
import static seedu.finance.testutil.TypicalRecords.CAP;
import static seedu.finance.testutil.TypicalRecords.HAMBURGER;
import static seedu.finance.testutil.TypicalRecords.IPHONE;
import static seedu.finance.testutil.TypicalRecords.KEYWORD_MATCHING_DONUT;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.finance.commons.core.Messages;
import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.SpendCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.model.Model;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;
import seedu.finance.testutil.RecordUtil;

public class SpendCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void spend() {
        Model model = getModel();


        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */



        /* Case: add a record to a non-empty finance tracker,
         * & command with leading spaces and trailing spaces
         * -> added
         */

        Record toSpend = AMY;
        String command = "   " + SpendCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "   " + AMOUNT_DESC_AMY + "   "
                + DATE_DESC_AMY + "   " + CATEGORY_DESC_FRIEND + "  " + DESCRIPTION_DESC_AMY + "   ";
        assertCommandSuccess(command, toSpend);



        /* Case: undo adding Amy to the list -> Amy deleted */

        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: redo adding Amy to the list -> Amy added again */

        command = RedoCommand.COMMAND_WORD;
        model.addRecord(toSpend);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: Add a record using command alias "add" -> added */

        toSpend = new RecordBuilder(BOB).withName("bread").build();
        command = SpendCommand.COMMAND_ALIAS + " n/bread" + AMOUNT_DESC_BOB
                + DATE_DESC_BOB + CATEGORY_DESC_HUSBAND + DESCRIPTION_DESC_BOB;
        assertCommandSuccess(command, toSpend);


        /* Case: add a record with all fields same as another record in the finance tracker except name -> added */

        toSpend = new RecordBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = SpendCommand.COMMAND_WORD + NAME_DESC_BOB + AMOUNT_DESC_AMY + DATE_DESC_AMY
                + CATEGORY_DESC_FRIEND + DESCRIPTION_DESC_AMY;
        assertCommandSuccess(command, toSpend);



        /* Case: add a record with all fields same as another record in the finance tracker except amount and date
         * -> added
         */

        toSpend = new RecordBuilder(AMY).withAmount("1.11").withDate("01/01/2001").build();
        command = RecordUtil.getSpendCommand(toSpend);
        assertCommandSuccess(command, toSpend);


        /* Case: add to empty finance tracker -> added */
        deleteAllRecords();
        assertCommandSuccessExceededBudget(AMY);



        /* Case: add a record, command with parameters in random order -> added */

        toSpend = BOB;
        command = SpendCommand.COMMAND_WORD + NAME_DESC_BOB + CATEGORY_DESC_HUSBAND
                + DATE_DESC_BOB + AMOUNT_DESC_BOB + DESCRIPTION_DESC_BOB;
        assertCommandSuccessExceededBudget(command, toSpend);



        /* Case: add a duplicate record -> added */
        assertCommandSuccessExceededBudget(HAMBURGER);
        assertCommandSuccessExceededBudget(HAMBURGER);



        /* Case: add a duplicate record except with different amount -> added */

        toSpend = new RecordBuilder(HAMBURGER).withAmount(VALID_AMOUNT_BOB).build();
        command = RecordUtil.getSpendCommand(toSpend);
        assertCommandSuccessExceededBudget(command, toSpend);


        /* Case: add a duplicate record except with different date -> added */

        toSpend = new RecordBuilder(HAMBURGER).withDate(VALID_DATE_BOB).build();
        command = RecordUtil.getSpendCommand(toSpend);
        assertCommandSuccessExceededBudget(command, toSpend);



        /* Case: add a duplicate record except with different category -> added */

        toSpend = new RecordBuilder(HAMBURGER).withCategory("Food").build();
        command = RecordUtil.getSpendCommand(toSpend);
        assertCommandSuccessExceededBudget(command, toSpend);



        /* Case: mixed case command word -> added */

        toSpend = new RecordBuilder(BOB).withName("mango").build();
        command = "spEnD n/mango" + AMOUNT_DESC_BOB + DATE_DESC_BOB + CATEGORY_DESC_HUSBAND + DESCRIPTION_DESC_BOB;
        assertCommandSuccessExceededBudget(command, toSpend);



        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */


        /* Case: filters the record list before adding -> added */

        showRecordsWithName(KEYWORD_MATCHING_DONUT);
        assertCommandSuccessExceededBudget(IPHONE);


        /* ------------------------ Perform add operation while a record card is selected --------------------------- */


        /* Case: selects first card in the record list, add a record -> added, card selection remains unchanged */

        selectRecord(Index.fromOneBased(1));
        assertCommandSuccessExceededBudget(CAP);


        /* ----------------------------------- Perform invalid add operations --------------------------------------- */


        /* Case: missing name -> rejected */

        command = SpendCommand.COMMAND_WORD + AMOUNT_DESC_AMY + DATE_DESC_AMY + CATEGORY_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE));


        /* Case: missing amount -> rejected */

        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + DATE_DESC_AMY + CATEGORY_DESC_FRIEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE));


        /* Case: missing category -> rejected */

        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + AMOUNT_DESC_AMY + DATE_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, SpendCommand.MESSAGE_USAGE));


        /* Case: invalid keyword -> rejected */

        command = "adds " + RecordUtil.getRecordDetails(toSpend);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);


        /* Case: invalid name -> rejected */

        command = SpendCommand.COMMAND_WORD + INVALID_NAME_DESC + AMOUNT_DESC_AMY + DATE_DESC_AMY
                + CATEGORY_DESC_FRIEND;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);


        /* Case: invalid amount -> rejected */

        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_AMOUNT_DESC + DATE_DESC_AMY
                + CATEGORY_DESC_FRIEND;
        assertCommandFailure(command, Amount.MESSAGE_CONSTRAINTS);


        /* Case: invalid date -> rejected */

        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + AMOUNT_DESC_AMY + INVALID_DATE_DESC
                + CATEGORY_DESC_FRIEND;
        assertCommandFailure(command, Date.MESSAGE_CONSTRAINTS);


        /* Case: Invalid date because date is a future date -> rejected */
        LocalDate date = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + AMOUNT_DESC_AMY + " "
                + PREFIX_DATE + date.format(formatter) + CATEGORY_DESC_FRIEND;
        assertCommandFailure(command, Date.MESSAGE_CONSTRAINTS);


        /* Case: invalid category -> rejected */

        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + AMOUNT_DESC_AMY + DATE_DESC_AMY + INVALID_CATEGORY_DESC;
        assertCommandFailure(command, Category.MESSAGE_CONSTRAINTS);

        /* Case: invalid description -> rejected */
        command = SpendCommand.COMMAND_WORD + NAME_DESC_AMY + AMOUNT_DESC_AMY + DATE_DESC_AMY + CATEGORY_DESC_FRIEND
                + INVALID_DESCRIPTION_DESC;
        assertCommandFailure(command, Description.MESSAGE_CONSTRAINTS);

    }


    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccessExceededBudget(Record toAdd) {
        assertCommandSuccessExceededBudget(RecordUtil.getSpendCommand(toAdd), toAdd);
    }



    /**
     * Performs the same verification as {@code assertCommandSuccess(Record)}. Executes {@code command}
     * instead.
     * Budget is exceeded here
     * @see SpendCommandSystemTest#assertCommandSuccessExceededBudget(Record)
     */

    private void assertCommandSuccessExceededBudget(String command, Record toAdd) {
        Model expectedModel = getModel();
        expectedModel.addRecord(toAdd);

        String expectedResultMessage = String.format(SpendCommand.MESSAGE_SUCCESS_EXCEED_BUDGET, toAdd.getCategory());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Record)}. Executes {@code command}
     * instead.
     *
     * @see SpendCommandSystemTest#assertCommandSuccessExceededBudget(Record)
     */

    private void assertCommandSuccess(String command, Record toAdd) {
        Model expectedModel = getModel();
        expectedModel.addRecord(toAdd);

        String expectedResultMessage = String.format(SpendCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }


    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Record)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code RecordListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see SpendCommandSystemTest#assertCommandSuccess(String, Record)
     */

    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }


    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RecordListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
