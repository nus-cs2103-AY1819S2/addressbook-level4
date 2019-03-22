package seedu.travel.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.commons.core.Messages.MESSAGE_PLACES_LISTED_OVERVIEW;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BENSON;
import static seedu.travel.testutil.TypicalPlaces.CARL;
import static seedu.travel.testutil.TypicalPlaces.ELLE;
import static seedu.travel.testutil.TypicalPlaces.GEORGE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;
import seedu.travel.model.place.RatingContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchRatingCommand}.
 */
public class SearchRatingCommandTest {
    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        RatingContainsKeywordsPredicate firstPredicate =
                new RatingContainsKeywordsPredicate(Collections.singletonList("1"));
        RatingContainsKeywordsPredicate secondPredicate =
                new RatingContainsKeywordsPredicate(Collections.singletonList("2"));

        SearchRatingCommand searchRatingFirstCommand = new SearchRatingCommand(firstPredicate);
        SearchRatingCommand searchRatingSecondCommand = new SearchRatingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchRatingFirstCommand.equals(searchRatingFirstCommand));

        // same values -> returns true
        SearchRatingCommand searchRatingFirstCommandCopy = new SearchRatingCommand(firstPredicate);
        assertTrue(searchRatingFirstCommand.equals(searchRatingFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchRatingFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchRatingFirstCommand.equals(null));

        // different place -> returns false
        assertFalse(searchRatingFirstCommand.equals(searchRatingSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPlaceFound() {
        String expectedMessage = String.format(MESSAGE_PLACES_LISTED_OVERVIEW, 0);
        RatingContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchRatingCommand command = new SearchRatingCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPlaceList());
    }

    @Test
    public void execute_multipleKeywords_multiplePlacesFound() {
        String expectedMessage = String.format(MESSAGE_PLACES_LISTED_OVERVIEW, 5);
        RatingContainsKeywordsPredicate predicate = preparePredicate("1 4 5");
        SearchRatingCommand command = new SearchRatingCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, ELLE, GEORGE), model.getFilteredPlaceList());
    }

    /**
     * Parses {@code userInput} into a {@code RatingContainsKeywordsPredicate}.
     */
    private RatingContainsKeywordsPredicate preparePredicate(String userInput) {
        return new RatingContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
