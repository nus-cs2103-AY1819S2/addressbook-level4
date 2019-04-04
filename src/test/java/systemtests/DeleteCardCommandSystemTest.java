package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.updateCardsView;
import static seedu.address.logic.commands.DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS;
import static seedu.address.testutil.TypicalCards.LAYER;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalCards.TRANSPORT;
import static seedu.address.testutil.TypicalDecks.DECK_A;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CardsView;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardUtil;



public class DeleteCardCommandSystemTest extends TopDeckSystemTest {
    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT = String
        .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE);
    private static final String CHANGE_DECK_COMMAND = "open 2";
    private static final Deck TEST_DECK = DECK_A;

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown ------------------- */

        Model model = getModel();
        model.changeDeck(TEST_DECK);

        executeCommand(CHANGE_DECK_COMMAND);
        CardsView cardsView = (CardsView) model.getViewState();

        Card toAdd = SUBTRACTION;
        model.addCard(toAdd, cardsView.getActiveDeck());
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        String command = "   " + AddCardCommand.COMMAND_WORD + "  " + QUESTION_DESC_SUBTRACTION + "  "
            + ANSWER_DESC_SUBTRACTION + "   " + TAG_DESC_MATH + " ";
        executeCommand(command);

        toAdd = LAYER;
        model.addCard(toAdd, cardsView.getActiveDeck());
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        command = CardUtil.getAddCommand(toAdd);
        executeCommand(command);

        toAdd = TRANSPORT;
        model.addCard(toAdd, cardsView.getActiveDeck());
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        command = CardUtil.getAddCommand(toAdd);
        executeCommand(command);

        /* Case: delete the first card in the list, command with leading spaces and trailing spaces -> deleted */
        Card target = SUBTRACTION;

        command = "   " + DeleteCardCommand.COMMAND_WORD + " " + 1 + "   ";

        cardsView = assertCommandSuccess(command, target, model, cardsView);

        /* Case: undo deleting the last card in the list -> last card restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;

        model.undoTopDeck();
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo deleting the last card in the list -> last card deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;

        model.redoTopDeck();
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        assertCommandSuccess(command, model, expectedResultMessage);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered card list, delete index within bounds of top deck and card list -> deleted */
        /**TODO
        showCardsWithQuestion(KEYWORD_MATCHING_HTTP, cardsView.getActiveDeck());
        Index index = INDEX_FIRST_CARD;
        assertTrue(index.getZeroBased() < model.getFilteredList().size());
        target = LAYER;

        command = "   " + DeleteCardCommand.COMMAND_WORD + " " + 1 + "   ";

        cardsView = assertCommandSuccess(command, target, model, cardsView);
         **/

        /* Case: filtered card list, delete index within bounds of address book but out of bounds of card list
        -> rejected */
        /**TODO
        showCardsWithQuestion(KEYWORD_MATCHING_HTTP, cardsView.getActiveDeck());
        int invalidIndex = cardsView.getActiveDeck().getCards().internalList.size() + 1;
        command = DeleteCardCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, model, MESSAGE_INVALID_DISPLAYED_INDEX);
         **/

        /* --------------------------------- Performing invalid delete operation ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCardCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, model, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCardCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, model, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
            getModel().getTopDeck().getDeckList().size() + 1);
        command = DeleteCardCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, model, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCardCommand.COMMAND_WORD + " abc", model,
            MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCardCommand.COMMAND_WORD + " 1 abc", model,
            MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", model, MESSAGE_UNKNOWN_COMMAND);
    }


    /**
     * Executes the {@code DeleteCardCommand} that deletes {@code target} in the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCardCommand} with the
     * details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code ListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Status bar's sync status changes.<br>
     * Verifications 1 and 3 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private CardsView assertCommandSuccess(String command, Card target, Model model, CardsView cardsView) {
        model.deleteCard(target, cardsView.getActiveDeck());

        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        String expectedResultMessage = String.format(MESSAGE_DELETE_CARD_SUCCESS, target);
        assertCommandSuccess(command, model, expectedResultMessage);

        model.commitTopDeck();

        return cardsView;
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Card, Model, CardsView)}
     * except asserts that the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        //assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ListPanel} remain unchanged.<br>
     * Verifications 1 and 3 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        //assertStatusBarUnchanged();
    }
}
