package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX;
import static seedu.travel.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.travel.logic.commands.DeleteMultipleCommand.MESSAGE_DELETEM_PLACE_SUCCESS;
import static seedu.travel.testutil.TestUtil.getLastIndex;
import static seedu.travel.testutil.TestUtil.getMidIndex;
import static seedu.travel.testutil.TestUtil.getPlace;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.travel.testutil.TypicalPlaces.KEYWORD_MATCHING_SINGAPORE;

import org.junit.Test;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.commands.DeleteMultipleCommand;
import seedu.travel.logic.commands.RedoCommand;
import seedu.travel.logic.commands.UndoCommand;
import seedu.travel.model.Model;
import seedu.travel.model.place.Place;

public class DeleteMultipleCommandSystemTest extends TravelBuddySystemTest {

    private static final String MESSAGE_INVALID_DELETEM_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteMultipleCommand.MESSAGE_USAGE);

    @Test
    public void deleteMultiple() {
        /* ----------------- Performing deletem operation while an unfiltered list is being shown ------------------- */

        /* Case: delete the first to second place in list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteMultipleCommand.COMMAND_WORD + "      " + INDEX_FIRST_PLACE.getOneBased() + " "
                + INDEX_SECOND_PLACE.getOneBased() + "       ";
        Place deletedPlace = removePlace(expectedModel, INDEX_FIRST_PLACE);
        String expectedResultMessage = String.format(MESSAGE_DELETEM_PLACE_SUCCESS, deletedPlace);
        deletedPlace = removePlace(expectedModel, INDEX_FIRST_PLACE);
        expectedResultMessage = expectedResultMessage.concat(
                String.format(MESSAGE_DELETEM_PLACE_SUCCESS, deletedPlace));

        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last place in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastPlaceIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPlaceIndex);

        /* Case: undo deleting the last place in the list -> last place restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last place in the list -> last place deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePlace(modelBeforeDeletingLast, lastPlaceIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle place in the list -> deleted */
        Index middlePlaceIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePlaceIndex);

        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(UndoCommand.COMMAND_WORD);
        executeCommand(UndoCommand.COMMAND_WORD);

        /* ------------------ Performing deletem operation while a filtered list is being shown --------------------- */

        /* Case: filtered place list, delete index within bounds of travelBuddy and place list -> deleted */
        showPlacesWithName(KEYWORD_MATCHING_SINGAPORE);
        Index index = INDEX_FIRST_PLACE;
        assertTrue(index.getZeroBased() < getModel().getFilteredPlaceList().size());
        assertCommandSuccess(index);

        /* Case: filtered place list, delete index within bounds of travel book but out of bounds of place list
         * -> rejected
         */
        showPlacesWithName(KEYWORD_MATCHING_SINGAPORE);
        int invalidStartIndex = getModel().getTravelBuddy().getPlaceList().size();
        int invalidEndIndex = getModel().getTravelBuddy().getPlaceList().size() + 2;
        command = DeleteMultipleCommand.COMMAND_WORD + " " + invalidStartIndex + " " + invalidEndIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);

        /* --------------------- Performing deletem operation while a place card is selected ------------------------ */

        /* Case: delete the selected place -> place list panel selects the place before the deleted place */
        showAllPlaces();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPlace(selectedIndex);
        command = DeleteMultipleCommand.COMMAND_WORD + " " + selectedIndex.getOneBased() + " "
                + (selectedIndex.getOneBased() + 2);
        deletedPlace = removePlace(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETEM_PLACE_SUCCESS, deletedPlace);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid deletem operation ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteMultipleCommand.COMMAND_WORD + " 0 2";
        assertCommandFailure(command, MESSAGE_INVALID_DELETEM_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteMultipleCommand.COMMAND_WORD + " -1 2";
        assertCommandFailure(command, MESSAGE_INVALID_DELETEM_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsStartIndex = Index.fromOneBased(
                getModel().getTravelBuddy().getPlaceList().size() + 1);
        Index outOfBoundsEndIndex = Index.fromOneBased(
                getModel().getTravelBuddy().getPlaceList().size() + 3);
        command = DeleteMultipleCommand.COMMAND_WORD + " " + outOfBoundsStartIndex.getOneBased()
                + " " + outOfBoundsEndIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteMultipleCommand.COMMAND_WORD + " abc sa",
                MESSAGE_INVALID_DELETEM_COMMAND_FORMAT);

        /* Case: no arguments -> rejected */
        assertCommandFailure(DeleteMultipleCommand.COMMAND_WORD,
                MESSAGE_INVALID_DELETEM_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEm 1 2", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Place} at the specified {@code index} in {@code model}'s travel book.
     * @return the removed place
     */
    private Place removePlace(Model model, Index index) {
        Place targetPlace = getPlace(model, index);
        model.deletePlace(targetPlace);
        return targetPlace;
    }

    /**
     * Deletes the place at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteMultipleCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Place deletedPlace = removePlace(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETEM_PLACE_SUCCESS, deletedPlace);

        assertCommandSuccess(DeleteMultipleCommand.COMMAND_WORD + " " + toDelete.getOneBased() + " "
                + toDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteMultipleCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see TravelBuddySystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code TravelBuddySystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
