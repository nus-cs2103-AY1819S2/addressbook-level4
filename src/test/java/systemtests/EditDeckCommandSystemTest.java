package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_B_ARGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static seedu.address.testutil.TypicalDecks.KEYWORD_MATCHING_JOHN;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DECK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.DecksView;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.DeckUtil;

public class EditDeckCommandSystemTest extends TopDeckSystemTest {

    @Test
    public void edit() {
        Model model = getModel();
        DecksView decksView = (DecksView) model.getViewState();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit deck, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_DECK;
        String command = " " + EditDeckCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + VALID_DECK_NAME_B_ARGS + "  ";
        Deck editedDeck = new DeckBuilder(VALID_DECK_B).build();
        assertCommandSuccess(command, index, editedDeck);

        /* Case: undo editing the last deck in the list -> last deck restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last deck in the list -> last deck edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateDeck(
                decksView.getFilteredList().get(INDEX_FIRST_DECK.getZeroBased()), editedDeck);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a deck with new values same as existing values -> edited */
        command = EditDeckCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DECK_NAME_B_ARGS;
        assertCommandSuccess(command, index, VALID_DECK_B);

        index = INDEX_FIRST_DECK;
        Deck deckToEdit = decksView.getFilteredList().get(index.getZeroBased());
        editedDeck = new DeckBuilder(deckToEdit).build();

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered deck list, edit index within bounds of TopDeck but out of bounds of deck list
         * -> rejected
         */
        showDecksWithName(KEYWORD_MATCHING_JOHN, decksView);
        int invalidIndex = getModel().getTopDeck().getDeckList().size();
        assertCommandFailure(EditDeckCommand.COMMAND_WORD + " " + invalidIndex + VALID_DECK_NAME_B_ARGS,
                Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditDeckCommand.COMMAND_WORD + " 0" + VALID_DECK_NAME_B_ARGS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditDeckCommand.COMMAND_WORD + " -1" + VALID_DECK_NAME_B_ARGS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = decksView.getFilteredList().size() + 1;
        assertCommandFailure(EditDeckCommand.COMMAND_WORD + " " + invalidIndex + VALID_DECK_NAME_B_ARGS,
                Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditDeckCommand.COMMAND_WORD + VALID_DECK_NAME_B_ARGS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE));

        /* Case: edit a deck with new values same as another deck's values -> rejected */
        executeCommand(DeckUtil.getAddDeckCommand(VALID_DECK_A));
        assertTrue(getModel().getTopDeck().getDeckList().contains(VALID_DECK_A));
        index = INDEX_FIRST_DECK;
        assertFalse(decksView.getFilteredList().get(index.getZeroBased()).equals(VALID_DECK_A));
        command = EditDeckCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DECK_NAME_A_ARGS;
        assertCommandFailure(command, Messages.MESSAGE_DUPLICATE_DECK);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Deck, Index)} except that
     * the browser url and selected deck remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditDeckCommandSystemTest#assertCommandSuccess(String, Index, Deck, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Deck editedDeck) {
        assertCommandSuccess(command, toEdit, editedDeck, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and
     * in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditDeckCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the deck at index {@code toEdit} being
     * updated to values specified {@code editedDeck}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditDeckCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Deck editedDeck,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        DecksView decksView = (DecksView) expectedModel.getViewState();
        expectedModel.updateDeck(decksView.getFilteredList().get(toEdit.getZeroBased()), editedDeck);
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditDeckCommand.MESSAGE_EDIT_DECK_SUCCESS, editedDeck), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)}
     * except that the
     * browser url and selected deck remain unchanged.
     *
     * @see EditDeckCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected deck update accordingly depending on the deck at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see TopDeckSystemTest#assertSelectedDeckChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String
            expectedResultMessage, Index expectedSelectedCardIndex) {
        executeCommand(command);
        DecksView decksView = (DecksView) expectedModel.getViewState();
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedDeckChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedDeckUnchanged();
        }
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
