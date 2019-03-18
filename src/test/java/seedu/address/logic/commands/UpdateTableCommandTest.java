package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showTableAtIndex;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestOrRant;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateTableCommand.
 */
public class UpdateTableCommandTest {

    private Model model = new ModelManager(getTypical(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Table editedTable = new TableBuilder().build();
        EditTableDescriptor descriptor = new EditTableDescriptorBuilder(editedTable).build();
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UpdateTableCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTable);

        Model expectedModel = new ModelManager(new RestOrRant(model.getRestOrRant()), new UserPrefs());
        expectedModel.setTable(model.getFilteredTableList().get(0), editedTable);
        expectedModel.updateMode();

        assertCommandSuccess(updateTableCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTable = Index.fromOneBased(model.getFilteredTableList().size());
        Table lastTable = model.getFilteredTableList().get(indexLastTable.getZeroBased());

        TableBuilder tableInList = new TableBuilder(lastTable);
        Table editedTable = tableInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditTableDescriptor descriptor = new EditTableDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(indexLastTable, descriptor);

        String expectedMessage = String.format(UpdateTableCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTable);

        Model expectedModel = new ModelManager(new RestOrRant(model.getRestOrRant()), new UserPrefs());
        expectedModel.setTable(lastTable, editedTable);
        expectedModel.updateMode();

        assertCommandSuccess(updateTableCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(INDEX_FIRST_PERSON, new EditTableDescriptor());
        Table editedTable = model.getFilteredTableList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UpdateTableCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTable);

        Model expectedModel = new ModelManager(new RestOrRant(model.getRestOrRant()), new UserPrefs());
        expectedModel.updateMode();

        assertCommandSuccess(updateTableCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showTableAtIndex(model, INDEX_FIRST_PERSON);

        Table tableInFilteredList = model.getFilteredTableList().get(INDEX_FIRST_PERSON.getZeroBased());
        Table editedTable = new TableBuilder(tableInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(INDEX_FIRST_PERSON,
                new EditTableDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateTableCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedTable);

        Model expectedModel = new ModelManager(new RestOrRant(model.getRestOrRant()), new UserPrefs());
        expectedModel.setTable(model.getFilteredTableList().get(0), editedTable);
        expectedModel.updateMode();

        assertCommandSuccess(updateTableCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTableUnfilteredList_failure() {
        Table firstTable = model.getFilteredTableList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditTableDescriptor descriptor = new EditTableDescriptorBuilder(firstTable).build();
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(updateTableCommand, model, commandHistory, UpdateTableCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateTableFilteredList_failure() {
        showTableAtIndex(model, INDEX_FIRST_PERSON);

        // edit table in filtered list into a duplicate in address book
        Table tableInList = model.getRestOrRant().getTableList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(INDEX_FIRST_PERSON,
                new EditTableDescriptorBuilder(tableInList).build());

        assertCommandFailure(updateTableCommand, model, commandHistory, UpdateTableCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidTableIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTableList().size() + 1);
        EditTableDescriptor descriptor = new EditTableDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateTableCommand updateTableCommand = new UpdateTableCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(updateTableCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTableIndexFilteredList_failure() {
        showTableAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestOrRant().getTableList().size());

        UpdateTableCommand updateTableCommand = new UpdateTableCommand(outOfBoundIndex,
                new EditTableDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(updateTableCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UpdateTableCommand standardCommand = new UpdateTableCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditTableDescriptor copyDescriptor = new EditTableDescriptor(DESC_AMY);
        UpdateTableCommand commandWithSameValues = new UpdateTableCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateTableCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateTableCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
