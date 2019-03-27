package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_HEALTHWORKER_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORGANIZATION_ANDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BETTY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.BETTY;
import static seedu.address.testutil.TypicalHealthWorkers.ELLA;
import static seedu.address.testutil.TypicalHealthWorkers.PANIEL;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.tag.Specialisation;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterHealthWorkerCommand}.
 */
public class FilterHealthWorkerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        List<Predicate> firstList = Arrays.asList(x -> ((HealthWorker) x).getName().equals(VALID_NAME_ANDY));
        List<Predicate> secondList = Arrays.asList(x -> ((HealthWorker) x).getName().equals(VALID_PHONE_BETTY));
        FilterHealthWorkerCommand firstCommand = new FilterHealthWorkerCommand(firstList);
        FilterHealthWorkerCommand secondCommand = new FilterHealthWorkerCommand(secondList);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> return true
        FilterHealthWorkerCommand firstCommandCopy = new FilterHealthWorkerCommand(firstList);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different predicates -> return false
        assertFalse(firstCommand.equals(secondCommand));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(5));
    }

    @Test
    public void reducePredicates() {
        Predicate<HealthWorker> firstPredicate = x -> x.getName().contains(VALID_NAME_ANDY);
        Predicate<HealthWorker> secondPredicate = x -> x.hasSkill(Specialisation.GENERAL_PRACTICE);

        // combine multiple predicates
        expectedModel.updateFilteredHealthWorkerList(FilterHealthWorkerCommand
                .reducePredicates(Arrays.asList(firstPredicate, secondPredicate)));
        assertEquals(Arrays.asList(ANDY), expectedModel.getFilteredHealthWorkerList());

        Predicate<HealthWorker> thirdPredicate = x -> x.getOrganization().contains(VALID_ORGANIZATION_ANDY);
        expectedModel.updateFilteredHealthWorkerList(FilterHealthWorkerCommand
                .reducePredicates(Arrays.asList(firstPredicate, secondPredicate, thirdPredicate)));
        assertEquals(Arrays.asList(ANDY), expectedModel.getFilteredHealthWorkerList());
    }

    @Test
    public void execute_singleParameter() {
        String expectedMessage = String.format(MESSAGE_HEALTHWORKER_LISTED_OVERVIEW, 3);
        Predicate<HealthWorker> predicate = x -> x.hasSkill(Specialisation.PHYSIOTHERAPY);
        FilterHealthWorkerCommand command = new FilterHealthWorkerCommand(Arrays.asList(predicate));
        expectedModel.updateFilteredHealthWorkerList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ANDY, PANIEL, ELLA), model.getFilteredHealthWorkerList());

        expectedMessage = String.format(MESSAGE_HEALTHWORKER_LISTED_OVERVIEW, 3);
        predicate = x -> x.getName().contains("Me");
        command = new FilterHealthWorkerCommand(Arrays.asList(predicate));
        expectedModel.updateFilteredHealthWorkerList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BETTY, PANIEL, ELLA), model.getFilteredHealthWorkerList());
    }

    @Test
    public void execute_multipleParameters() {
        String expectedMessage = String.format(MESSAGE_HEALTHWORKER_LISTED_OVERVIEW, 2);
        Predicate<HealthWorker> firstPredicate = x -> x.hasSkill(Specialisation.PHYSIOTHERAPY);
        Predicate<HealthWorker> secondPredicate = x -> x.getName().contains("Me");
        FilterHealthWorkerCommand command = new FilterHealthWorkerCommand(Arrays
                .asList(firstPredicate, secondPredicate));
        expectedModel.updateFilteredHealthWorkerList(FilterHealthWorkerCommand
                .reducePredicates(Arrays.asList(firstPredicate, secondPredicate)));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(PANIEL, ELLA), model.getFilteredHealthWorkerList());
    }
}
