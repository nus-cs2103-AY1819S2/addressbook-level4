package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.finance.commons.core.Messages.MESSAGE_RECORDS_LISTED_OVERVIEW;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_CATEGORY;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_DATE;
import static seedu.finance.logic.parser.CliSyntax.COMMAND_FLAG_NAME;
import static seedu.finance.testutil.TypicalRecords.BANANA;
import static seedu.finance.testutil.TypicalRecords.CAP;
import static seedu.finance.testutil.TypicalRecords.DONUT;
import static seedu.finance.testutil.TypicalRecords.FRUITS;
import static seedu.finance.testutil.TypicalRecords.KEYWORD_MATCHING_DONUT;

import java.util.Iterator;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.commands.DeleteCommand;
import seedu.finance.logic.commands.RedoCommand;
import seedu.finance.logic.commands.SearchCommand;
import seedu.finance.logic.commands.UndoCommand;
import seedu.finance.model.Model;
import seedu.finance.model.category.Category;
import seedu.finance.model.record.Record;



public class SearchCommandSystemTest extends FinanceTrackerSystemTest {

    @Test
    public void search() {
        /* Case: search without a flag
         * -> rejected
         */
        /* Case: find records with a incorrect flag*/

        String command = SearchCommand.COMMAND_WORD + " " + DONUT.getName();
        String expectedResultMessage = "Invalid command format! \n" + SearchCommand.NO_FLAG;
        assertCommandFailure(command, expectedResultMessage);
        assertSelectedCardUnchanged();

        /* Case: search with 2 valid flag
         * -> rejected
         */
        /* Case: find records with a incorrect flag*/

        command = SearchCommand.COMMAND_WORD + " -cat -name " + DONUT.getName();
        expectedResultMessage = "Invalid command format! \n" + SearchCommand.ONE_FLAG_ONLY;
        assertCommandFailure(command, expectedResultMessage);
        assertSelectedCardUnchanged();


        /* Case: find multiple records in finance tracker, command with leading spaces and trailing spaces
         * -> 2 records found
         */

        command = "   " + SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " "
                + KEYWORD_MATCHING_DONUT + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BANANA, DONUT); // Banana Donut and Chocolate Donut
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where record list is displaying the records we are finding
         * -> 2 records found
         */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + KEYWORD_MATCHING_DONUT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: Repeat previous search command where record list is displaying the records we are finding again,
         * this time using its alias, find.
         * -> 2 records found
         */

        command = SearchCommand.COMMAND_ALIAS + " " + COMMAND_FLAG_NAME + " " + KEYWORD_MATCHING_DONUT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find record where record list is not displaying the record we are finding -> 1 record found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "Cap";
        ModelHelper.setFilteredList(expectedModel, CAP);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple records in finance tracker, 2 keywords -> 2 records found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " Banana Donut";
        ModelHelper.setFilteredList(expectedModel, BANANA, DONUT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple records in finance tracker, 2 keywords in reversed order -> 2 records found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " Donut Banana";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple records in finance tracker, 2 keywords with 1 repeat -> 2 records found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "Donut Banana Donut";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find multiple records in finance tracker, 2 matching keywords and 1 non-matching keyword
         * -> 2 records found
         */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " "
                + "Donut Banana NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */

        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);


        /* Case: redo previous find command -> rejected */

        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);


        /* Case: find same records in finance tracker after deleting 1 of them -> 1 record found */

        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getFinanceTracker().getRecordList().contains(BANANA));
        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + KEYWORD_MATCHING_DONUT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DONUT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find record in finance tracker, keyword is same as name but of different case -> 1 record found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "DoNuT";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> 0 records found */

        command = "FiNd -name Donut";
        assertCommandSuccess(command, expectedModel);

        /* Case: find record in finance tracker, keyword is substring of name -> 0 records found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "Don";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find record in finance tracker, name is substring of keyword -> 0 records found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "Donuts";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find record not in finance tracker -> 0 records found */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "Milo";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find records with a incorrect flag*/

        command = SearchCommand.COMMAND_WORD + " -amount " + DONUT.getAmount().toString();
        expectedResultMessage = "Invalid command format! \n" + SearchCommand.INVALID_FLAG;
        assertCommandFailure(command, expectedResultMessage);
        assertSelectedCardUnchanged();


        /* Case: find category of record but with invalid flag in finance tracker -> 0 records found */

        Category category = DONUT.getCategory();
        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + category.categoryName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Find records in finance tracker using the category flag.
         * -> 3 records found
         */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_CATEGORY + " " + "Food";
        ModelHelper.setFilteredList(expectedModel, BANANA, DONUT, FRUITS); // Banana Donut, Chocolate Donut and Fruits
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Find records in finance tracker using the category flag using a category that was not used.
         * -> 0 records found
         */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_CATEGORY + " " + "NonMatchingCategory";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Find records in finance tracker using the date flag.
         * -> 1 records found
         */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_DATE + " " + "12/05/2017";
        ModelHelper.setFilteredList(expectedModel, CAP);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: Find records in finance tracker using the date flag using a date that does not match.
         * -> 0 records found
         */

        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_DATE + " " + "01/01/1996";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


        /* Case: find while a record is selected -> selected card deselected */

        showAllRecords();
        selectRecord(Index.fromOneBased(1));
        assertFalse(getRecordListPanel().getHandleToSelectedCard().getName().equals(DONUT.getName().fullName));
        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + "Donut";
        ModelHelper.setFilteredList(expectedModel, DONUT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();


        /* Case: find record in empty finance tracker -> 0 records found */

        deleteAllRecords();
        command = SearchCommand.COMMAND_WORD + " " + COMMAND_FLAG_NAME + " " + KEYWORD_MATCHING_DONUT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DONUT);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();


    }


    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_RECORDS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */

    private void assertCommandSuccess(String command, Model expectedModel) {
        ObservableList<Record> filteredRecord = expectedModel.getFilteredRecordList();
        Double totalSpent = Double.valueOf(0);
        Iterator<Record> recordIterator = filteredRecord.iterator();
        while (recordIterator.hasNext()) {
            totalSpent += recordIterator.next().getAmount().getValue();
        }

        String expectedResultMessage = String.format(
                MESSAGE_RECORDS_LISTED_OVERVIEW + "\nTotal spent on searched records = $ "
                        + String.format("%.2f", totalSpent), expectedModel.getFilteredRecordList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }


    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code FinanceTrackerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
