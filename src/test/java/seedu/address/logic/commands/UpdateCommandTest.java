package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BATCHNUMBER_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.UpdateCommand.UpdateBatchDescriptor;
import seedu.address.model.Inventory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.testutil.BatchBuilder;
import seedu.address.testutil.MedicineBuilder;
import seedu.address.testutil.UpdateBatchDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * UpdateCommand.
 */
public class UpdateCommandTest {
    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Batch defaultBatch = new BatchBuilder().build();

    @Test
    public void execute_addNewBatchUnfilteredList_success() {
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(0);
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();

        String expectedMessage = String.format(UpdateCommand.MESSAGE_SUCCESS, defaultBatch);
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(BatchBuilder.DEFAULT_QUANTITY).withAddedBatch(defaultBatch)
                .withExpiry(BatchBuilder.DEFAULT_EXPIRY).build();

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addNewBatchUnfilteredListNoExpiry_failure() {
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(0);
        assertFalse(medicineToUpdate.getBatches().containsKey(defaultBatch.getBatchNumber()));

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).withNoExpiry().build();

        String expectedMessage = UpdateCommand.MESSAGE_NEW_BATCH_MISSING_PARAMETER;
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_addNewBatchUnfilteredListNoQuantity_failure() {
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(0);
        assertFalse(medicineToUpdate.getBatches().containsKey(defaultBatch.getBatchNumber()));

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).withNoQuantity().build();

        String expectedMessage = UpdateCommand.MESSAGE_NEW_BATCH_MISSING_PARAMETER;
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_addNewBatchUnfilteredListZeroQuantity_failure() {
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(0);
        Batch newBatch = new BatchBuilder().withQuantity("0").build();
        assertFalse(medicineToUpdate.getBatches().containsKey(newBatch.getBatchNumber()));

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(newBatch).build();

        String expectedMessage = UpdateCommand.MESSAGE_NEW_BATCH_ZERO_QUANTITY;
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_updateExistingBatchUnfilteredListNoExpiry_success() {
        Index indexLastMedicine = Index.fromOneBased(model.getFilteredMedicineList().size());
        Medicine lastMedicine = model.getFilteredMedicineList().get(indexLastMedicine.getZeroBased());

        // Get an existing batch from lastMedicine
        Iterator<Batch> iter = lastMedicine.getBatches().values().iterator();
        assertTrue(iter.hasNext());
        Batch batchToUpdate = iter.next();

        Batch updatedBatch = new BatchBuilder(batchToUpdate).withQuantity(VALID_QUANTITY_AMOXICILLIN).build();
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(updatedBatch).withNoExpiry().build();

        int changeInQuantity = updatedBatch.getQuantity().getNumericValue() - batchToUpdate.getQuantity()
                .getNumericValue();

        Medicine updatedMedicine = new MedicineBuilder(lastMedicine)
                .withAddedQuantity(Integer.toString(changeInQuantity))
                .withAddedBatch(updatedBatch).build();

        UpdateCommand updateCommand = new UpdateCommand(indexLastMedicine, newBatchDetails);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_SUCCESS, updatedBatch);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(lastMedicine, updatedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_updateExistingBatchUnfilteredListNoQuantity_success() {
        Index index = INDEX_FIRST_MEDICINE;
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(index.getZeroBased());

        // Get an existing batch from medicineToUpdate
        Iterator<Batch> iter = medicineToUpdate.getBatches().values().iterator();
        assertTrue(iter.hasNext());
        Batch batchToUpdate = iter.next();

        Batch updatedBatch = new BatchBuilder(batchToUpdate).withExpiry(defaultBatch.getExpiry().toString()).build();

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withExpiry(defaultBatch.getExpiry().toString())
                .withAddedBatch(updatedBatch).build();

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(updatedBatch).withNoQuantity()
                .build();

        UpdateCommand updateCommand = new UpdateCommand(index, newBatchDetails);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_SUCCESS, updatedBatch);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeBatchUnfilteredListNoOtherBatch_success() {
        Index index = INDEX_SECOND_MEDICINE;
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(index.getZeroBased());

        Map<BatchNumber, Batch> batches = new HashMap<>(medicineToUpdate.getBatches());

        // Get an existing batch from medicineToUpdate
        Iterator<Batch> iter = batches.values().iterator();
        assertTrue(iter.hasNext());
        Batch batchToRemove = iter.next();
        assertFalse(iter.hasNext()); // No more batches

        batches.remove(batchToRemove.getBatchNumber());

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withQuantity("0")
                .withExpiry("-")
                .withBatches(batches).build();

        Batch inputBatch = new BatchBuilder(batchToRemove).withQuantity("0").build();
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(inputBatch).withNoExpiry().build();

        UpdateCommand updateCommand = new UpdateCommand(index, newBatchDetails);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_SUCCESS, inputBatch);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeBatchUnfilteredListOneOtherBatch_success() {
        Index index = INDEX_FOURTH_MEDICINE;
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(index.getZeroBased());

        Map<BatchNumber, Batch> batches = new HashMap<>(medicineToUpdate.getBatches());
        Iterator<Batch> iter = batches.values().iterator();
        assertTrue(iter.hasNext());
        Batch batchToRemove = iter.next();

        assertTrue(iter.hasNext()); // There is one more batch after removing
        Batch batchRemaining = iter.next();

        batches.remove(batchToRemove.getBatchNumber());

        int newQuantity = medicineToUpdate.getTotalQuantity().getNumericValue()
                - batchToRemove.getQuantity().getNumericValue();

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withQuantity(Integer.toString(newQuantity))
                .withExpiry(batchRemaining.getExpiry().toString())
                .withBatches(batches).build();

        Batch inputBatch = new BatchBuilder(batchToRemove).withQuantity("0").build();
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(inputBatch).withNoExpiry().build();

        UpdateCommand updateCommand = new UpdateCommand(index, newBatchDetails);

        String expectedMessage = String.format(UpdateCommand.MESSAGE_SUCCESS, inputBatch);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        Medicine medicineInFilteredList = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        Medicine updatedMedicine = new MedicineBuilder(medicineInFilteredList)
                .withAddedQuantity(BatchBuilder.DEFAULT_QUANTITY)
                .withExpiry(BatchBuilder.DEFAULT_EXPIRY)
                .withAddedBatch(defaultBatch)
                .build();

        String expectedMessage = String.format(UpdateCommand.MESSAGE_SUCCESS, defaultBatch);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineInFilteredList, updatedMedicine);
        expectedModel.commitInventory();
        showMedicineAtIndex(expectedModel, INDEX_FIRST_MEDICINE); // List should remain filtered after update

        assertCommandSuccess(updateCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidMedicineIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, newBatchDetails);

        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_exceedMaxQuantityUnfilteredList_success() {
        Batch newBatch = new BatchBuilder().withQuantity(Integer.toString(Quantity.MAX_QUANTITY)).build();
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(newBatch).build();

        String expectedMessage = UpdateCommand.MESSAGE_MAX_QUANTITY_EXCEEDED;

        // Try to add max quantity to the first medicine in the inventory.
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);
        assertCommandFailure(updateCommand, model, commandHistory, expectedMessage);
    }

    /**
     * Update filtered list where index is larger than size of filtered list,
     * but smaller than size of inventory
     */
    @Test
    public void execute_invalidMedicineIndexFilteredList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        Index outOfBoundIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getMedicineList().size());

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, newBatchDetails);

        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredListNewBatch_success() throws Exception {
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(BatchBuilder.DEFAULT_QUANTITY)
                .withExpiry(BatchBuilder.DEFAULT_EXPIRY)
                .withAddedBatch(defaultBatch)
                .build();

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        // update -> first medicine updated
        updateCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first medicine updated again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredListExistingBatch_success() throws Exception {
        Index index = INDEX_SECOND_MEDICINE;
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(index.getZeroBased());

        Iterator<Batch> iter = medicineToUpdate.getBatches().values().iterator();
        assertTrue(iter.hasNext());
        Batch batchToUpdate = iter.next();

        Batch updatedBatch = new BatchBuilder(batchToUpdate).withQuantity(VALID_QUANTITY_AMOXICILLIN).build();

        int changeInQuantity = updatedBatch.getQuantity().getNumericValue() - batchToUpdate.getQuantity()
                .getNumericValue();

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(Integer.toString(changeInQuantity))
                .withAddedBatch(updatedBatch).build();

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(updatedBatch).withNoExpiry().build();

        UpdateCommand updateCommand = new UpdateCommand(index, newBatchDetails);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        // update -> second medicine updated
        updateCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first medicine updated again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredListRemoveBatch_success() throws Exception {
        Index index = INDEX_FIRST_MEDICINE;
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(index.getZeroBased());

        Map<BatchNumber, Batch> batches = new HashMap<>(medicineToUpdate.getBatches());
        Iterator<Batch> iter = batches.values().iterator();
        assertTrue(iter.hasNext());
        Batch batchToRemove = iter.next();

        batches.remove(batchToRemove.getBatchNumber());

        int changeInQuantity = -(batchToRemove.getQuantity().getNumericValue());

        String updatedExpiry;
        if (batches.size() > 0) {
            updatedExpiry = batches.values().stream()
                    .min(Comparator.comparing(Batch::getExpiry)).get().getExpiry().toString();
        } else {
            updatedExpiry = "-";
        }

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(Integer.toString(changeInQuantity))
                .withExpiry(updatedExpiry)
                .withBatches(batches).build();

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(batchToRemove).withQuantity("0")
                .withNoExpiry().build();

        UpdateCommand updateCommand = new UpdateCommand(index, newBatchDetails);

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        // update -> first medicine updated
        updateCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first medicine updated again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() >= model.getInventory().getMedicineList().size());

        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();

        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, newBatchDetails);

        // execution failed -> inventory state not added into model
        assertCommandFailure(updateCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Updates a {@code Medicine} from a filtered list.
     * 2. Undo the update.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously updated medicine in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the update. This ensures {@code RedoCommand} updates the medicine object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMedicineUpdated() throws Exception {
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(INDEX_SECOND_MEDICINE.getZeroBased());
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(BatchBuilder.DEFAULT_QUANTITY)
                .withExpiry(BatchBuilder.DEFAULT_EXPIRY)
                .withAddedBatch(defaultBatch)
                .build();

        Model expectedModel = new ModelManager(new Inventory(model.getInventory()), new UserPrefs());

        showMedicineAtIndex(model, INDEX_SECOND_MEDICINE);
        medicineToUpdate = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        expectedModel.setMedicine(medicineToUpdate, updatedMedicine);
        expectedModel.commitInventory();

        // update -> updates second medicine in unfiltered medicine list / first medicine in filtered medicine list
        updateCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);
        assertNotEquals(model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased()), medicineToUpdate);

        // redo -> updates same second medicine in unfiltered medicine list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        UpdateBatchDescriptor newBatchDetails = new UpdateBatchDescriptorBuilder(defaultBatch).build();
        final UpdateCommand standardCommand = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);

        // same values -> returns true
        UpdateCommand commandWithSameValues = new UpdateCommand(INDEX_FIRST_MEDICINE, newBatchDetails);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_SECOND_MEDICINE, newBatchDetails)));

        // different UpdateBatchDescriptor -> returns false
        Batch differentBatch = new BatchBuilder().withBatchNumber(VALID_BATCHNUMBER_AMOXICILLIN).build();
        UpdateBatchDescriptor differentBatchDetails = new UpdateBatchDescriptorBuilder(differentBatch).build();
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_FIRST_MEDICINE, differentBatchDetails)));
    }
}

