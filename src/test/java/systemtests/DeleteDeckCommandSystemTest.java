package systemtests;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteDeckCommand.MESSAGE_DELETE_DECK_SUCCESS;
import static seedu.address.testutil.TestUtil.getDeck;
import static seedu.address.testutil.TestUtil.getLastIndexDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.DecksView;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;

public class DeleteDeckCommandSystemTest extends TopDeckSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT = String
            .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteDeckCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown-------------------- */

        /* Case: delete the first deck in the list, command with leading spaces and trailing spaces -> deleted */
        Model model = getModel();
        DecksView decksView = (DecksView) model.getViewState();

        String command = "     " + DeleteCardCommand.COMMAND_WORD + "      " + INDEX_FIRST_DECK
                .getOneBased() + "       ";
        Deck deletedDeck = removeDeck(model, INDEX_FIRST_DECK);
        String expectedResultMessage = String.format(MESSAGE_DELETE_DECK_SUCCESS, deletedDeck);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: delete the last deck in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastDeckIndex = getLastIndexDeck(decksView);
        assertCommandSuccess(lastDeckIndex);

        /* Case: undo deleting the last deck in the list -> last deck restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last deck in the list -> last deck deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeDeck(modelBeforeDeletingLast, lastDeckIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* ------------------ Performing delete operation while a filtered list is being shown---------------------- */

        /* Case: filtered deck list, delete index within bounds of address book and deck list -> deleted */
        Index index = INDEX_FIRST_CARD;
        showDecksWithName("A", decksView);
        assertTrue(index.getZeroBased() <= decksView.getFilteredList().size());

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteDeckCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteDeckCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getTopDeck().getDeckList().size() + 1);
        command = DeleteDeckCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteDeckCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteDeckCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Deck} at the specified {@code index} in {@code model}'s deck.
     *
     * @return the removed deck
     */
    private Deck removeDeck(Model model, Index index) {
        Deck targetDeck = getDeck((DecksView) model.getViewState(), index);
        model.deleteDeck(targetDeck);
        return targetDeck;
    }

    /**
     * Deletes the deck at {@code toDelete} by creating a default {@code DeleteDeckCommand} using {@code
     * toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     *
     * @see DeleteDeckCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Deck deletedDeck = removeDeck(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_DECK_SUCCESS, deletedDeck);

        assertCommandSuccess(DeleteDeckCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected deck remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that
     * the browser url
     * and selected deck are expected to update accordingly depending on the deck at {@code
     * expectedSelectedDeckIndex}.
     *
     * @see DeleteDeckCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see TopDeckSystemTest#assertSelectedDeckChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedDeckIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedDeckIndex != null) {
            assertSelectedDeckChanged(expectedSelectedDeckIndex);
        } else {
            assertSelectedDeckUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected deck and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedDeckUnchanged();
        assertCommandBoxShowsErrorStyle();
    }
}
