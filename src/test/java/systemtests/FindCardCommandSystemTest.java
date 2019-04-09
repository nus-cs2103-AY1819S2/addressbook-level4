package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.updateCardsView;
import static seedu.address.testutil.TypicalCards.HELLO_WORLD;
import static seedu.address.testutil.TypicalCards.KEYWORD_MATCHING_HTTP;
import static seedu.address.testutil.TypicalCards.TRANSPORT;
import static seedu.address.testutil.TypicalCards.getTypicalDeck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CardsView;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.FindCardCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.QuestionContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCardCommandSystemTest extends TopDeckSystemTest {
    private static final String OPEN_DECK = "open 1";

    @Test
    public void find() {
        Model expectedModel = getModel();
        expectedModel.changeDeck(getTypicalDeck());
        CardsView cardsView = (CardsView) expectedModel.getViewState();

        executeCommand(OPEN_DECK);

        /* Case: find multiple cards in deck, command with leading spaces and trailing spaces
         * -> 2 cards found
         */
        String command = "   " + FindCardCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_HTTP + "   ";
        QuestionContainsKeywordsPredicate predicate =
            new QuestionContainsKeywordsPredicate(Arrays.asList(KEYWORD_MATCHING_HTTP));
        cardsView.updateFilteredList(predicate);

        assertCommandSuccess(command, expectedModel);

        /* Case: repeat previous find command where card list is displaying the cards we are finding
         * -> 2 cards found
         */
        command = FindCardCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_HTTP;
        assertCommandSuccess(command, expectedModel);

        /* Case: find card where card list is not displaying the card we are finding -> 1 card found */
        command = FindCardCommand.COMMAND_WORD + " Hello";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Hello"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple cards in deck, 2 keywords -> 2 cards found */
        command = FindCardCommand.COMMAND_WORD + " transport layer";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("transport", "layer"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple cards in deck, 2 keywords in reversed order -> 2 cards found */
        command = FindCardCommand.COMMAND_WORD + " layer transport";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple cards in deck, 2 keywords with 1 repeat -> 2 cards found */
        command = FindCardCommand.COMMAND_WORD + " transport layer transport";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple cards in deck, 2 matching keywords and 1 non-matching keyword
         * -> 2 cards found
         */
        command = FindCardCommand.COMMAND_WORD + " transport layer NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);

        /* Case: find tags of card in deck -> 1 cards found */
        List<String> tagStrings = new ArrayList<>();
        for (Tag tag: TRANSPORT.getTags()) {
            tagStrings.add(tag.toString().substring(1, tag.toString().length() - 1));
        }

        predicate = new QuestionContainsKeywordsPredicate(tagStrings);
        cardsView.updateFilteredList(predicate);
        command = FindCardCommand.COMMAND_WORD + " " + tagStrings.get(0);
        assertCommandSuccess(command, expectedModel);

        /* Case: find card in deck with the full question */
        String question = HELLO_WORLD.getQuestion();
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList(question));
        cardsView.updateFilteredList(predicate);
        command = FindCardCommand.COMMAND_WORD + " \"" + question + "\"";
        assertCommandSuccess(command, expectedModel);

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same cards in deck after deleting 1 of them -> 0 card found */
        executeCommand(DeleteCardCommand.COMMAND_WORD + " 1");

        expectedModel.deleteCard(HELLO_WORLD, cardsView.getActiveDeck());
        updateCardsView(expectedModel);
        cardsView = (CardsView) expectedModel.getViewState();
        assertFalse(cardsView.getFilteredList().contains(HELLO_WORLD));

        command = FindCardCommand.COMMAND_WORD + " Hello?";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Hello?"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find card in deck, keyword is same as name but of different case -> 1 card found */
        command = FindCardCommand.COMMAND_WORD + " HttP";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("HttP"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find card in deck, keyword is substring of name -> 0 cards found */
        command = FindCardCommand.COMMAND_WORD + " HT";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("HT"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find card in deck, name is substring of keyword -> 0 cards found */
        command = FindCardCommand.COMMAND_WORD + " Transpo";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Transpo"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find card not in deck -> 0 cards found */
        command = FindCardCommand.COMMAND_WORD + " NotInDeck";
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList(" NotInDeck"));
        cardsView.updateFilteredList(predicate);
        assertCommandSuccess(command, expectedModel);

        /* Case: find answer of card in deck -> 0 cards found */
        command = FindCardCommand.COMMAND_WORD + " " + TRANSPORT.getAnswer();
        assertCommandSuccess(command, expectedModel);

        /* Case: mixed case command word -> rejected */
        command = "FiNd HTTP";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_CARDS_LISTED_OVERVIEW} with the number of people in the
     * filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style
     * class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        CardsView cardsView = (CardsView) expectedModel.getViewState();

        String expectedResultMessage = String
            .format(MESSAGE_CARDS_LISTED_OVERVIEW, cardsView.getFilteredList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command
     * box has the
     * error style.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedDeckUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
