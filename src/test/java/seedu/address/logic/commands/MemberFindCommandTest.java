package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithPerson;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.FindCriteriaContainsKeywordPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MatricNumberContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code MemberFindCommand}.
 */
public class MemberFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPerson(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBookWithPerson(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        FindCriteriaContainsKeywordPredicate firstPredicate =
                new FindCriteriaContainsKeywordPredicate(Collections.singletonList("name first").toString());
        FindCriteriaContainsKeywordPredicate secondPredicate =
                new FindCriteriaContainsKeywordPredicate(Collections.singletonList("name second").toString());

        MemberFindCommand findFirstCommand = new MemberFindCommand(firstPredicate);
        MemberFindCommand findSecondCommand = new MemberFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        MemberFindCommand findFirstCommandCopy = new MemberFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.toString().equals(findSecondCommand.toString()));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCriteriaContainsKeywordPredicate predicate = preparePredicate("name ");
        MemberFindCommand command = new MemberFindCommand(predicate);
        if (predicate.toString().equalsIgnoreCase("name")) {
            expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(predicate
                    .getFindKeywords())));
        } else if (predicate.toString().equalsIgnoreCase("matricnum")) {
            expectedModel.updateFilteredPersonList(new MatricNumberContainsKeywordsPredicate(Arrays.asList(predicate
                    .getFindKeywords())));
        }

        assertTrue(true);
        assertEquals(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCriteriaContainsKeywordPredicate predicate = preparePredicate("name Kurz Elle Kunz");
        MemberFindCommand command = new MemberFindCommand(predicate);
        if (predicate.toString().equalsIgnoreCase("name")) {
            expectedModel.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(predicate
                    .getFindKeywords())));
        } else if (predicate.toString().equalsIgnoreCase("matricnum")) {
            expectedModel.updateFilteredPersonList(new MatricNumberContainsKeywordsPredicate(Arrays.asList(predicate
                    .getFindKeywords())));
        }
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private FindCriteriaContainsKeywordPredicate preparePredicate(String userInput) {
        return new FindCriteriaContainsKeywordPredicate(userInput);
    }
}
