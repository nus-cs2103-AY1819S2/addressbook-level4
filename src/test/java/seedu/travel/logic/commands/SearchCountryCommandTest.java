package seedu.travel.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.BENSON;
import static seedu.travel.testutil.TypicalPlaces.CARL;
import static seedu.travel.testutil.TypicalPlaces.DANIEL;
import static seedu.travel.testutil.TypicalPlaces.ELLE;
import static seedu.travel.testutil.TypicalPlaces.FIONA;
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
import seedu.travel.model.place.CountryCodeContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCountryCommand}.
 */
public class SearchCountryCommandTest {
    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        CountryCodeContainsKeywordsPredicate firstPredicate =
                new CountryCodeContainsKeywordsPredicate(Collections.singletonList("SGP"));
        CountryCodeContainsKeywordsPredicate secondPredicate =
                new CountryCodeContainsKeywordsPredicate(Collections.singletonList("JPN"));

        SearchCountryCommand searchCountryFirstCommand = new SearchCountryCommand(firstPredicate);
        SearchCountryCommand searchCountrySecondCommand = new SearchCountryCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchCountryFirstCommand.equals(searchCountryFirstCommand));

        // same values -> returns true
        SearchCountryCommand searchCountryFirstCommandCopy = new SearchCountryCommand(firstPredicate);
        assertTrue(searchCountryFirstCommand.equals(searchCountryFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchCountryFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchCountryFirstCommand.equals(null));

        // different place -> returns false
        assertFalse(searchCountryFirstCommand.equals(searchCountrySecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPlaceFound() {
        CountryCodeContainsKeywordsPredicate predicate = preparePredicate(" ");
        String expectedMessage = constructExpectedMessage(predicate, 0);
        SearchCountryCommand command = new SearchCountryCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPlaceList());
    }

    @Test
    public void execute_multipleKeywords_multiplePlacesFound() {
        CountryCodeContainsKeywordsPredicate predicate = preparePredicate("SGP JPN");
        String expectedMessage = constructExpectedMessage(predicate, 7);
        SearchCountryCommand command = new SearchCountryCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPlaceList());
    }

    /**
     * Parses {@code userInput} into a {@code CountryCodeContainsKeywordsPredicate}.
     */
    private CountryCodeContainsKeywordsPredicate preparePredicate(String userInput) {
        return new CountryCodeContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Constructs the expected message from running SearchCountryCommand
     * @param predicate the keywords used in the query
     * @param expectedNumberOfPlaces expected number of places that will be returned
     * @return A string that contains the expected message upon running SearchCountryCommand
     */
    private String constructExpectedMessage(CountryCodeContainsKeywordsPredicate predicate,
                                            int expectedNumberOfPlaces) {
        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(SearchCountryCommand.COMMAND_WORD);
        expectedMessage.append(" ");
        expectedMessage.append(predicate.getKeywords());
        expectedMessage.append(": ");
        expectedMessage.append(String.format(Messages.MESSAGE_PLACES_LISTED_OVERVIEW,
                expectedNumberOfPlaces));
        return expectedMessage.toString();
    }
}
