package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.BATCHNUMBER_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.BATCHNUMBER_DESC_LISNOPRIL;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.EXPIRY_DESC_LISNOPRIL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BATCHNUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPIRY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_LISNOPRIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BATCHNUMBER_LISNOPRIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_LISPINOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_LISNOPRIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.KEYWORD_MATCHING_SODIUM;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;
import seedu.address.testutil.BatchBuilder;
import seedu.address.testutil.MedicineBuilder;

public class UpdateCommandSystemTest extends MediTabsSystemTest {

    @Test
    public void update() {
        Model model = getModel();

        /* ---------------- Performing update operation while an unfiltered list is being shown --------------------- */

        /* Case: add a new batch, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> updated
         */
        Index index = INDEX_FIRST_MEDICINE;
        Medicine medicineToUpdate = model.getFilteredMedicineList().get(index.getZeroBased());
        String command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_LISNOPRIL + "  " + QUANTITY_DESC_LISNOPRIL + " " + EXPIRY_DESC_LISNOPRIL + " ";
        Batch batch = new BatchBuilder().withBatchNumber(VALID_BATCHNUMBER_LISNOPRIL)
                .withQuantity(VALID_QUANTITY_LISNOPRIL).withExpiry(VALID_EXPIRY_LISPINOL).build();

        Medicine updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(batch.getQuantity().toString())
                .withExpiry(VALID_EXPIRY_LISPINOL)
                .withAddedBatch(batch).build();

        assertCommandSuccess(command, index, updatedMedicine, batch, index, false);

        /* Case: undo adding the batch -> batch removed */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding the batch -> batch replaced */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setMedicine(getModel().getFilteredMedicineList().get(index.getZeroBased()),
                updatedMedicine);
        model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        assertCommandSuccess(command, model, expectedResultMessage);


        /* Case: update existing batch, no expiry input -> updated */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_LISNOPRIL + "  " + QUANTITY_DESC_AMOXICILLIN;

        Batch existingBatch = batch;
        Batch updatedBatch = new BatchBuilder(existingBatch).withQuantity(VALID_QUANTITY_AMOXICILLIN).build();

        int changeInQuantity = updatedBatch.getQuantity().getNumericValue() - existingBatch.getQuantity()
                .getNumericValue();

        updatedMedicine = new MedicineBuilder(updatedMedicine).withAddedQuantity(Integer.toString(changeInQuantity))
                .withAddedBatch(updatedBatch).build();

        assertCommandSuccess(command, index, updatedMedicine, updatedBatch, index, false);

        /* Case: update existing batch, no quantity input -> updated */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_LISNOPRIL + "  " + EXPIRY_DESC_AMOXICILLIN;

        existingBatch = updatedBatch;
        updatedBatch = new BatchBuilder(existingBatch).withExpiry(VALID_EXPIRY_AMOXICILLIN).build();

        updatedMedicine = new MedicineBuilder(updatedMedicine).withExpiry(VALID_EXPIRY_AMOXICILLIN)
                .withAddedBatch(updatedBatch).build();

        assertCommandSuccess(command, index, updatedMedicine, updatedBatch, index, false);

        /* Case: remove existing batch -> updated */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_LISNOPRIL + "  " + PREFIX_QUANTITY + "0";

        existingBatch = updatedBatch;

        Map<BatchNumber, Batch> batches = new HashMap<>(medicineToUpdate.getBatches());
        batches.remove(existingBatch.getBatchNumber());

        int newQuantity = updatedMedicine.getTotalQuantity().getNumericValue()
                - existingBatch.getQuantity().getNumericValue();

        updatedMedicine = new MedicineBuilder(updatedMedicine).withQuantity(Integer.toString(newQuantity))
                .withExpiry("-").withBatches(batches).build();

        updatedBatch = new BatchBuilder(existingBatch).withQuantity("0").build();

        assertCommandSuccess(command, index, updatedMedicine, updatedBatch, index, false);

        /* ------------------ Performing update operation while a filtered list is being shown ---------------------- */

        /* Case: filtered medicine list, update index within bounds of inventory and medicine list -> updated */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        index = INDEX_FIRST_MEDICINE;
        assertTrue(index.getZeroBased() < getModel().getFilteredMedicineList().size());
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_LISNOPRIL + "  " + QUANTITY_DESC_LISNOPRIL + " " + EXPIRY_DESC_LISNOPRIL + " ";
        medicineToUpdate = getModel().getFilteredMedicineList().get(index.getZeroBased());
        batch = new BatchBuilder().withBatchNumber(VALID_BATCHNUMBER_LISNOPRIL)
                .withQuantity(VALID_QUANTITY_LISNOPRIL).withExpiry(VALID_EXPIRY_LISPINOL).build();

        updatedMedicine = new MedicineBuilder(medicineToUpdate)
                .withAddedQuantity(VALID_QUANTITY_LISNOPRIL)
                .withAddedBatch(batch).build();
        assertCommandSuccess(command, index, updatedMedicine, batch, index, true);

        /* Case: filtered medicine list, update index within bounds of inventory but out of bounds of medicine list
         * -> rejected
         */
        showMedicinesWithName(KEYWORD_MATCHING_SODIUM);
        int invalidIndex = getModel().getInventory().getMedicineList().size();
        command = " " + UpdateCommand.COMMAND_WORD + "  " + invalidIndex + "  "
                + BATCHNUMBER_DESC_AMOXICILLIN + "  " + QUANTITY_DESC_LISNOPRIL + " " + EXPIRY_DESC_LISNOPRIL + " ";
        assertCommandFailure(command, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid update operation ----------------__------------------ */

        /* Case: invalid index (0) -> rejected */
        command = UpdateCommand.COMMAND_WORD + "  " + "0" + "  " + BATCHNUMBER_DESC_AMOXICILLIN + "  "
                + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        command = UpdateCommand.COMMAND_WORD + "  " + "-1" + "  " + BATCHNUMBER_DESC_AMOXICILLIN + "  "
                + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredMedicineList().size() + 1;
        command = UpdateCommand.COMMAND_WORD + "  " + invalidIndex + "  " + BATCHNUMBER_DESC_AMOXICILLIN + "  "
                + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        command = UpdateCommand.COMMAND_WORD + "  " + BATCHNUMBER_DESC_AMOXICILLIN + "  "
                + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateCommand.MESSAGE_USAGE));

        /* Case: add a new batch, no batch number input -> rejected */
        command = UpdateCommand.COMMAND_WORD + "  " + index.getOneBased()
                + QUANTITY_DESC_AMOXICILLIN + "  " + EXPIRY_DESC_AMOXICILLIN;

        assertCommandFailure(command, UpdateCommand.MESSAGE_MISSING_PARAMETER);

        /* Case: add a new batch, no quantity and no expiry -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_AMOXICILLIN;

        assertCommandFailure(command, UpdateCommand.MESSAGE_MISSING_PARAMETER);

        /* Case: add a new batch, no expiry input -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_AMOXICILLIN + "  " + QUANTITY_DESC_AMOXICILLIN;

        assertCommandFailure(command, UpdateCommand.MESSAGE_NEW_BATCH_MISSING_PARAMETER);

        /* Case: add a new batch, no quantity input -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_AMOXICILLIN + "  " + EXPIRY_DESC_AMOXICILLIN;

        assertCommandFailure(command, UpdateCommand.MESSAGE_NEW_BATCH_MISSING_PARAMETER);

        /* Case: add a new batch, zero quantity input -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + BATCHNUMBER_DESC_AMOXICILLIN + "  " + PREFIX_QUANTITY + "0 " + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, UpdateCommand.MESSAGE_NEW_BATCH_ZERO_QUANTITY);


        /* Case: invalid batch number -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + INVALID_BATCHNUMBER_DESC
                + QUANTITY_DESC_AMOXICILLIN + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, BatchNumber.MESSAGE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + BATCHNUMBER_DESC_AMOXICILLIN
                + INVALID_QUANTITY_DESC + EXPIRY_DESC_AMOXICILLIN;
        assertCommandFailure(command, Quantity.MESSAGE_CONSTRAINTS);

        /* Case: invalid expiry -> rejected */
        command = " " + UpdateCommand.COMMAND_WORD + "  " + index.getOneBased() + BATCHNUMBER_DESC_AMOXICILLIN
                + QUANTITY_DESC_AMOXICILLIN + INVALID_EXPIRY_DESC;
        assertCommandFailure(command, Expiry.MESSAGE_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the medicine at index {@code toEdit} being
     * updated to values specified {@code editedMedicine}.<br>
     * @param toUpdate the index of the current model's filtered list.
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUpdate, Medicine updatedMedicine, Batch updatedBatch,
            Index expectedSelectedCardIndex, boolean isFiltered) {
        Model expectedModel = getModel();
        expectedModel.setMedicine(expectedModel.getFilteredMedicineList().get(toUpdate.getZeroBased()),
                updatedMedicine);
        if (!isFiltered) {
            expectedModel.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        }

        assertCommandSuccess(command, expectedModel,
                String.format(UpdateCommand.MESSAGE_SUCCESS, updatedBatch), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * loaded information table and selected card remain unchanged.
     * @see UpdateCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the loaded information table and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see MediTabsSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertInformationPageIsCorrect();
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the loaded information table, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see MediTabsSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
