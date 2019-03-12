package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.address.testutil.TypicalRecords.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.record.Description;
import seedu.address.model.record.Record;
import seedu.address.testutil.RecordBuilder;

/**
 * Contains integration tests (interaction with Model)and unit tests for DescriptionCommand.
 */
public class DescriptionCommandTest {

    private static final String DESCRIPTION_STUB = "Some description";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_addDescriptionUnfilteredList_success() {
        Record firstRecord = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(firstRecord).withDescription(new Description(DESCRIPTION_STUB)).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(editedRecord.getDescription().value));

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_ADD_DESCRIPTION_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecord(firstRecord, editedRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(descriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeDescriptionUnfilteredList_success() {
        Record firstRecord = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(firstRecord).withDescription(new Description("")).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(editedRecord.getDescription().value));

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_REMOVE_DESCRIPTION_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecord(firstRecord, editedRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(descriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);

        Record firstRecord = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased()))
                .withDescription(new Description(DESCRIPTION_STUB)).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(editedRecord.getDescription().value));

        String expectedMessage = String.format(DescriptionCommand.MESSAGE_ADD_DESCRIPTION_SUCCESS, editedRecord);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setRecord(firstRecord, editedRecord);
        expectedModel.commitAddressBook();

        assertCommandSuccess(descriptionCommand, model, commandHistory, expectedMessage, expectedModel);
    }




    @Test
    public void execute_invalidRecordIndexUnfilteredIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DescriptionCommand descriptionCommand = new DescriptionCommand(outOfBoundIndex,
                new Description(VALID_DESCRIPTION_BOB));

        assertCommandFailure(descriptionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidRecordIndexFilteredList_failure() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        Index outOfBoundIndex = INDEX_SECOND_RECORD;

        // see if outOfBoundIndex is still smaller than max size of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getRecordList().size());

        DescriptionCommand descriptionCommand = new DescriptionCommand(outOfBoundIndex,
                new Description(VALID_DESCRIPTION_BOB));

        assertCommandFailure(descriptionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Record recordToEdit = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record editedRecord = new RecordBuilder(recordToEdit)
                .withDescription(new Description(DESCRIPTION_STUB)).build();

        DescriptionCommand descriptionCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(DESCRIPTION_STUB));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setRecord(recordToEdit, editedRecord);
        expectedModel.commitAddressBook();

        // description -> first record description changed
        descriptionCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered record list to show all records
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first record edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DescriptionCommand descriptionCommand = new DescriptionCommand(outOfBoundIndex, new Description(""));

        // execution failed -> address book state not added into model
        assertCommandFailure(descriptionCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies {@code Record#description} from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified record in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the record object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecordDeleted() throws Exception {
        DescriptionCommand remarkCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(DESCRIPTION_STUB));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showRecordAtIndex(model, INDEX_SECOND_RECORD);
        Record recordToModify = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        Record modifiedRecord = new RecordBuilder(recordToModify)
                .withDescription(new Description(DESCRIPTION_STUB)).build();

        expectedModel.setRecord(recordToModify, modifiedRecord);
        expectedModel.commitAddressBook();

        // remark -> modifies second record in unfiltered record list / first record in filtered record list
        remarkCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered record list to show all records
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> modifies same second record in unfiltered record list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final DescriptionCommand standardCommand = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(VALID_DESCRIPTION_AMY));

        // Object with same values -> returns true
        DescriptionCommand commandWithSameValues = new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(VALID_DESCRIPTION_AMY));

        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DescriptionCommand(INDEX_SECOND_RECORD,
                new Description(VALID_DESCRIPTION_AMY))));

        // different description -> returns false
        assertFalse(standardCommand.equals(new DescriptionCommand(INDEX_FIRST_RECORD,
                new Description(VALID_DESCRIPTION_BOB))));
    }
}
