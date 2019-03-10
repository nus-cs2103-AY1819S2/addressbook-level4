package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPlaces.ALICE;
import static seedu.address.testutil.TypicalPlaces.BENSON;
import static seedu.address.testutil.TypicalPlaces.CARL;
import static seedu.address.testutil.TypicalPlaces.ELLE;
import static seedu.address.testutil.TypicalPlaces.GEORGE;
import static seedu.address.testutil.TypicalPlaces.KEYWORD_MATCHING_FOUR;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchRatingCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class SearchRatingCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple places in address book, command with leading spaces and trailing spaces
         * -> 3 places found
         */
        String command = "   " + SearchRatingCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FOUR + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL, ELLE, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where place list is displaying the places we are finding
         * -> 3 places found
         */
        command = SearchRatingCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FOUR;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords -> 4 places found */
        command = SearchRatingCommand.COMMAND_WORD + " 4 5";
        ModelHelper.setFilteredList(expectedModel, BENSON, CARL, ELLE, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords in reversed order -> 4 places found */
        command = SearchRatingCommand.COMMAND_WORD + " 5 4";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 keywords with 1 repeat -> 4 places found */
        command = SearchRatingCommand.COMMAND_WORD + " 5 4 5";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in address book, 2 matching keywords and 1 non-matching keyword
         * -> 4 places found
         */
        command = SearchRatingCommand.COMMAND_WORD + " 4 5 2";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous search rating command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous search rating command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same places in address book after deleting 1 of them -> 3 places found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPlaceList().contains(BENSON));
        command = SearchRatingCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FOUR;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL, ELLE, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in address book, keyword is not found in address book -> 0 places found */
        command = SearchRatingCommand.COMMAND_WORD + " 2";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a place is selected -> selected card deselected */
        showAllPlaces();
        selectPlace(Index.fromOneBased(1));
        assertFalse(getPlaceListPanel().getHandleToSelectedCard().getName().equals(CARL.getName().fullName));
        command = SearchRatingCommand.COMMAND_WORD + " 1";
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find place in empty address book -> 0 places found */
        deleteAllPlaces();
        command = SearchRatingCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FOUR;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "sEaRcHr 4";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPlaceList().size());

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
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
