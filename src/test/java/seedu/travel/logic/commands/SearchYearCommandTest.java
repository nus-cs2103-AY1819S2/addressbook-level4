package seedu.travel.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BENSON;
import static seedu.travel.testutil.TypicalPlaces.CARL;
import static seedu.travel.testutil.TypicalPlaces.DANIEL;
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
import seedu.travel.model.place.YearContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchYearCommand}.
 */
public class SearchYearCommandTest {
    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        YearContainsKeywordsPredicate firstPredicate =
                new YearContainsKeywordsPredicate(Collections.singletonList("2018"));
        YearContainsKeywordsPredicate secondPredicate =
                new YearContainsKeywordsPredicate(Collections.singletonList("2013"));

        SearchYearCommand searchYearFirstCommand = new SearchYearCommand(firstPredicate);
        SearchYearCommand searchYearSecondCommand = new SearchYearCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchYearFirstCommand.equals(searchYearFirstCommand));

        // same values -> returns true
        SearchYearCommand searchYearFirstCommandCopy = new SearchYearCommand(firstPredicate);
        assertTrue(searchYearFirstCommand.equals(searchYearFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchYearFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchYearFirstCommand.equals(null));

        // different place -> returns false
        assertFalse(searchYearFirstCommand.equals(searchYearSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPlaceFound() {
        YearContainsKeywordsPredicate predicate = preparePredicate(" ");
        String expectedMessage = constructExpectedMessage(predicate, 0);
        SearchYearCommand command = new SearchYearCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPlaceList());
    }

    @Test
    public void execute_multipleKeywords_multiplePlacesFound() {
        YearContainsKeywordsPredicate predicate = preparePredicate("2016 2017");
        String expectedMessage = constructExpectedMessage(predicate, 5);
        SearchYearCommand command = new SearchYearCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, GEORGE), model.getFilteredPlaceList());
    }

    /**
     * Parses {@code userInput} into a {@code YearContainsKeywordsPredicate}.
     */
    private YearContainsKeywordsPredicate preparePredicate(String userInput) {
        return new YearContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Constructs the expected message from running SearchYearCommand
     * @param predicate the keywords used in the query
     * @param expectedNumberOfPlaces expected number of places that will be returned
     * @return A string that contains the expected message upon running SearchYearCommand
     */
    private String constructExpectedMessage(YearContainsKeywordsPredicate predicate,
                                            int expectedNumberOfPlaces) {
        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(SearchYearCommand.COMMAND_WORD);
        expectedMessage.append(" ");
        expectedMessage.append(predicate.getKeywords());
        expectedMessage.append(": ");
        expectedMessage.append(String.format(Messages.MESSAGE_PLACES_LISTED_OVERVIEW,
                expectedNumberOfPlaces));
        return expectedMessage.toString();
    }
}
