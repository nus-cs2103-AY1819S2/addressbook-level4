package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.QUESTION_DESC_SUBTRACTION;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_MATH;
import static seedu.address.testutil.TypicalCards.SUBTRACTION;
import static seedu.address.testutil.TypicalDecks.DECK_A;

import org.junit.Test;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.CardUtil;

public class AddCardCommandSystemTest extends TopDeckSystemTest {

        private static final String CHANGE_DECK_COMMAND = "open 2";
        private static final Deck TEST_DECK = DECK_A;

        @Test
        public void add() {
            Model model = getModel();
            model.changeDeck(TEST_DECK);
            /* ------------------------ Perform add operations on the shown unfiltered list
            ----------------------------- */

            executeCommand(CHANGE_DECK_COMMAND);

            /* Case: add a card without tags to a non-empty address book, command with leading spaces and
            trailing spaces
             * -> added
             */
            Card toAdd = SUBTRACTION;
            String command = "   " + AddCardCommand.COMMAND_WORD + "  " + QUESTION_DESC_SUBTRACTION + "  "
                + ANSWER_DESC_SUBTRACTION + "   " + TAG_DESC_MATH + " ";
            assertCommandSuccess(command, toAdd);

            /* Case: undo adding Subtraction card to the list -> Subtraction card deleted */
            command = UndoCommand.COMMAND_WORD;
            String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
            assertCommandSuccess(command, model, expectedResultMessage);

    //        /* Case: redo adding Subtraction card to the list -> Subtraction card added again */
    //        command = RedoCommand.COMMAND_WORD;
    //        model.addCard(toAdd);
    //        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
    //        assertCommandSuccess(command, model, expectedResultMessage);
    //
    //        /* Case: add a card with all fields same as another card in the deck except question -> added */
    //        toAdd = new CardBuilder(ADDITION).withQuestion(VALID_QUESTION_UNIQUE).build();
    //        command = AddCardCommand.COMMAND_WORD + QUESTION_DESC_UNIQUE + ANSWER_DESC_ADDITION
    //                + TAG_DESC_MATH;
    //        assertCommandSuccess(command, toAdd);
    //
    //        /* Case: add to empty address book -> added */
    //        deleteAllDecks();
    //        assertCommandSuccess(ADDITION);
    //
    //        /* Case: add a card with tags, command with parameters in random order -> added */
    //        toAdd = SUBTRACTION;
    //        command = AddCardCommand.COMMAND_WORD + TAG_DESC_MATH + ANSWER_DESC_SUBTRACTION +
    //        QUESTION_DESC_SUBTRACTION;
    //        assertCommandSuccess(command, toAdd);
    //
    //        /* Case: add a card, missing tags -> added */
    //        assertCommandSuccess(NO_TAG);
    //
    //        /* -------------------------- Perform add operation on the shown filtered list
    //        ------------------------------ */
    //
    //        /* Case: filters the card list before adding -> added */
    //        showDecksWithQuestion(KEYWORD_MATCHING_HTTP);
    //        assertCommandSuccess(HELLO_WORLD);
    //
    //        /* ------------------------ Perform add operation while a card card is selected
    //        --------------------------- */
    //
    //        /* Case: selects first card in the card list, add a card -> added, card selection remains
    //        unchanged */
    //        selectDeck(Index.fromOneBased(1));
    //        assertCommandSuccess(MULTIPLICATION);
    //
    //        /* ----------------------------------- Perform invalid add operations
    //        --------------------------------------- */
    //
    //        /* Case: add a duplicate card -> rejected */
    //        command = CardUtil.getAddCommand(NO_TAG);
    //        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);
    //
    //        /* Case: add a duplicate card except with different answer -> rejected */
    //        toAdd = new CardBuilder(NO_TAG).withAnswer(VALID_ANSWER_ADDITION).build();
    //        command = CardUtil.getAddCommand(toAdd);
    //        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);
    //
    //        /* Case: add a duplicate card except with different tags -> rejected */
    //        command = CardUtil.getAddCommand(NO_TAG) + " " + PREFIX_TAG.getPrefix() + "interesting";
    //        assertCommandFailure(command, AddCardCommand.MESSAGE_DUPLICATE_CARD);
    //
    //        /* Case: missing question -> rejected */
    //        command = AddCardCommand.COMMAND_WORD + ANSWER_DESC_ADDITION + TAG_DESC_MATH;
    //        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand
    //        .MESSAGE_USAGE));
    //
    //        /* Case: missing answer -> rejected */
    //        command = AddCardCommand.COMMAND_WORD + QUESTION_DESC_ADDITION + TAG_DESC_MATH;
    //        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCardCommand
    //        .MESSAGE_USAGE));
    //
    //        /* Case: invalid keyword -> rejected */
    //        command = "adds " + CardUtil.getCardDetails(toAdd);
    //        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
    //
    //        /* Case: invalid tag -> rejected */
    //        command = AddCardCommand.COMMAND_WORD + QUESTION_DESC_ADDITION + ANSWER_DESC_ADDITION +
    //        INVALID_TAG_DESC;
    //        assertCommandFailure(command, Tag.MESSAGE_CONSTRAINTS);
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
    private void assertCommandSuccess(Card toAdd) {
        assertCommandSuccess(CardUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     *
     * @see AddCardCommandSystemTest#assertCommandSuccess(Card)
     */
    private void assertCommandSuccess(String command, Card toAdd) {
        Model expectedModel = getModel();
        expectedModel.changeDeck(TEST_DECK);
        expectedModel.addCard(toAdd, TEST_DECK);
        String expectedResultMessage = String.format(AddCardCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddCardCommandSystemTest#assertCommandSuccess(String, Card)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
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
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
