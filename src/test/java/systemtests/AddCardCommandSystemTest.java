package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_ADDITION;
import static seedu.address.logic.commands.CommandTestUtil.updateCardsView;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalCards.ADDITION;
import static seedu.address.testutil.TypicalCards.HELLO_WORLD;
import static seedu.address.testutil.TypicalCards.KEYWORD_MATCHING_HTTP;
import static seedu.address.testutil.TypicalCards.NO_TAG;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CardsView;
import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;
import seedu.address.testutil.CardUtil;

public class AddCardCommandSystemTest extends TopDeckSystemTest {

    private static final String CHANGE_DECK_COMMAND = "open 2";
    private static final Deck TEST_DECK = DECK_A;

    @Test
    public void add() {
        Model model = getModel();
        executeCommand(CHANGE_DECK_COMMAND);

        model.changeDeck(TEST_DECK);
        CardsView cardsView = (CardsView) model.getViewState();
        /* ------------------------ Perform add operations on the shown unfiltered list------------------------ */

        /* Case: add a card without tags to a non-empty topdeck, command with leading spaces and
        trailing spaces* -> added*/
        Card toAdd = SUBTRACTION;
        model.addCard(toAdd, cardsView.getActiveDeck());

        String command = "   " + AddCardCommand.COMMAND_WORD + "  " + QUESTION_DESC_SUBTRACTION + "  "
            + ANSWER_DESC_SUBTRACTION + "   " + TAG_DESC_MATH + " ";

        cardsView = assertCommandSuccess(command, toAdd, model, cardsView);

        /* Case: undo adding Subtraction card to the list -> Subtraction card deleted */;
        command = UndoCommand.COMMAND_WORD;
        model.deleteCard(toAdd, cardsView.getActiveDeck());
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Subtraction card to the list -> Subtraction card added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCard(toAdd, cardsView.getActiveDeck());
        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        model.addCard(toAdd, TEST_DECK);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a card with tags, command with parameters in random order -> added */
        toAdd = ADDITION;
        model.addCard(toAdd, cardsView.getActiveDeck());

        command = AddCardCommand.COMMAND_WORD + TAG_DESC_MATH + ANSWER_DESC_ADDITION
            + QUESTION_DESC_ADDITION;

        cardsView = assertCommandSuccess(command, toAdd, model, cardsView);

        /* Case: add a card, missing tags -> added */
        toAdd = NO_TAG;
        model.addCard(toAdd, cardsView.getActiveDeck());

        cardsView = assertCommandSuccess(toAdd, model, cardsView);

        /* -------------------------- Perform add operation on the shown filtered list ----------------------------- */

        /* Case: filters the card list before adding -> added */
        showCardsWithQuestion(KEYWORD_MATCHING_HTTP, cardsView.getActiveDeck());
        toAdd = HELLO_WORLD;
        model.addCard(toAdd, cardsView.getActiveDeck());

        cardsView = assertCommandSuccess(toAdd, model, cardsView);

        /* ----------------------------------- Perform invalid add operations -------------------------------------- */

        /* Case: add a duplicate card -> rejected */
        command = CardUtil.getAddCommand(NO_TAG);
        assertCommandFailure(command, model, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: add a duplicate card except with different answer -> rejected */
        toAdd = new CardBuilder(NO_TAG).withAnswer(VALID_ANSWER_ADDITION).build();
        command = CardUtil.getAddCommand(toAdd);
        assertCommandFailure(command, model, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: add a duplicate card except with different tags -> rejected */
        command = CardUtil.getAddCommand(NO_TAG) + " " + PREFIX_TAG.getPrefix() + "interesting";
        assertCommandFailure(command, model, AddCardCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: missing question -> rejected */
        command = AddCardCommand.COMMAND_WORD + ANSWER_DESC_ADDITION + TAG_DESC_MATH;
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand
            .MESSAGE_USAGE));

        /* Case: missing answer -> rejected */
        command = AddCardCommand.COMMAND_WORD + QUESTION_DESC_ADDITION + TAG_DESC_MATH;
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand
            .MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + CardUtil.getCardDetails(toAdd);
        assertCommandFailure(command, model, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid tag -> rejected */
        command = AddCardCommand.COMMAND_WORD + QUESTION_DESC_ADDITION + ANSWER_DESC_ADDITION
            + INVALID_TAG_DESC;
        assertCommandFailure(command, model, Tag.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCardCommand} that adds {@code toAdd} to the model and asserts that the,<br>
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
    private CardsView assertCommandSuccess(Card toAdd, Model model, CardsView cardsView) {
        String command = CardUtil.getAddCommand(toAdd);
        return assertCommandSuccess(command, toAdd, model, cardsView);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Card, Model, CardsView)}.
     * Executes {@code command} instead.
     *
     * @see AddCardCommandSystemTest#assertCommandSuccess(Card, Model, CardsView)
     */
    private CardsView assertCommandSuccess(String command, Card toAdd, Model model, CardsView cardsView) {
        model.addCard(toAdd, cardsView.getActiveDeck());

        updateCardsView(model);
        cardsView = (CardsView) model.getViewState();

        String expectedResultMessage = String.format(AddCardCommand.MESSAGE_SUCCESS, toAdd);
        assertCommandSuccess(command, model, expectedResultMessage);

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
        //assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
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
        //assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
