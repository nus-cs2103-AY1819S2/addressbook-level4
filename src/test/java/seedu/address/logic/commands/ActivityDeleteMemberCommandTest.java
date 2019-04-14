package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showActivityAtIndex;

import static seedu.address.testutil.TypicalActivities.getTypicalAddressBookWithActivities;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.MatricNumber;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ActivityDeleteMemberCommand}.
 */
public class ActivityDeleteMemberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithActivities(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    /**
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person validPerson = new PersonBuilder().build();
         model.addPerson(validPerson);
         Activity selectedActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());

         ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
         Activity copyActivity = Activity.addMemberToActivity(selectedActivity, validPerson.getMatricNumber());
         expectedModel.setActivity(selectedActivity, copyActivity);
         expectedModel.commitAddressBook();

         ActivityDeleteMemberCommand activityDeleteMemberCommand =
         new ActivityDeleteMemberCommand(INDEX_FIRST_ACTIVITY, validPerson.getMatricNumber());

         String expectedMessage = String.format(
         ActivityDeleteMemberCommand.MESSAGE_ACTIVITY_DELETE_MEMBER_SUCCESS, 1);

         Activity deleteMemberActivity = Activity.removeMemberFromActivity(copyActivity, validPerson.getMatricNumber());
         expectedModel.setActivity(copyActivity, deleteMemberActivity);
         expectedModel.commitAddressBook();


         assertCommandSuccess(activityDeleteMemberCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        Person validPerson = new PersonBuilder().build();
        ActivityDeleteMemberCommand activityDeleteMemberCommand =
                new ActivityDeleteMemberCommand(outOfBoundIndex, validPerson.getMatricNumber());

        assertCommandFailure(activityDeleteMemberCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    /**
    @Test
    public void execute_validIndexFilteredList_success() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        Activity selectedActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Activity copyActivity = Activity.addMemberToActivity(selectedActivity, validPerson.getMatricNumber());
        expectedModel.setActivity(selectedActivity, copyActivity);
        expectedModel.commitAddressBook();

        ActivityDeleteMemberCommand activityDeleteMemberCommand =
                new ActivityDeleteMemberCommand(INDEX_FIRST_ACTIVITY, validPerson.getMatricNumber());

        String expectedMessage = String.format(
                ActivityDeleteMemberCommand.MESSAGE_ACTIVITY_DELETE_MEMBER_SUCCESS, 1);

        Activity deleteMemberActivity = Activity.removeMemberFromActivity(copyActivity, validPerson.getMatricNumber());
        expectedModel.setActivity(copyActivity, deleteMemberActivity);
        expectedModel.commitAddressBook();

        assertCommandSuccess(activityDeleteMemberCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    */
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);
        Person validPerson = new PersonBuilder().build();

        Index outOfBoundIndex = INDEX_SECOND_ACTIVITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getActivityList().size());

        ActivityDeleteMemberCommand activityDeleteMemberCommand =
                new ActivityDeleteMemberCommand(outOfBoundIndex, validPerson.getMatricNumber());

        assertCommandFailure(activityDeleteMemberCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Person validPerson = new PersonBuilder().build();
        MatricNumber matricNumber = validPerson.getMatricNumber();
        ActivityDeleteMemberCommand activityDeleteFromFirstCommand =
                new ActivityDeleteMemberCommand(INDEX_FIRST_ACTIVITY, matricNumber);
        ActivityDeleteMemberCommand activityDeleteFromSecondCommand =
                new ActivityDeleteMemberCommand(INDEX_SECOND_ACTIVITY, matricNumber);

        // same object -> returns true
        assertTrue(activityDeleteFromFirstCommand.equals(activityDeleteFromFirstCommand));

        // same values -> returns true
        ActivityDeleteMemberCommand activityDeleteFromFirstCommandCopy =
                new ActivityDeleteMemberCommand(INDEX_FIRST_ACTIVITY, matricNumber);
        assertTrue(activityDeleteFromFirstCommand.equals(activityDeleteFromFirstCommandCopy));

        // different types -> returns false
        assertFalse(activityDeleteFromFirstCommand.equals(1));

        // null -> returns false
        assertFalse(activityDeleteFromFirstCommand.equals(null));

        // different activity -> returns false
        assertFalse(activityDeleteFromFirstCommand.equals(activityDeleteFromSecondCommand));
    }
}
