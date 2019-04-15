package seedu.address.logic.commands;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILTERNAME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.commandExecute;
import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_CANOT_FOUND_TARGET_FILTER;
import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_LACK_LISTNAME;
import static seedu.address.logic.commands.DeleteFilterCommand.MESSAGE_USAGE_DETAIL_SCREEN;
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
 * {@code DeleteFilterCommand}.
 */
public class DeleteFilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validFilterNameAllPersons_success() {
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, EMPTY, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(EMPTY, VALID_FILTERNAME);

        String expectedMessage = String.format(DeleteFilterCommand.MESSAGE_DELETE_FILTER_SUCCESS, VALID_FILTERNAME);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.removePredicate(VALID_FILTERNAME, EMPTY);

        assertCommandSuccess(deleteFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNameApplicant_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, APPLICANT, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(APPLICANT, VALID_FILTERNAME);

        String expectedMessage = String.format(DeleteFilterCommand.MESSAGE_DELETE_FILTER_SUCCESS, VALID_FILTERNAME);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.removePredicate(VALID_FILTERNAME, EMPTY);

        assertCommandSuccess(deleteFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNameInterview_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, INTERVIEW, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(INTERVIEW, VALID_FILTERNAME);

        String expectedMessage = String.format(DeleteFilterCommand.MESSAGE_DELETE_FILTER_SUCCESS, VALID_FILTERNAME);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.removePredicate(VALID_FILTERNAME, EMPTY);

        assertCommandSuccess(deleteFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNameShortlist_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, SHORTLIST, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(SHORTLIST, VALID_FILTERNAME);

        String expectedMessage = String.format(DeleteFilterCommand.MESSAGE_DELETE_FILTER_SUCCESS, VALID_FILTERNAME);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.removePredicate(VALID_FILTERNAME, EMPTY);

        assertCommandSuccess(deleteFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validFilterNamKiv_success() {
        model.setIsAllJobScreen(false);
        FilterCommand.PredicatePersonDescriptor descriptor = preparePredicatePersonDescriptor(" ");
        FilterCommand filterCommand = new FilterCommand(VALID_FILTERNAME, KIV, descriptor);
        Predicate<Person> predicator = descriptor.toPredicate();
        commandExecute(filterCommand, model, commandHistory);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(KIV, VALID_FILTERNAME);

        String expectedMessage = String.format(DeleteFilterCommand.MESSAGE_DELETE_FILTER_SUCCESS, VALID_FILTERNAME);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setIsAllJobScreen(false);
        expectedModel.addPredicate(VALID_FILTERNAME, predicator, EMPTY);
        expectedModel.removePredicate(VALID_FILTERNAME, EMPTY);

        assertCommandSuccess(deleteFilterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidListName_fail() {
        model.setIsAllJobScreen(false);
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(EMPTY, VALID_FILTERNAME);
        String expectedMessage = String.format(MESSAGE_LACK_LISTNAME, MESSAGE_USAGE_DETAIL_SCREEN);
        assertCommandFailure(deleteFilterCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_filterNameNotFoundAllJob_fail() {
        DeleteFilterCommand deleteFilterCommand = new DeleteFilterCommand(EMPTY, VALID_FILTERNAME);
        String expectedMessage = MESSAGE_CANOT_FOUND_TARGET_FILTER;
        assertCommandFailure(deleteFilterCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_filterNameNotFoundDetailJob_fail() {
        model.setIsAllJobScreen(false);
        DeleteFilterCommand deleteFilterCommandApplicant = new DeleteFilterCommand(APPLICANT, VALID_FILTERNAME);
        DeleteFilterCommand deleteFilterCommandInterview = new DeleteFilterCommand(INTERVIEW, VALID_FILTERNAME);
        DeleteFilterCommand deleteFilterCommandKiv = new DeleteFilterCommand(KIV, VALID_FILTERNAME);
        DeleteFilterCommand deleteFilterCommandShortList = new DeleteFilterCommand(SHORTLIST, VALID_FILTERNAME);
        String expectedMessage = MESSAGE_CANOT_FOUND_TARGET_FILTER;
        assertCommandFailure(deleteFilterCommandApplicant, model, commandHistory, expectedMessage);
        assertCommandFailure(deleteFilterCommandInterview, model, commandHistory, expectedMessage);
        assertCommandFailure(deleteFilterCommandKiv, model, commandHistory, expectedMessage);
        assertCommandFailure(deleteFilterCommandShortList, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteFilterCommand deleteFilterFirstCommand = new DeleteFilterCommand(APPLICANT, "firstName");
        DeleteFilterCommand deleteFilterSecondCommand = new DeleteFilterCommand(APPLICANT, "secondName");
        DeleteFilterCommand deleteFilterThirdCommand = new DeleteFilterCommand(INTERVIEW, "firstName");

        // same object -> returns true
        assertEquals(deleteFilterFirstCommand, deleteFilterFirstCommand);

        // same values -> returns true
        DeleteFilterCommand deleteFilterFirstCommandCopy = new DeleteFilterCommand(APPLICANT, "firstName");
        assertEquals(deleteFilterFirstCommand, deleteFilterFirstCommandCopy);

        // different targetname -> returns false
        assertNotEquals(deleteFilterFirstCommand, deleteFilterSecondCommand);

        // different list name -> returns false
        assertNotEquals(deleteFilterFirstCommand, deleteFilterThirdCommand);

        // null -> returns false
        assertNotEquals(deleteFilterFirstCommand, null);

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
