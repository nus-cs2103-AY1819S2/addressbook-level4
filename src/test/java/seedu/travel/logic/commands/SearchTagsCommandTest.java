package seedu.travel.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BENSON;
import static seedu.travel.testutil.TypicalPlaces.ELLE;
import static seedu.travel.testutil.TypicalPlaces.GEORGE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.travel.commons.core.Messages;
import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;
import seedu.travel.model.place.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchTagsCommand}.
 */
public class SearchTagsCommandTest {
    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("first"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchTagsCommand searchFirstTagCommand = new SearchTagsCommand(firstPredicate);
        SearchTagsCommand searchSecondTagCommand = new SearchTagsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstTagCommand.equals(searchFirstTagCommand));

        // same values -> returns true
        SearchTagsCommand searchFirstTagCommandCopy = new SearchTagsCommand(firstPredicate);
        assertTrue(searchFirstTagCommand.equals(searchFirstTagCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstTagCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstTagCommand.equals(null));

        // different place -> returns false
        assertFalse(searchFirstTagCommand.equals(searchSecondTagCommand));
    }

    @Test
    public void execute_zeroKeywords_noPlaceFound() {
        TagContainsKeywordsPredicate predicate = preparePredicate(" ");
        String expectedMessage = constructExpectedMessage(predicate, 0);
        SearchTagsCommand command = new SearchTagsCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPlaceList());
    }

    @Test
    public void execute_multipleKeywords_multiplePlacesFound() {
        TagContainsKeywordsPredicate predicate = preparePredicate("shoppingMall temple school");
        String expectedMessage = constructExpectedMessage(predicate, 4);
        SearchTagsCommand command = new SearchTagsCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, ELLE, GEORGE), model.getFilteredPlaceList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate preparePredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Constructs the expected message from running SearchTagsCommand
     * @param predicate the keywords used in the query
     * @param expectedNumberOfPlaces expected number of places that will be returned
     * @return A string that contains the expected message upon running SearchTagsCommand
     */
    private String constructExpectedMessage(TagContainsKeywordsPredicate predicate,
                                            int expectedNumberOfPlaces) {
        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(SearchTagsCommand.COMMAND_WORD);
        expectedMessage.append(" ");
        expectedMessage.append(predicate.getKeywords());
        expectedMessage.append(": ");
        expectedMessage.append(String.format(Messages.MESSAGE_PLACES_LISTED_OVERVIEW,
                expectedNumberOfPlaces));
        return expectedMessage.toString();
    }
}
