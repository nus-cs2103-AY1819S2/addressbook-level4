package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.ANSWER_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_FRIEND;
import static seedu.knowitall.logic.commands.CommandTestUtil.HINT_DESC_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_ANSWER_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.INVALID_QUESTION_DESC;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.QUESTION_DESC_SAMPLE_2;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_ANSWER_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_HINT_HUSBAND;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_QUESTION_1;
import static seedu.knowitall.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.model.Model.PREDICATE_SHOW_ALL_CARDS;
import static seedu.knowitall.testutil.TypicalCards.CARD_1;
import static seedu.knowitall.testutil.TypicalCards.CARD_2;
import static seedu.knowitall.testutil.TypicalCards.KEYWORD_MATCHING_MEIER;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.knowitall.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Test;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.commons.core.index.Index;
import seedu.knowitall.logic.commands.EditCommand;
import seedu.knowitall.logic.commands.RedoCommand;
import seedu.knowitall.logic.commands.UndoCommand;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.testutil.CardBuilder;
import seedu.knowitall.testutil.CardUtil;

public class EditCommandSystemTest extends CardFolderSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_CARD;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + QUESTION_DESC_SAMPLE_2
                + "  " + ANSWER_DESC_SAMPLE_2 + " " + HINT_DESC_HUSBAND + " ";
        Card editedCard = new CardBuilder(CARD_2).withHint(VALID_HINT_HUSBAND).build();
        assertCommandSuccess(command, index, editedCard);

        /* Case: undo editing the last card in the list -> last card restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last card in the list -> last card edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setCard(getModel().getActiveFilteredCards().get(INDEX_FIRST_CARD.getZeroBased()), editedCard);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a card with new values same as existing values -> edited */
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                       + HINT_DESC_FRIEND + HINT_DESC_HUSBAND;
        assertCommandSuccess(command, index, CARD_2);

        /* Case: edit a card with new values same as another card's values but with different question -> edited */
        assertTrue(getModel().getActiveCardFolder().getCardList().contains(CARD_2));
        index = INDEX_SECOND_CARD;
        assertNotEquals(getModel().getActiveFilteredCards().get(index.getZeroBased()), CARD_2);
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_2
                       + HINT_DESC_FRIEND + HINT_DESC_HUSBAND;
        editedCard = new CardBuilder(CARD_2).withQuestion(VALID_QUESTION_1).build();
        assertCommandSuccess(command, index, editedCard);

        /* Case: edit a card with new values same as another card's values but with different answer -> edited */
        index = INDEX_SECOND_CARD;
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_1
                        + HINT_DESC_FRIEND + HINT_DESC_HUSBAND;
        editedCard = new CardBuilder(CARD_2).withAnswer(VALID_ANSWER_1).build();
        assertCommandSuccess(command, index, editedCard);

        /* Case: clear hints -> cleared */
        index = INDEX_FIRST_CARD;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_HINT.getPrefix();
        Card cardToEdit = getModel().getActiveFilteredCards().get(index.getZeroBased());
        editedCard = new CardBuilder(cardToEdit).withHint().build();
        assertCommandSuccess(command, index, editedCard);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered card list, edit index within bounds of card folder and card list -> edited */
        showCardsWithQuestion(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_CARD;
        assertTrue(index.getZeroBased() < getModel().getActiveFilteredCards().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + QUESTION_DESC_SAMPLE_2;
        cardToEdit = getModel().getActiveFilteredCards().get(index.getZeroBased());
        editedCard = new CardBuilder(cardToEdit).withQuestion(VALID_QUESTION_2).build();
        assertCommandSuccess(command, index, editedCard);

        /* Case: filtered card list, edit index within bounds of card folder but out of bounds of card list
         * -> rejected
         */
        showCardsWithQuestion(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getActiveCardFolder().getCardList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + QUESTION_DESC_SAMPLE_2,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a card card is selected -------------------------- */

        /* Case: selects first card in the card list, edit a card -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllCards();
        index = INDEX_FIRST_CARD;
        selectCard(index);
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_1 + ANSWER_DESC_SAMPLE_1
                       + HINT_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new card's question
        assertCommandSuccess(command, index, CARD_1, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + QUESTION_DESC_SAMPLE_2,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + QUESTION_DESC_SAMPLE_2,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getActiveFilteredCards().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + QUESTION_DESC_SAMPLE_2,
                Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + QUESTION_DESC_SAMPLE_2,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid question -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased()
                + INVALID_QUESTION_DESC, Question.MESSAGE_CONSTRAINTS);

        /* Case: invalid answer -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_CARD.getOneBased()
                + INVALID_ANSWER_DESC, Answer.MESSAGE_CONSTRAINTS);

        /* Case: edit a card with new values same as another card's values -> rejected */
        executeCommand(CardUtil.getAddCommand(CARD_2));
        assertTrue(getModel().getActiveCardFolder().getCardList().contains(CARD_2));
        index = INDEX_FIRST_CARD;
        assertFalse(getModel().getActiveFilteredCards().get(index.getZeroBased()).equals(CARD_2));
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                       + HINT_DESC_FRIEND + HINT_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: edit a card with new values same as another card's values but with different hints -> rejected */
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_2
                       + HINT_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CARD);

        /* Case: edit a card with new values same as another card's values but with different answer -> rejected */
        command =
                EditCommand.COMMAND_WORD + " " + index.getOneBased() + QUESTION_DESC_SAMPLE_2 + ANSWER_DESC_SAMPLE_1
                       + HINT_DESC_FRIEND + HINT_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_CARD);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Card, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Card, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Card editedCard) {
        assertCommandSuccess(command, toEdit, editedCard, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the card at index {@code toEdit} being
     * updated to values specified {@code editedCard}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Card editedCard,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setCard(expectedModel.getActiveFilteredCards().get(toEdit.getZeroBased()), editedCard);
        expectedModel.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_CARD_SUCCESS, editedCard), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar still indicates the user is in folder correctly.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see CardFolderSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredCard(PREDICATE_SHOW_ALL_CARDS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarIsInFolder(expectedModel.getActiveCardFolderName());
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see CardFolderSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarIsInFolder(expectedModel.getActiveCardFolderName());
    }
}
