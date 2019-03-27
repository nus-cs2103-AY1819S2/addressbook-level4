package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_EXPIRY + "EXPIRY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BATCHNUMBER + "HH-156224 "
            + PREFIX_QUANTITY + "40 "
            + PREFIX_EXPIRY + "13/11/2019 ";

    public static final String MESSAGE_SUCCESS = "Batch updated: %1$s";
    public static final String MESSAGE_MISSING_PARAMETER = "Must include batch number and quantity or expiry of the "
            + "batch used for updating.";
    public static final String MESSAGE_NEW_BATCH_MISSING_PARAMETER = "Must include expiry date and quantity for new "
            + "batches.";
    public static final String MESSAGE_NEW_BATCH_ZERO_QUANTITY = "Batch not found. Cannot remove batch.";
    public static final String MESSAGE_MAX_QUANTITY_EXCEEDED = "Max quantity exceeded. Max quantity: "
            + Quantity.MAX_QUANTITY;

    private final Index targetIndex;
    private final UpdateBatchDescriptor newBatchDetails;

    /**
     * Creates an UpdateCommand to update the {@code Medicine} at the specified {@index} with new batch details.
     */
    public UpdateCommand(Index targetIndex, UpdateBatchDescriptor newBatchDetails) {
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
            checkCommandForNewBatch();
        }

        Batch updatedBatch = createUpdatedBatch(batchToUpdate);
        Medicine updatedMedicine = createUpdatedMedicine(medicineToUpdate, batchToUpdate, updatedBatch);

        model.setMedicine(medicineToUpdate, updatedMedicine);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedBatch));
    }

    /**
     * Throws CommandException if fields needed to add a new batch is not input correctly.
     */
    private void checkCommandForNewBatch() throws CommandException {
        if (!newBatchDetails.getQuantity().isPresent() || !newBatchDetails.getExpiry().isPresent()) {
            throw new CommandException(MESSAGE_NEW_BATCH_MISSING_PARAMETER);
        }
        if (newBatchDetails.getQuantity().get().getNumericValue() == 0) {
            throw new CommandException(MESSAGE_NEW_BATCH_ZERO_QUANTITY);
        }
    }

    /**
     * Returns a {@code Batch} with the details of {@code batchToUpdate} updated with {@code newBatchDetails}.
     */
    private Batch createUpdatedBatch(Batch batchToUpdate) {
        Quantity quantity;
        Expiry expiry;

        if (newBatchDetails.getExpiry().isPresent()) {
            expiry = newBatchDetails.getExpiry().get();
        } else {
            expiry = batchToUpdate.getExpiry();
        }

        if (newBatchDetails.getQuantity().isPresent()) {
            quantity = newBatchDetails.getQuantity().get();
        } else {
            quantity = batchToUpdate.getQuantity();
        }

        return new Batch(newBatchDetails.getBatchNumber(), expiry, quantity);
    }

    /**
     * Returns a {@code Medicine} with the details of {@code medicineToUpdate} updated with {@code updatedBatch}.
     */
    private Medicine createUpdatedMedicine(Medicine medicineToUpdate, Batch batchToUpdate, Batch updatedBatch) throws
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

    /**
     * There are 4 possible scenarios for finding the next nearest expiry date after the update.
     * 1. There are no more batches after the update and so next expiry date is "-".
     * 2. There was previously no batches and so the added batch is the first batch. Thus expiry is the added batch's.
     * 3. The batch which had the current next expiry is being updated. Iterate through all batches to find next min.
     * 4. The batch which had the current next expiry is not being updated. Return the new batch expiry if it is nearer.
     */
    private Expiry getNewMedicineExpiry(Medicine medicineToUpdate, Batch batchToUpdate, Batch updatedBatch,
            Map<BatchNumber, Batch> updatedBatches) {
        if (updatedBatches.size() == 0) {
            return new Expiry("-");
        }

        Expiry currentExpiry = medicineToUpdate.getNextExpiry();

        if (currentExpiry.getExpiryDate() == null) {
            return updatedBatch.getExpiry();
        }

        if (batchToUpdate != null && batchToUpdate.getExpiry().equals(currentExpiry)) {
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

    /**
     * Stores the details to update the batch with. Each non-empty field value will replace the
     * corresponding field value of the batch.
     */
    public static class UpdateBatchDescriptor {
        private BatchNumber batchNumber;
        private Quantity quantity;
        private Expiry expiry;

        public UpdateBatchDescriptor() {}

        /**
         * Copy constructor.
         */
        public UpdateBatchDescriptor(UpdateBatchDescriptor toCopy) {
            setBatchNumber(toCopy.batchNumber);
            setQuantity(toCopy.quantity);
            setExpiry(toCopy.expiry);
        }

        public void setBatchNumber(BatchNumber batchNumber) {
            this.batchNumber = batchNumber;
        }

        public BatchNumber getBatchNumber() {
            return batchNumber;
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setExpiry(Expiry expiry) {
            this.expiry = expiry;
        }

        public Optional<Expiry> getExpiry() {
            return Optional.ofNullable(expiry);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof UpdateBatchDescriptor)) {
                return false;
            }

            // state check
            UpdateBatchDescriptor e = (UpdateBatchDescriptor) other;

            return getBatchNumber().equals(e.getBatchNumber())
                    && getQuantity().equals(e.getQuantity())
                    && getExpiry().equals(e.getExpiry());
        }
    }
}

