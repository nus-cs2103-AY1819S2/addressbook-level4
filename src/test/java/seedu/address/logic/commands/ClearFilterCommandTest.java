package seedu.address.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.ClearFilterCommand.MESSAGE_LACK_LISTNAME;
import static seedu.address.logic.commands.ClearFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.commandExecute;
import static seedu.address.model.job.JobListName.APPLICANT;
import static seedu.address.model.job.JobListName.EMPTY;
import static seedu.address.model.job.JobListName.INTERVIEW;
import static seedu.address.model.job.JobListName.KIV;
import static seedu.address.model.job.JobListName.SHORTLIST;
import static seedu.address.testutil.TypicalObjects.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ClearFilterCommand}.
 */
public class ClearFilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validFilterNameAllPersons_success() {
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, EMPTY, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        ClearFilterCommand clearFilterCommand = new ClearFilterCommand(EMPTY);

        String expectedMessage = String.format(ClearFilterCommand.MESSAGE_CLEAR_FILTER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.clearJobFilteredLists(EMPTY);

        assertCommandSuccess(clearFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNameApplicant_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, APPLICANT, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        ClearFilterCommand clearFilterCommand = new ClearFilterCommand(APPLICANT);

        String expectedMessage = String.format(ClearFilterCommand.MESSAGE_CLEAR_FILTER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, APPLICANT);
        expectedModel.clearJobFilteredLists(APPLICANT);

        assertCommandSuccess(clearFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNameInterview_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, INTERVIEW, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        ClearFilterCommand clearFilterCommand = new ClearFilterCommand(INTERVIEW);

        String expectedMessage = String.format(ClearFilterCommand.MESSAGE_CLEAR_FILTER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.clearJobFilteredLists(EMPTY);

        assertCommandSuccess(clearFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNameShortlist_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, SHORTLIST, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        ClearFilterCommand clearFilterCommand = new ClearFilterCommand(SHORTLIST);

        String expectedMessage = String.format(ClearFilterCommand.MESSAGE_CLEAR_FILTER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, SHORTLIST);
        expectedModel.clearJobFilteredLists(SHORTLIST);

        assertCommandSuccess(clearFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNamKiv_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, KIV, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        ClearFilterCommand clearFilterCommand = new ClearFilterCommand(KIV);

        String expectedMessage = String.format(ClearFilterCommand.MESSAGE_CLEAR_FILTER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, KIV);
        expectedModel.clearJobFilteredLists(KIV);

        assertCommandSuccess(clearFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidListName_fail() {
        model.setIsAllJobScreen(false);
        ClearFilterCommand clearFilterCommand = new ClearFilterCommand(EMPTY);
        String expectedMessage = String.format(MESSAGE_LACK_LISTNAME, MESSAGE_USAGE_DETAIL_SCREEN);
        assertCommandFailure(clearFilterCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        ClearFilterCommand clearFilterFirstCommand = new ClearFilterCommand(APPLICANT);
        ClearFilterCommand clearFilterSecondCommand = new ClearFilterCommand(INTERVIEW);

        // same object -> returns true
        assertEquals(clearFilterFirstCommand, clearFilterFirstCommand);

        // same values -> returns true
        ClearFilterCommand clearFilterFirstCommandCopy = new ClearFilterCommand(APPLICANT);
        assertEquals(clearFilterFirstCommand, clearFilterFirstCommandCopy);

        // different targetname -> returns false
        assertNotEquals(clearFilterFirstCommand, clearFilterSecondCommand);

        // null -> returns false
        assertNotEquals(clearFilterFirstCommand, null);

    }

    /**
     * Parses {@code userInput} into a {@code Descriptor}.
     */
    private FilterCommand.PredicatePersonDescriptor preparePredicatePersonDescriptor(String userInput) {
        requireAllNonNull(userInput);
        FilterCommand.PredicatePersonDescriptor descriptor = new FilterCommand.PredicatePersonDescriptor();
        descriptor.setName(new HashSet<>(Arrays.asList(userInput.split("\\s+"))));
        return descriptor;
    }
}
