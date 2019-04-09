package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DECK_NAME_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_A_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DECK_NAME_B_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK_B;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DECKS;
import static seedu.address.testutil.TypicalDecks.DECK_A;
import static seedu.address.testutil.TypicalDecks.DECK_I;
import static seedu.address.testutil.TypicalDecks.KEYWORD_MATCHING_JOHN;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.DecksView;
import seedu.address.logic.commands.AddDeckCommand;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;
import seedu.address.model.deck.Name;
import seedu.address.testutil.DeckBuilder;
import seedu.address.testutil.DeckUtil;

public class AddDeckCommandSystemTest extends TopDeckSystemTest {

    @Test
    public void addDeck() {
        Model model = getModel();
        DecksView decksView = (DecksView) model.getViewState();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a deck to TopDeck, command with leading spaces and trailing spaces
         * -> added
         */
        Deck toAdd = new DeckBuilder(DECK_A).withName(VALID_NAME_DECK_A).build();
        model.addDeck(toAdd);
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        model.commitTopDeck();

        String command = "   " + AddDeckCommand.COMMAND_WORD + "  " + PREFIX_NAME + VALID_NAME_DECK_A;
        String expectedResultMessage = String.format(AddDeckCommand.MESSAGE_SUCCESS, toAdd);
        assertCommandSuccess(command, model, expectedResultMessage);

        model.changeDeck(DECK_A);
        command = "open 3";
        assertCommandSuccess(command, model, OpenDeckCommand.MESSAGE_OPEN_DECK_SUCCESS);

        /* Case: undo adding DECK_A to the list -> Deck_A deleted */
        command = UndoCommand.COMMAND_WORD;
        model.undoTopDeck();
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding DECK_A to the list -> Deck_A added again */
        command = RedoCommand.COMMAND_WORD;
        model.redoTopDeck();
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a Deck with a different name -> added */
        toAdd = new DeckBuilder(DECK_A).withName(VALID_NAME_DECK_B).build();
        model.addDeck(toAdd);
        decksView.updateFilteredList(PREDICATE_SHOW_ALL_DECKS);
        expectedResultMessage = String.format(AddDeckCommand.MESSAGE_SUCCESS, toAdd);
        model.commitTopDeck();

        command = AddDeckCommand.COMMAND_WORD + VALID_DECK_NAME_B_ARGS;
        assertCommandSuccess(command, model, expectedResultMessage);
        //      assertCommandSuccess(command, toAdd);

        /* Case: add to empty TopDeck -> added */
        deleteAllDecks();
        assertCommandSuccess(DECK_A);

        /* Case: add a Deck, use two name prefixes, both valid -> last prefix added */
        command = AddDeckCommand.COMMAND_WORD + VALID_DECK_NAME_A_ARGS + VALID_DECK_NAME_B_ARGS;
        assertFalse(getModel().getTopDeck().getDeckList().contains(VALID_NAME_DECK_A));
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list------------------------------ */
        /* Case: filters the Deck list before adding -> added */
        showDecksWithName(KEYWORD_MATCHING_JOHN, decksView);
        assertCommandSuccess(DECK_I);

        /* ----------------------------------- Perform invalid add operations--------------------------------------- */

        /* Case: add a duplicate Deck -> rejected */
        command = AddDeckCommand.COMMAND_WORD + "  " + PREFIX_NAME + VALID_NAME_DECK_B;
        assertCommandFailure(command, getModel(), AddDeckCommand.MESSAGE_DUPLICATE_DECK);

        /* Case: missing name -> rejected */
        command = AddDeckCommand.COMMAND_WORD;
        assertCommandFailure(command, getModel(), "Invalid command format! \n" + AddDeckCommand.MESSAGE_USAGE);

        /* Case: invalid keyword -> rejected */
        command = "newdecks " + VALID_DECK_NAME_B_ARGS;
        assertCommandFailure(command, getModel(), Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddDeckCommand.COMMAND_WORD + INVALID_DECK_NAME_ARGS;
        assertCommandFailure(command, getModel(), Name.MESSAGE_NAME);
    }

    /**
     * Executes the {@code AddDeckCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddDeckCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code DeckListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see TopDeckSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Deck toAdd) {
        assertCommandSuccess(DeckUtil.getAddDeckCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Deck)}. Executes {@code command}
     * instead.
     *
     * @see AddDeckCommandSystemTest#assertCommandSuccess(Deck)
     */
    private void assertCommandSuccess(String command, Deck toAdd) {
        Model expectedModel = getModel();
        expectedModel.addDeck(toAdd);
        String expectedResultMessage = String.format(AddDeckCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Deck)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code DeckListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddDeckCommandSystemTest#assertCommandSuccess(String, Deck)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
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

