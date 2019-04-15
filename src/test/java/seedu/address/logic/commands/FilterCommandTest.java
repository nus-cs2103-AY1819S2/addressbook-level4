package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.commandExecute;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_LACK_LISTNAME;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_REDUNDANT_FILTERNAME;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_REDUNDANT_LISTNAME;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_USAGE_ALLJOB_SCREEN;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_USAGE_JOB_DETAIL_SCREEN;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.EMPTY;
import static seedu.address.model.job.JobListName.INTERVIEW;
import static seedu.address.model.job.JobListName.KIV;
import static seedu.address.model.job.JobListName.SHORTLIST;
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
import seedu.address.model.person.Person;


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
        FilterCommand firstCommand = new FilterCommand("", EMPTY, firstDescriptor);
        FilterCommand.PredicatePersonDescriptor secondDescriptor =
            preparePredicatePersonDescriptor("second");
        FilterCommand secondCommand = new FilterCommand("", EMPTY, secondDescriptor);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(EMPTY, VALID_FILTERNAME);

        // same object -> returns true
        assertEquals(firstCommand, firstCommand);

        // same values -> returns true
        FilterCommand firstCommandCopy = new FilterCommand("", EMPTY, firstDescriptor);
        assertEquals(firstCommand, firstCommandCopy);

        // different types -> returns false
        assertNotEquals(firstCommand, 1);

        // different person -> returns false
        assertNotEquals(firstCommand, secondCommand);

        // different command type -> returns false
        assertNotEquals(firstCommand, deleteFilterCommand);
    }

    @Test
    public void execute_redundantListName_failure() {
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, APPLICANT, descriptor);

        assertCommandFailure(filterCommand, model, commandHistory,
            String.format(MESSAGE_REDUNDANT_LISTNAME, MESSAGE_USAGE_ALLJOB_SCREEN));
    }

    @Test
    public void execute_lackListName_failure() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, EMPTY, descriptor);

        assertCommandFailure(filterCommand, model, commandHistory,
            String.format(MESSAGE_LACK_LISTNAME, MESSAGE_USAGE_JOB_DETAIL_SCREEN));
    }

    @Test
    public void execute_repetitiveFilterName_failure() {
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand commandAllPerson = new FilterCommand(VALID_FILTERNAME, EMPTY, descriptor);
        commandExecute(commandAllPerson, model, commandHistory);
        assertCommandFailure(commandAllPerson, model, commandHistory, MESSAGE_REDUNDANT_FILTERNAME);

        model.setIsAllJobScreen(false);
        FilterCommand commandApplicant = new FilterCommand(VALID_FILTERNAME, APPLICANT, descriptor);
        FilterCommand commandKiv = new FilterCommand(VALID_FILTERNAME, KIV, descriptor);
        FilterCommand commandInterview = new FilterCommand(VALID_FILTERNAME, INTERVIEW, descriptor);
        FilterCommand commandShortlist = new FilterCommand(VALID_FILTERNAME, SHORTLIST, descriptor);
        commandExecute(commandApplicant, model, commandHistory);
        commandExecute(commandKiv, model, commandHistory);
        commandExecute(commandInterview, model, commandHistory);
        commandExecute(commandShortlist, model, commandHistory);

        assertCommandFailure(commandApplicant, model, commandHistory, MESSAGE_REDUNDANT_FILTERNAME);
        assertCommandFailure(commandKiv, model, commandHistory, MESSAGE_REDUNDANT_FILTERNAME);
        assertCommandFailure(commandInterview, model, commandHistory, MESSAGE_REDUNDANT_FILTERNAME);
        assertCommandFailure(commandShortlist, model, commandHistory, MESSAGE_REDUNDANT_FILTERNAME);
    }


    @Test
    public void execute_emptyKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, EMPTY, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.updateFilteredPersonLists(EMPTY);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_emptyKeywordsApplicant_noPersonFound() {
        model.setIsAllJobScreen(false);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, APPLICANT, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, APPLICANT);
        expectedModel.updateFilteredPersonLists(APPLICANT);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getJobsList(APPLICANT));
    }

    @Test
    public void execute_emptyKeywordsKiv_noPersonFound() {
        model.setIsAllJobScreen(false);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, KIV, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, KIV);
        expectedModel.updateFilteredPersonLists(KIV);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getJobsList(KIV));
    }

    @Test
    public void execute_emptyKeywordsInterview_noPersonFound() {
        model.setIsAllJobScreen(false);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, INTERVIEW, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, INTERVIEW);
        expectedModel.updateFilteredPersonLists(INTERVIEW);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getJobsList(INTERVIEW));
    }

    @Test
    public void execute_emptyKeywordsShortList_noPersonFound() {
        model.setIsAllJobScreen(false);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, SHORTLIST, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, SHORTLIST);
        expectedModel.updateFilteredPersonLists(SHORTLIST);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getJobsList(SHORTLIST));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FilterCommand.PredicatePersonDescriptor descriptor =
            preparePredicatePersonDescriptor("Kurz Elle Kunz");
        FilterCommand command = new FilterCommand(VALID_FILTERNAME, EMPTY, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        expectedModel.updateBaseFilteredPersonList(predicator);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private FilterCommand.PredicatePersonDescriptor preparePredicatePersonDescriptor(String userInput) {
        requireAllNonNull(userInput);
        FilterCommand.PredicatePersonDescriptor descriptor = new FilterCommand.PredicatePersonDescriptor();
        descriptor.setName(new HashSet<>(Arrays.asList(userInput.split("\\s+"))));
        return descriptor;
    }
}
