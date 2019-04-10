package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
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
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FilterCommand firstCommandCopy = new FilterCommand("", JobListName.EMPTY, firstDescriptor);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // different person -> returns false
        assertFalse(firstCommand.equals(secondCommand));

        // different command type -> returns false
        assertFalse(firstCommand.equals(findCommand));
    }

    //    @Test
    //    @SuppressWarnings("unchecked")
    //    public void execute_zeroKeywords_noPersonFound() {
    //        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
    //        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
    //        FilterCommand command = new FilterCommand(JobListName.APPLICANT, descriptor);
    //        Predicate<Person> predicator = (Predicate<Person>) descriptor.toPredicate();
    //        expectedModel.updateJobAllApplicantsFilteredPersonList(predicator);
    //        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    //        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    //    }

    @Test
    @SuppressWarnings("unchecked")
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FilterCommand.PredicatePersonDescriptor descriptor =
            preparePredicatePersonDescriptor("Kurz Elle Kunz");
        FilterCommand command = new FilterCommand("", JobListName.EMPTY, descriptor);
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
