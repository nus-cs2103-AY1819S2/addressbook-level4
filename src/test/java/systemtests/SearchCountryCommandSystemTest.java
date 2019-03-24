package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.travel.commons.core.Messages.MESSAGE_PLACES_LISTED_OVERVIEW;
import static seedu.travel.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BENSON;
import static seedu.travel.testutil.TypicalPlaces.CARL;
import static seedu.travel.testutil.TypicalPlaces.DANIEL;
import static seedu.travel.testutil.TypicalPlaces.ELLE;
import static seedu.travel.testutil.TypicalPlaces.FIONA;
import static seedu.travel.testutil.TypicalPlaces.GEORGE;
import static seedu.travel.testutil.TypicalPlaces.KEYWORD_MATCHING_SGP;

import org.junit.Test;

import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.commands.DeleteCommand;
import seedu.travel.logic.commands.RedoCommand;
import seedu.travel.logic.commands.SearchCountryCommand;
import seedu.travel.logic.commands.UndoCommand;
import seedu.travel.model.Model;

public class SearchCountryCommandSystemTest extends TravelBuddySystemTest {

    @Test
    public void find() {
        /* Case: find multiple places in travel buddy, command with leading spaces and trailing spaces
         * -> 6 places found
         */
        String command = "   " + SearchCountryCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SGP + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where place list is displaying the places we are finding
         * -> 6 places found
         */
        command = SearchCountryCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SGP;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in travel book, 2 keywords -> 7 places found */
        command = SearchCountryCommand.COMMAND_WORD + " SGP JPN";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in travel book, 2 keywords in reversed order -> 7 places found */
        command = SearchCountryCommand.COMMAND_WORD + " JPN SGP";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in travel book, 2 keywords with 1 repeat -> 7 places found */
        command = SearchCountryCommand.COMMAND_WORD + " JPN SGP JPN";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple places in travel book, 2 matching keywords and 1 non-matching keyword
         * -> 7 places found
         */
        command = SearchCountryCommand.COMMAND_WORD + " SGP JPN CHN";
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

        /* Case: find same places in travel book after deleting 1 of them -> 5 places found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getTravelBuddy().getPlaceList().contains(ALICE));
        command = SearchCountryCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SGP;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, CARL, ELLE, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find place in travel book, keyword is not found in travel book -> 0 places found */
        command = SearchCountryCommand.COMMAND_WORD + " HKG";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a place is selected -> selected card deselected */
        showAllPlaces();
        selectPlace(Index.fromOneBased(1));
        assertFalse(getPlaceListPanel().getHandleToSelectedCard().getName().equals(CARL.getName().fullName));
        command = SearchCountryCommand.COMMAND_WORD + " JPN";
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find place in empty travel book -> 0 places found */
        deleteAllPlaces();
        command = SearchCountryCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SGP;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, CARL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "sEaRcHc SGP";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PLACES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PLACES_LISTED_OVERVIEW, expectedModel.getFilteredPlaceList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
