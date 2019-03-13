package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GABAPENTIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PAINKILLER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditMedicineDescriptor;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;
import seedu.address.testutil.EditMedicineDescriptorBuilder;
import seedu.address.testutil.MedicineBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Medicine medicineToEdit = model.getFilteredMedicineList().get(0);
        Medicine editedMedicine = new MedicineBuilder().withUneditableFields(medicineToEdit).build();
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder(editedMedicine).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEDICINE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToEdit, editedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMedicine = Index.fromOneBased(model.getFilteredMedicineList().size());
        Medicine lastMedicine = model.getFilteredMedicineList().get(indexLastMedicine.getZeroBased());

        MedicineBuilder medicineInList = new MedicineBuilder(lastMedicine);
        Medicine editedMedicine = medicineInList.withName(VALID_NAME_GABAPENTIN).withTags(VALID_TAG_PAINKILLER).build();

        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_GABAPENTIN)
                .withTags(VALID_TAG_PAINKILLER).build();
        EditCommand editCommand = new EditCommand(indexLastMedicine, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(lastMedicine, editedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEDICINE, new EditMedicineDescriptor());
        Medicine editedMedicine = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        Medicine medicineInFilteredList = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        Medicine editedMedicine = new MedicineBuilder(medicineInFilteredList).withName(VALID_NAME_GABAPENTIN).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEDICINE,
                new EditMedicineDescriptorBuilder().withName(VALID_NAME_GABAPENTIN).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEDICINE_SUCCESS, editedMedicine);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(model.getFilteredMedicineList().get(0), editedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMedicineUnfilteredList_failure() {
        Medicine firstMedicine = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder(firstMedicine).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_MEDICINE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

    @Test
    public void execute_duplicateMedicineFilteredList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        // edit medicine in filtered list into a duplicate in inventory
        Medicine medicineInList = model.getInventory().getMedicineList().get(INDEX_SECOND_MEDICINE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEDICINE,
                new EditMedicineDescriptorBuilder(medicineInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

    @Test
    public void execute_invalidMedicineIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_GABAPENTIN).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of inventory
     */
    @Test
    public void execute_invalidMedicineIndexFilteredList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        Index outOfBoundIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getMedicineList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMedicineDescriptorBuilder().withName(VALID_NAME_GABAPENTIN).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Medicine medicineToEdit = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        Medicine editedMedicine = new MedicineBuilder().withUneditableFields(medicineToEdit).build();
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder(editedMedicine).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEDICINE, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToEdit, editedMedicine);
        expectedModel.commitInventory();

        // edit -> first medicine edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first medicine edited again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder().withName(VALID_NAME_GABAPENTIN).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> inventory state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Medicine} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited medicine in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the medicine object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMedicineEdited() throws Exception {
        Medicine medicineToEdit = model.getFilteredMedicineList().get(INDEX_SECOND_MEDICINE.getZeroBased());
        Medicine editedMedicine = new MedicineBuilder().withUneditableFields(medicineToEdit).build();
        EditMedicineDescriptor descriptor = new EditMedicineDescriptorBuilder(editedMedicine).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEDICINE, descriptor);
        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());

        showMedicineAtIndex(model, INDEX_SECOND_MEDICINE);
        medicineToEdit = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        expectedModel.setMedicine(medicineToEdit, editedMedicine);
        expectedModel.commitInventory();

        // edit -> edits second medicine in unfiltered medicine list / first medicine in filtered medicine list
        editCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased()), medicineToEdit);
        // redo -> edits same second medicine in unfiltered medicine list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MEDICINE, DESC_AMOXICILLIN);

        // same values -> returns true
        EditMedicineDescriptor copyDescriptor = new EditMedicineDescriptor(DESC_AMOXICILLIN);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MEDICINE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MEDICINE, DESC_AMOXICILLIN)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MEDICINE, DESC_GABAPENTIN)));
    }

}
