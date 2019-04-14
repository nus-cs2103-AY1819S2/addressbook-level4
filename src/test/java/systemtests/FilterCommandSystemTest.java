package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.FILTERNAME_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.job.JobListName.APPLICANT_NAME;
import static seedu.address.testutil.TypicalObjects.BENSON;
import static seedu.address.testutil.TypicalObjects.CARL;
import static seedu.address.testutil.TypicalObjects.DANIEL;
import static seedu.address.testutil.TypicalObjects.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.logic.commands.ClearFilterCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.Model;

public class FilterCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void filter() {
        String clearCommand = ClearFilterCommand.COMMAND_WORD;
        /* ------------------ Performing filter operation while in All Jobs Showing Screen ------------------------- */

        /* Case: Filter multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command =
            "   " + FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);

        /* Case: repeat previous Filter command where person list is displaying the persons we are Filtering
         * -> 2 persons found
         */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter person where person list is not displaying the person we are Filtering -> 1 person found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Carl";
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter multiple persons in address book, 2 keywords -> 2 persons found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, BENSON, DANIEL);
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter multiple persons in address book, 2 keywords in reversed order -> 2 persons found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter same persons in address book after deleting 1 of them -> 1 person found */
        executeCommand(clearCommand);
        executeCommand(DeleteCommand.COMMAND_WORD + " 2");
        assertFalse(getModel().getAddressBook().getPersonList().contains(BENSON));
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter person in address book, keyword is same as name but of different case -> 1 person found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " MeIeR";
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter person in address book, keyword is substring of name -> 0 persons found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter person in address book, name is substring of keyword -> 0 persons found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: Filter person not in address book -> 0 persons found */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + " Mark";
        assertCommandSuccess(command, expectedModel);
        /* --------------------------------- Performing invalid filter operation ------------------------------------ */

        /* Case: repetitive filter name -> rejected */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + FILTERNAME_DESC + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        executeCommand(command);
        assertCommandFailure(command, FilterCommand.MESSAGE_REDUNDANT_FILTERNAME);

        /* Case: no filter name -> rejected */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + " " + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        executeCommand(command);
        assertCommandFailure(command, String.format(FilterCommand.MESSAGE_LACK_FILTERNAME,
            FilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN + FilterCommand.MESSAGE_USAGE_JOB_DETAIL_SCREEN));

        /* Case: unneeded List name name -> rejected */
        executeCommand(clearCommand);
        command = FilterCommand.COMMAND_WORD + " " + APPLICANT_NAME + " "
            + FILTERNAME_DESC + PREFIX_NAME + KEYWORD_MATCHING_MEIER;
        executeCommand(command);
        assertCommandFailure(command, String.format(FilterCommand.MESSAGE_REDUNDANT_LISTNAME,
            FilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN));

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
            MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
