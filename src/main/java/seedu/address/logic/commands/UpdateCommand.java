package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEDICINES;

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
 * Updates quantity of medicine in inventory with new batch information.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the medicine identified by the index number with new batch information. "
            + "Existing values will be overwritten. Batch with quantity 0 will be removed.\n"
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
    public static final String MESSAGE_MISSING_PARAMETER = "Must include batch number and quantity of the batch used "
            + "for updating.";
    public static final String MESSAGE_MISSING_EXPIRY = "Must include expiry date for new batches.";

    private final Batch toUpdate;
    private final Index targetIndex;

    /**
     * Creates an UpdateCommand to update the {@code Medicine} at the specified {@index} by updating {@code Batch}
     */
    public UpdateCommand(Index targetIndex, Batch batch) {
        requireNonNull(targetIndex);
        requireNonNull(batch);

        this.targetIndex = targetIndex;
        this.toUpdate = batch;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Medicine> lastShownList = model.getFilteredMedicineList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        Medicine medicineToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Batch updatedBatch = getUpdatedBatch(medicineToUpdate);
        Medicine updatedMedicine = createUpdatedMedicine(medicineToUpdate, updatedBatch);

        model.setMedicine(medicineToUpdate, updatedMedicine);
        model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedBatch));
    }

    Batch getUpdatedBatch(Medicine medicineToUpdate) throws CommandException {
        Map<BatchNumber, Batch> batchesToUpdate = medicineToUpdate.getBatches();
        Batch batchToUpdate = batchesToUpdate.get(toUpdate.getBatchNumber());
        Batch updatedBatch;

        if (toUpdate.getExpiry().toString().equals("-")) {
            try {
                updatedBatch = new Batch(toUpdate.getBatchNumber(), batchToUpdate.getExpiry(), toUpdate.getQuantity());
            } catch (NullPointerException e) {
                throw new CommandException(MESSAGE_MISSING_EXPIRY);
            }
        } else {
            updatedBatch = toUpdate;
        }
        return updatedBatch;
    }

    /**
     * Creates and returns a {@code Medicine} with the details of {@code medicineToUpdate}
     * updated with {@code toUpdate}.
     */
    Medicine createUpdatedMedicine(Medicine medicineToUpdate, Batch updatedBatch) {
        Map<BatchNumber, Batch> updatedBatches = getNewBatches(medicineToUpdate, updatedBatch);
        Quantity updatedQuantity = getNewQuantity(medicineToUpdate, updatedBatch);
        Expiry updatedExpiry = getNewExpiry(updatedBatches);

        return new Medicine(medicineToUpdate.getName(), updatedQuantity, updatedExpiry, medicineToUpdate.getCompany(),
                medicineToUpdate.getTags(), updatedBatches);
    }

    Map<BatchNumber, Batch> getNewBatches(Medicine medicineToUpdate, Batch updatedBatch) {
        HashMap<BatchNumber, Batch> newBatches = new HashMap<>(medicineToUpdate.getBatches());
        if (updatedBatch.getQuantity().value.equals("0")) {
            newBatches.remove(updatedBatch.getBatchNumber());
        } else {
            newBatches.put(updatedBatch.getBatchNumber(), updatedBatch);
        }
        return newBatches;
    }

    Quantity getNewQuantity(Medicine medicineToUpdate, Batch updatedBatch) {
        int quantity = medicineToUpdate.getQuantity().getNumericValue();
        Batch batchToUpdate = medicineToUpdate.getBatches().get(updatedBatch.getBatchNumber());

        if (batchToUpdate != null) {
            quantity -= batchToUpdate.getQuantity().getNumericValue();
        }
        quantity += updatedBatch.getQuantity().getNumericValue();
        return new Quantity(Integer.toString(quantity));
    }

    Expiry getNewExpiry(Map<BatchNumber, Batch> updatedBatches) {
        if (updatedBatches.size() > 0) {
            return updatedBatches.values().stream().min(Comparator.comparing(Batch::getExpiry)).get().getExpiry();
        }
        return new Expiry("-");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.UpdateCommand // instanceof handles nulls
                && targetIndex.equals(((seedu.address.logic.commands.UpdateCommand) other).targetIndex)
                && toUpdate.equals(((seedu.address.logic.commands.UpdateCommand) other).toUpdate));
    }
}
