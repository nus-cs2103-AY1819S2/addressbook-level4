package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithPerson;


/**
 * Contains integration tests (interaction with the Model) for {@code MemberFilterCommand}.
 */
/*
public class MemberMemberFilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithPerson(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        RideContainsConditionPredicate firstPredicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate("<",
                        new Maintenance("100"))));
        RideContainsConditionPredicate secondPredicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate("<=",
                        new WaitTime("100"))));

        MemberFilterCommand filterFirstCommand = new MemberFilterCommand(firstPredicate);
        MemberFilterCommand filterSecondCommand = new MemberFilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> return true
        MemberFilterCommand filterFirstCommandCopy = new MemberFilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroPredicates_noRideFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 0);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(List.of(new AttributePredicate(" ", new Maintenance("0"))));
        MemberFilterCommand command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());

        predicate = new RideContainsConditionPredicate(List.of(new AttributePredicate(" ", new WaitTime("0"))));
        command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());
    }

    @Test
    public void execute_singlePredicate_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 3);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(Arrays.asList(prepareMaintenancePredicate(">=", "15")));
        MemberFilterCommand command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIG, DUMBO, GALAXY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 5);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate("<", "15")));
        command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACCELERATOR, CASTLE, DUMBO, FANTASY, GALAXY), model.getFilteredRideList());
    }

    @Test
    public void execute_multiplePredicates_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        RideContainsConditionPredicate predicate =
                new RideContainsConditionPredicate(Arrays.asList(prepareMaintenancePredicate(">", "15"),
                        prepareMaintenancePredicate("<=", "50")));
        MemberFilterCommand command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BIG, GALAXY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate("<=", "15"),
                prepareWaitTimePredicate(">", "5")));
        command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CASTLE, FANTASY), model.getFilteredRideList());

        expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 2);
        predicate = new RideContainsConditionPredicate(Arrays.asList(prepareWaitTimePredicate("<=", "15"),
                prepareMaintenancePredicate(">=", "15")));
        command = new MemberFilterCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DUMBO, GALAXY), model.getFilteredRideList());
    }

    private AttributePredicate prepareMaintenancePredicate(String s, String value) {
        return new AttributePredicate(s, new Maintenance(value));
    }

    private AttributePredicate prepareWaitTimePredicate(String s, String value) {
        return new AttributePredicate(s, new WaitTime(value));
    }
} */