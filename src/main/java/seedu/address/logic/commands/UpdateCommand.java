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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the batch details of the medicine identified "
            + "by the index number used in the displayed medicine list. Existing values will be overwritten by the "
            + "input values.\n"
            + "Parameters: INDEX "
            + PREFIX_BATCHNUMBER + "BATCH_NUMBER "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_EXPIRY + "EXPIRY_DATE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BATCHNUMBER + "HH-156224 "
            + PREFIX_QUANTITY + "40 "
            + PREFIX_EXPIRY + "13/11/2019 ";

    public static final String MESSAGE_SUCCESS = "Batch updated: %1$s";
    public static final String MESSAGE_MISSING_PARAMETER = "Must include batch number and quantity or expiry of the "
            + "batch used for updating.";
    public static final String MESSAGE_MAX_QUANTITY_EXCEEDED = "Max quantity exceeded. Max quantity: "
            + Quantity.MAX_QUANTITY;
    public static final String MESSAGE_EXPIRED_BATCH = "Expiry date has passed. Cannot set expiry to passed date";
    public static final String MESSAGE_NEW_BATCH_MISSING_PARAMETER = "Attempting to add a new batch. Must include "
            + "both expiry date and quantity for new batches.";
    public static final String MESSAGE_NEW_BATCH_ZERO_QUANTITY = "Attempting to add a new batch. Cannot add a new "
            + "batch with zero quantity.";

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
            assertCanAddNewBatchToMedicine(medicineToUpdate);
        }

        Batch updatedBatch = createUpdatedBatch(batchToUpdate);
        Medicine updatedMedicine = createUpdatedMedicine(medicineToUpdate, batchToUpdate, updatedBatch);

        model.setMedicine(medicineToUpdate, updatedMedicine);
        model.commitInventory();
        model.setSelectedMedicine(updatedMedicine);

        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedBatch));
    }

    /**
     * @throws CommandException if requirements needed to add a new batch is not met.
     */
    private void assertCanAddNewBatchToMedicine(Medicine medicine) throws CommandException {
        if (medicine.getBatches().size() == Medicine.MAX_SIZE_BATCH) {
            throw new CommandException(Medicine.MESSAGE_CONSTRAINTS_BATCHES);
        }

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
    private Batch createUpdatedBatch(Batch batchToUpdate) throws CommandException {
        Quantity quantity;
        Expiry expiry;

        if (newBatchDetails.getQuantity().isPresent()) {
            quantity = newBatchDetails.getQuantity().get();
        } else {
            quantity = batchToUpdate.getQuantity();
        }

        if (newBatchDetails.getExpiry().isPresent()) {
            expiry = newBatchDetails.getExpiry().get();
            if (expiry.isExpired()) {
                throw new CommandException(MESSAGE_EXPIRED_BATCH);
            }
        } else {
            expiry = batchToUpdate.getExpiry();
        }
        return new Batch(newBatchDetails.getBatchNumber(), quantity, expiry);
    }

    /**
     * Returns a {@code Medicine} with the details of {@code medicineToUpdate} updated with {@code updatedBatch}.
     */
    private Medicine createUpdatedMedicine(Medicine medicineToUpdate, Batch batchToUpdate, Batch updatedBatch) throws
            CommandException {
        Map<BatchNumber, Batch> updatedBatches = getUpdatedBatches(medicineToUpdate, updatedBatch);
        Quantity updatedQuantity = getUpdatedQuantity(medicineToUpdate, batchToUpdate, updatedBatch);
        Expiry updatedExpiry = getUpdatedExpiry(updatedBatches);

        return new Medicine(medicineToUpdate.getName(), medicineToUpdate.getCompany(), updatedQuantity, updatedExpiry,
                medicineToUpdate.getTags(), updatedBatches);
    }

    private Map<BatchNumber, Batch> getUpdatedBatches(Medicine medicineToUpdate, Batch updatedBatch) {
        HashMap<BatchNumber, Batch> updatedBatches = new HashMap<>(medicineToUpdate.getBatches());
        if (updatedBatch.getQuantity().getNumericValue() != 0) {
            updatedBatches.put(updatedBatch.getBatchNumber(), updatedBatch);
        } else {
            updatedBatches.remove(updatedBatch.getBatchNumber());
        }
        return updatedBatches;
    }

    private Quantity getUpdatedQuantity(Medicine medicineToUpdate, Batch batchToUpdate, Batch updatedBatch) throws
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

    private Expiry getUpdatedExpiry(Map<BatchNumber, Batch> updatedBatches) {
        if (updatedBatches.size() == 0) {
            return new Expiry("-");
        }

        return updatedBatches.values().stream().min(Comparator.comparing(Batch::getExpiry)).get().getExpiry();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.UpdateCommand // instanceof handles nulls
                && targetIndex.equals(((seedu.address.logic.commands.UpdateCommand) other).targetIndex)
                && newBatchDetails.equals(((seedu.address.logic.commands.UpdateCommand) other).newBatchDetails));
    }

    /**
     * Stores the details to update the batch with.
     * Each non-empty field value will replace the corresponding field value of the batch.
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

        /**
         * Return true if the required parameters for UpdateBatchDescriptor are missing.
         */
        public boolean hasMissingParameters() {
            return batchNumber == null || (expiry == null && quantity == null);
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
            return (other == this)
                    || (other instanceof UpdateBatchDescriptor
                    && getBatchNumber().equals(((UpdateBatchDescriptor) other).getBatchNumber())
                    && getQuantity().equals(((UpdateBatchDescriptor) other).getQuantity())
                    && getExpiry().equals(((UpdateBatchDescriptor) other).getExpiry()));
        }
    }
}

