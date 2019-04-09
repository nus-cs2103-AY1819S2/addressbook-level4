package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DecksView;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;
import seedu.address.testutil.TypicalDecks;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDeckCommand}.
 */
public class FindDeckCommandTest {
    private Model model = new ModelManager(TypicalDecks.getTypicalTopDeck(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalDecks.getTypicalTopDeck(), new UserPrefs());
    private DecksView decksView;
    private DecksView expectedDecksView;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void initialise() {
        Assert.assertTrue(model.isAtDecksView());
        decksView = (DecksView) model.getViewState();
        expectedDecksView = (DecksView) expectedModel.getViewState();
        decksView.updateFilteredList(Model.PREDICATE_SHOW_ALL_DECKS);
        expectedDecksView.updateFilteredList(Model.PREDICATE_SHOW_ALL_DECKS);
    }

    @Test
    public void equals() {
        DeckNameContainsKeywordsPredicate firstDeckPredicate =
                new DeckNameContainsKeywordsPredicate(Collections.singletonList("firstDeck"));
        DeckNameContainsKeywordsPredicate secondDeckPredicate =
                new DeckNameContainsKeywordsPredicate(Collections.singletonList("secondDeck"));

        FindDeckCommand findFirstDeckCommand = new FindDeckCommand(decksView, firstDeckPredicate);
        FindDeckCommand findSecondDeckCommand = new FindDeckCommand(decksView, secondDeckPredicate);

        // same object -> returns true
        assertTrue(findFirstDeckCommand.equals(findFirstDeckCommand));

        // same values -> returns true
        FindDeckCommand findFirstDeckCommandCopy = new FindDeckCommand(decksView, firstDeckPredicate);
        assertTrue(findFirstDeckCommand.equals(findFirstDeckCommandCopy));

        // different types -> returns false
        assertFalse(findFirstDeckCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstDeckCommand.equals(null));

        // different deck -> returns false
        assertFalse(findFirstDeckCommand.equals(findSecondDeckCommand));
    }

    /**TODO
    @Test
    public void executeZeroKeywordsNoDeckFound() {
        String expectedMessage = String.format(MESSAGE_DECKS_LISTED_OVERVIEW, 0);
        DeckNameContainsKeywordsPredicate deckPredicate = prepareDeckPredicate(" ");
        FindDeckCommand command = new FindDeckCommand(decksView, deckPredicate);
        expectedDecksView.updateFilteredList(deckPredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredList());
    }**/

    /**TODO
    @Test
    public void executeMultipleKeywordsMultipleDecksFound() {
        String expectedMessage = String.format(MESSAGE_DECKS_LISTED_OVERVIEW, 3);
        DeckNameContainsKeywordsPredicate deckPredicate = prepareDeckPredicate("baby is back");
        FindDeckCommand command = new FindDeckCommand(decksView, deckPredicate);
        expectedModel.updateFilteredList(deckPredicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(THERE, NOTHING, HOLDING), model.getFilteredList());
    }**/

    /**
     * Parses {@code userInput} into a {@code DeckNameContainsKeywordsPredicate}.
     */
    private DeckNameContainsKeywordsPredicate prepareDeckPredicate(String userInput) {
        return new DeckNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
