package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_DECKS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalDecks.DECK_B;
import static seedu.address.testutil.TypicalDecks.DECK_C;
import static seedu.address.testutil.TypicalDecks.DECK_D;
import static seedu.address.testutil.TypicalDecks.DECK_G;
import static seedu.address.testutil.TypicalDecks.DECK_H;
import static seedu.address.testutil.TypicalDecks.KEYWORD_MATCHING_JOHN;

import org.junit.Test;

import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.FindDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.DecksView;
import seedu.address.model.Model;


public class FindDeckCommandSystemTest extends TopDeckSystemTest {

    @Test
    public void find() {
        /* Case: find multiple decks in TopDeck, command with leading spaces and trailing spaces
         * -> 2 decks found
         */
        String command = "   " + FindDeckCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JOHN + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredDeckList(expectedModel, DECK_G, DECK_H); // first names of Deck_G and Deck_H are John
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: repeat previous find command where deck list is displaying the decks we are finding
         * -> 2 decks found
         */
        command = FindDeckCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JOHN;
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find deck where deck list is not displaying the deck we are finding -> 1 deck found */
        command = FindDeckCommand.COMMAND_WORD + " Calculus";
        ModelHelper.setFilteredDeckList(expectedModel, DECK_C);
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find multiple decks in TopDeck, 2 keywords -> 2 decks found */
        command = FindDeckCommand.COMMAND_WORD + " Bacon Calculus";
        ModelHelper.setFilteredDeckList(expectedModel, DECK_B, DECK_C);
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find multiple decks in TopDeck, 2 keywords in reversed order -> 2 decks found */
        command = FindDeckCommand.COMMAND_WORD + " Calculus Bacon";
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find multiple decks in TopDeck, 2 keywords with 1 repeat -> 2 decks found */
        command = FindDeckCommand.COMMAND_WORD + " Calculus Bacon Calculus";
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find multiple decks in TopDeck, 2 matching keywords and 1 non-matching keyword
         * -> 2 decks found
         */
        command = FindDeckCommand.COMMAND_WORD + " Calculus Bacon NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same decks in TopDeck after deleting 1 of them -> 1 deck found */
        executeCommand(DeleteDeckCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getTopDeck().getDeckList().contains(DECK_G));
        command = FindDeckCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JOHN;
        expectedModel = getModel();
        ModelHelper.setFilteredDeckList(expectedModel, DECK_H);
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find deck in TopDeck, keyword is same as name but of different case -> 1 decks found */
        command = FindDeckCommand.COMMAND_WORD + " JoHn";
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find decks in TopDeck, keyword is substring of name -> 0 decks found */
        command = FindDeckCommand.COMMAND_WORD + " Joh";
        ModelHelper.setFilteredDeckList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find decks in TopDeck, name is substring of keyword -> 0 decks found */
        command = FindDeckCommand.COMMAND_WORD + " Johnny";
        ModelHelper.setFilteredDeckList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find decks not in TopDeck -> 0 decks found */
        command = FindDeckCommand.COMMAND_WORD + " Julius";
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: find decks in empty TopDeck -> 0 decks found */
        deleteAllDecks();
        command = FindDeckCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_JOHN;
        expectedModel = getModel();
        ModelHelper.setFilteredDeckList(expectedModel, DECK_D);
        assertCommandSuccess(command, expectedModel);
        assertSelectedDeckUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_DECKS_LISTED_OVERVIEW} with the number of deck in the
     * filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected deck updated accordingly, depending on {@code deckStatus}.
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        DecksView decksView = (DecksView) expectedModel.getViewState();
        String expectedResultMessage = String.format(
                MESSAGE_DECKS_LISTED_OVERVIEW, decksView.getFilteredList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        //assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected deck and status bar remain unchanged, and the command box has the
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
        //assertStatusBarUnchanged();
    }
}

