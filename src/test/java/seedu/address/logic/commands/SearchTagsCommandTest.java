package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlaces.ALICE;
import static seedu.address.testutil.TypicalPlaces.BENSON;
import static seedu.address.testutil.TypicalPlaces.ELLE;
import static seedu.address.testutil.TypicalPlaces.GEORGE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchTagsCommand}.
 */
public class SearchTagsCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchTagsCommand command = new SearchTagsCommand(predicate);
        expectedModel.updateFilteredPlaceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPlaceList());
    }

    @Test
    public void execute_multipleKeywords_multiplePlacesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        TagContainsKeywordsPredicate predicate = preparePredicate("shoppingMall temple school");
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
}
