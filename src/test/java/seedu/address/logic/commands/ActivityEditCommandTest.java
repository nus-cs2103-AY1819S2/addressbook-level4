package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.A_DESC_HTML;
import static seedu.address.logic.commands.CommandTestUtil.A_DESC_OUTING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_DATETIME_HTML;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_LOCATION_HTML;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_NAME_HTML;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showActivityAtIndex;

import static seedu.address.testutil.TypicalActivities.getTypicalAddressBookWithActivities;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;

import seedu.address.testutil.ActivityBuilder;
import seedu.address.testutil.EditActivityDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for ActivityEditCommand.
 */
public class ActivityEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithActivities(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Activity editedActivity = new ActivityBuilder().build();
        ActivityEditCommand.EditActivityDescriptor descriptor =
                new EditActivityDescriptorBuilder(editedActivity).build();
        ActivityEditCommand editCommand = new ActivityEditCommand(INDEX_FIRST_ACTIVITY, descriptor);

        String expectedMessage = String.format(ActivityEditCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setActivity(model.getFilteredActivityList().get(0), editedActivity);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastActivity = Index.fromOneBased(model.getFilteredActivityList().size());
        Activity lastActivity = model.getFilteredActivityList().get(indexLastActivity.getZeroBased());

        ActivityBuilder activityInList = new ActivityBuilder(lastActivity);
        Activity editedActivity = activityInList.withActivityName(VALID_ACTIVITY_NAME_HTML)
                .withActivityDateTime(VALID_ACTIVITY_DATETIME_HTML)
                .withActivityLocation(VALID_ACTIVITY_LOCATION_HTML).build();

        ActivityEditCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withActivityName(VALID_ACTIVITY_NAME_HTML)
                .withActivityDateTime(VALID_ACTIVITY_DATETIME_HTML)
                .withActivityLocation(VALID_ACTIVITY_LOCATION_HTML).build();

        ActivityEditCommand editCommand = new ActivityEditCommand(indexLastActivity, descriptor);

        String expectedMessage = String.format(ActivityEditCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setActivity(lastActivity, editedActivity);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        ActivityEditCommand editCommand = new ActivityEditCommand(INDEX_FIRST_ACTIVITY,
                new ActivityEditCommand.EditActivityDescriptor());
        Activity editedActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());

        String expectedMessage = String.format(ActivityEditCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);

        Activity activityInFilteredList = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        Activity editedActivity = new ActivityBuilder(activityInFilteredList)
                .withActivityName(VALID_ACTIVITY_NAME_HTML).build();
        ActivityEditCommand editCommand = new ActivityEditCommand(INDEX_FIRST_ACTIVITY,
                new EditActivityDescriptorBuilder().withActivityName(VALID_ACTIVITY_NAME_HTML).build());

        String expectedMessage = String.format(ActivityEditCommand.MESSAGE_EDIT_ACTIVITY_SUCCESS, editedActivity);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setActivity(model.getFilteredActivityList().get(0), editedActivity);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateActivityUnfilteredList_failure() {
        Activity firstActivity = model.getFilteredActivityList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        ActivityEditCommand.EditActivityDescriptor descriptor =
                new EditActivityDescriptorBuilder(firstActivity).build();
        ActivityEditCommand editCommand = new ActivityEditCommand(INDEX_SECOND_ACTIVITY, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, ActivityEditCommand.MESSAGE_DUPLICATE_ACTIVITY);
    }

    @Test
    public void execute_duplicateActivityFilteredList_failure() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);

        // edit activity in filtered list into a duplicate in address book
        Activity activityInList = model.getAddressBook().getActivityList().get(INDEX_SECOND_ACTIVITY.getZeroBased());
        ActivityEditCommand editCommand = new ActivityEditCommand(INDEX_FIRST_ACTIVITY,
                new EditActivityDescriptorBuilder(activityInList).build());

        assertCommandFailure(editCommand, model, commandHistory, ActivityEditCommand.MESSAGE_DUPLICATE_ACTIVITY);
    }

    @Test
    public void execute_invalidActivityIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActivityList().size() + 1);
        ActivityEditCommand.EditActivityDescriptor descriptor = new EditActivityDescriptorBuilder()
                .withActivityName(VALID_ACTIVITY_NAME_HTML).build();
        ActivityEditCommand editCommand = new ActivityEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidActivityIndexFilteredList_failure() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);
        Index outOfBoundIndex = INDEX_SECOND_ACTIVITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getActivityList().size());

        ActivityEditCommand editCommand = new ActivityEditCommand(outOfBoundIndex,
                new EditActivityDescriptorBuilder().withActivityName(VALID_ACTIVITY_NAME_HTML).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ActivityEditCommand standardCommand = new ActivityEditCommand(INDEX_FIRST_ACTIVITY, A_DESC_HTML);

        // same values -> returns true
        ActivityEditCommand.EditActivityDescriptor copyDescriptor =
                new ActivityEditCommand.EditActivityDescriptor(A_DESC_HTML);
        ActivityEditCommand commandWithSameValues = new ActivityEditCommand(INDEX_FIRST_ACTIVITY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ActivityEditCommand(INDEX_SECOND_ACTIVITY, A_DESC_HTML)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new ActivityEditCommand(INDEX_FIRST_ACTIVITY, A_DESC_OUTING)));
    }

}
