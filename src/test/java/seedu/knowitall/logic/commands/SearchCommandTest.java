package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.CARL;
import static seedu.knowitall.testutil.TypicalCards.ELLE;
import static seedu.knowitall.testutil.TypicalCards.FIONA;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.model.card.QuestionContainsKeywordsPredicate;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel = new ModelManager(model.getCardFolders(), new UserPrefs());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
    }

    @Test
    public void equals() {
        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("first"));
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noCardFound() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 0);
        QuestionContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredCard(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCards());
    }

    @Test
    public void execute_multipleKeywords_multipleCardsFound() {
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 3);
        QuestionContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredCard(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredCards());
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
