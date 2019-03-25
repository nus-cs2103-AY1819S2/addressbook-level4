package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Quantity;

/**
 * Updates a medicine with new batch details.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the medicine identified by the index number with new batch details. "
            + "Existing values will be overwritten. Batch updated with quantity 0 will be removed.\n"
            + "Parameters: "
            + "INDEX "
            + PREFIX_BATCHNUMBER + "BATCH_NUMBER "
            + PREFIX_QUANTITY + "QUANTITY "
            + "[" + PREFIX_EXPIRY + "EXPIRY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BATCHNUMBER + "HH-156224 "
            + PREFIX_QUANTITY + "40 "
            + PREFIX_EXPIRY + "13/11/2019 ";

    public static final String MESSAGE_SUCCESS = "Batch updated: %1$s";
    public static final String MESSAGE_MISSING_PARAMETER = "Must include batch number and quantity of the batch "
            + "used for updating.";
    public static final String MESSAGE_MISSING_EXPIRY = "Must include expiry date for new batches.";
    public static final String MESSAGE_MISSING_QUANTITY = "Batch not found. Cannot remove batch.";
    public static final String MESSAGE_MAX_QUANTITY_EXCEEDED = "Max quantity exceeded. Max quantity: "
            + Quantity.MAX_QUANTITY;

    private final Index targetIndex;
    private final Batch newBatchDetails;

    /**
     * Creates an UpdateCommand to update the {@code Medicine} at the specified {@index} with new batch details.
     */
    public UpdateCommand(Index targetIndex, Batch newBatchDetails) {
        requireNonNull(targetIndex);
        requireNonNull(newBatchDetails);

        this.targetIndex = targetIndex;
        this.newBatchDetails = newBatchDetails;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Medicine> lastShownList = model.getFilteredMedicineList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        Medicine medicineToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Batch batchToUpdate = medicineToUpdate.getBatches().get(newBatchDetails.getBatchNumber());

        if (batchToUpdate == null) {
            checkNewBatchCommand();
        }

        Batch updatedBatch = newBatchDetails;
        if (!updatedBatch.hasExpiry()) {
            updatedBatch = getExistingBatchExpiry(batchToUpdate);
        }

        Medicine updatedMedicine = getUpdatedMedicine(medicineToUpdate, batchToUpdate, updatedBatch);

        model.setMedicine(medicineToUpdate, updatedMedicine);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedBatch));
    }

    /**
     * Throws CommandException if fields needed to add a new batch is not input correctly.
     */
    private void checkNewBatchCommand() throws CommandException {
        if (!newBatchDetails.hasNonZeroQuantity()) {
            throw new CommandException(MESSAGE_MISSING_QUANTITY);
        }
        if (!newBatchDetails.hasExpiry()) {
            throw new CommandException(MESSAGE_MISSING_EXPIRY);
        }
    }

    /**
     * Returns a new {@code Batch} with existing batch's {@code Expiry} added to {@code newBatchDetails}.
     */
    private Batch getExistingBatchExpiry(Batch batchToUpdate) {
        return new Batch(newBatchDetails.getBatchNumber(), batchToUpdate.getExpiry(), newBatchDetails.getQuantity());
    }

    /**
     * Returns a {@code Medicine} with the details of {@code medicineToUpdate} updated with {@code updatedBatch}.
     */
    private Medicine getUpdatedMedicine(Medicine medicineToUpdate, Batch batchToUpdate, Batch updatedBatch) throws
            CommandException {
        Map<BatchNumber, Batch> updatedBatches = getNewMedicineBatches(medicineToUpdate, updatedBatch);
        Quantity updatedQuantity = getNewMedicineQuantity(medicineToUpdate, batchToUpdate, updatedBatch);
        Expiry updatedExpiry = getNewMedicineExpiry(medicineToUpdate, batchToUpdate, updatedBatch, updatedBatches);

        return new Medicine(medicineToUpdate.getName(), updatedQuantity, updatedExpiry, medicineToUpdate.getCompany(),
                medicineToUpdate.getTags(), updatedBatches);
    }

    private Map<BatchNumber, Batch> getNewMedicineBatches(Medicine medicineToUpdate, Batch updatedBatch) {
        HashMap<BatchNumber, Batch> newBatches = new HashMap<>(medicineToUpdate.getBatches());
        if (updatedBatch.hasNonZeroQuantity()) {
            newBatches.put(updatedBatch.getBatchNumber(), updatedBatch);
        } else {
            newBatches.remove(updatedBatch.getBatchNumber());
        }
        return newBatches;
    }

    private Quantity getNewMedicineQuantity(Medicine medicineToUpdate, Batch batchToUpdate, Batch updatedBatch) throws
            CommandException {
        int quantity = medicineToUpdate.getTotalQuantity().getNumericValue();

        if (batchToUpdate != null) {
            quantity -= batchToUpdate.getQuantity().getNumericValue();
        }
        quantity += updatedBatch.getQuantity().getNumericValue();

        try {
            return new Quantity(Integer.toString(quantity));
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_MAX_QUANTITY_EXCEEDED);
        }
    }

    private Expiry getNewMedicineExpiry(Medicine medicineToUpdate, Batch batchToUpdate,
            Batch updatedBatch, Map<BatchNumber, Batch> updatedBatches) {

        if (updatedBatches.size() == 0) {
            return new Expiry("-");
        }

        Expiry currentExpiry = medicineToUpdate.getNextExpiry();

        if (currentExpiry.getExpiryDate() == null) {
            return updatedBatch.getExpiry();
        }

        if (batchToUpdate != null && batchToUpdate.getExpiry().equals(currentExpiry)){
            return updatedBatches.values().stream().min(Comparator.comparing(Batch::getExpiry)).get().getExpiry();
        }

        if (updatedBatch.getExpiry().compareTo(currentExpiry) < 0) {
            return updatedBatch.getExpiry();
        } else {
            return currentExpiry;
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.UpdateCommand // instanceof handles nulls
                && targetIndex.equals(((seedu.address.logic.commands.UpdateCommand) other).targetIndex)
                && newBatchDetails.equals(((seedu.address.logic.commands.UpdateCommand) other).newBatchDetails));
    }
}
