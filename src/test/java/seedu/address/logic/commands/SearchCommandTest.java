package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PredicateManager;


/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        SearchCommand.PredicatePersonDescriptor firstDescriptor = preparePredicatePersonDescriptor("first");
        SearchCommand firstCommand = new SearchCommand(firstDescriptor);
        SearchCommand.PredicatePersonDescriptor secondDescriptor
            = preparePredicatePersonDescriptor("second");
        SearchCommand secondCommand = new SearchCommand(secondDescriptor);
        NameContainsKeywordsPredicate findPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindCommand findCommand = new FindCommand(findPredicate);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        SearchCommand firstCommandCopy = new SearchCommand(firstDescriptor);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different command type -> returns false
        assertFalse(firstCommand.equals(findCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SearchCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        SearchCommand command = new SearchCommand(descriptor);
        Predicate<Person> predicator = (Predicate<Person>) descriptor.toPredicate();
        expectedModel.updateFilteredPersonList(predicator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        SearchCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(descriptor);
        Predicate<Person> predicator = (Predicate<Person>) descriptor.toPredicate();
        expectedModel.updateFilteredPersonList(predicator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private SearchCommand.PredicatePersonDescriptor preparePredicatePersonDescriptor(String userInput) {
        SearchCommand.PredicatePersonDescriptor descriptor = new SearchCommand.PredicatePersonDescriptor();
        descriptor.setName(new HashSet<>(Arrays.asList(userInput.split("\\s+"))));
        return descriptor;
    }
}
