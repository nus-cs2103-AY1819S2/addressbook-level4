package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalObjects.CARL;
import static seedu.address.testutil.TypicalObjects.ELLE;
import static seedu.address.testutil.TypicalObjects.FIONA;
import static seedu.address.testutil.TypicalObjects.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.JobListName;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;


/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        FilterCommand.PredicatePersonDescriptor firstDescriptor = preparePredicatePersonDescriptor("first");
        FilterCommand firstCommand = new FilterCommand("", JobListName.EMPTY, firstDescriptor);
        FilterCommand.PredicatePersonDescriptor secondDescriptor =
            preparePredicatePersonDescriptor("second");
        FilterCommand secondCommand = new FilterCommand("", JobListName.EMPTY, secondDescriptor);
        NameContainsKeywordsPredicate findPredicate =
            new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        FindCommand findCommand = new FindCommand(findPredicate);

        // same object -> returns true
        assertEquals(firstCommand,firstCommand);

        // same values -> returns true
        FilterCommand firstCommandCopy = new FilterCommand("", JobListName.EMPTY, firstDescriptor);
        assertEquals(firstCommand,firstCommandCopy);

        // different types -> returns false
        assertNotEquals(firstCommand,1);

        // different person -> returns false
        assertNotEquals(firstCommand,secondCommand);

        // different command type -> returns false
        assertNotEquals(firstCommand,findCommand);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, JobListName.EMPTY, descriptor);

        assertCommandFailure(filterCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_emptyKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, JobListName.EMPTY, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.addPredicateAllPersons(VALID_FILTERNAME, predicator);
        expectedModel.updateFilteredPersonList();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FilterCommand.PredicatePersonDescriptor descriptor =
            preparePredicatePersonDescriptor("Kurz Elle Kunz");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, JobListName.EMPTY, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.updateBaseFilteredPersonList(predicator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private FilterCommand.PredicatePersonDescriptor preparePredicatePersonDescriptor(String userInput) {
        FilterCommand.PredicatePersonDescriptor descriptor = new FilterCommand.PredicatePersonDescriptor();
        descriptor.setName(new HashSet<>(Arrays.asList(userInput.split("\\s+"))));
        return descriptor;
    }
}
