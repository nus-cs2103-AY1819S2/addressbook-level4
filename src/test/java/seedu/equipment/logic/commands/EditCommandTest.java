package seedu.equipment.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.equipment.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.equipment.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalAddressBook;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.testutil.EditPersonDescriptorBuilder;
import seedu.equipment.testutil.EquipmentBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Equipment editedEquipment = new EquipmentBuilder().build();
        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder(editedEquipment).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedEquipment);

        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.setEquipment(model.getFilteredPersonList().get(0), editedEquipment);
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Equipment lastEquipment = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        EquipmentBuilder personInList = new EquipmentBuilder(lastEquipment);
        Equipment editedEquipment = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withSerialNumber(VALID_SERIAL_NUMBER_BOB).withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSerialNumber(VALID_SERIAL_NUMBER_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedEquipment);

        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.setEquipment(lastEquipment, editedEquipment);
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditEquipmentDescriptor());
        Equipment editedEquipment = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedEquipment);

        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Equipment equipmentInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Equipment editedEquipment = new EquipmentBuilder(equipmentInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedEquipment);

        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.setEquipment(model.getFilteredPersonList().get(0), editedEquipment);
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Equipment firstEquipment = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder(firstEquipment).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit equipment in filtered list into a duplicate in equipment book
        Equipment equipmentInList = model.getEquipmentManager().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(equipmentInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_EQUIPMENT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of equipment book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of equipment book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEquipmentManager().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Equipment editedEquipment = new EquipmentBuilder().build();
        Equipment equipmentToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder(editedEquipment).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());
        expectedModel.setEquipment(equipmentToEdit, editedEquipment);
        expectedModel.commitEquipmentManager();

        // edit -> first equipment edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered equipment list to show all persons
        expectedModel.undoEquipmentManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first equipment edited again
        expectedModel.redoEquipmentManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> equipment book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_EQUIPMENT_DISPLAYED_INDEX);

        // single equipment book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Equipment} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited equipment in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the equipment object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Equipment editedEquipment = new EquipmentBuilder().build();
        EditCommand.EditEquipmentDescriptor descriptor = new EditPersonDescriptorBuilder(editedEquipment).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new EquipmentManager(model.getEquipmentManager()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Equipment equipmentToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.setEquipment(equipmentToEdit, editedEquipment);
        expectedModel.commitEquipmentManager();

        // edit -> edits second equipment in unfiltered equipment list / first equipment in filtered equipment list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered equipment list to show all persons
        expectedModel.undoEquipmentManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), equipmentToEdit);
        // redo -> edits same second equipment in unfiltered equipment list
        expectedModel.redoEquipmentManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditCommand.EditEquipmentDescriptor copyDescriptor = new EditCommand.EditEquipmentDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
