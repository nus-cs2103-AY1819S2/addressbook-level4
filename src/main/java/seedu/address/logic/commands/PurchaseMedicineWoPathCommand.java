package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.record.MedicinePurchaseRecord;

/**
 * A command to record purchase of a medicine without a path
 */
public class PurchaseMedicineWoPathCommand extends PurchaseMedicineCommand {

    private final String medicineName;
    private final int quantity;
    private final BigDecimal cost;

    public PurchaseMedicineWoPathCommand(String medicineName, int quantity, BigDecimal cost) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        Optional<Medicine> medicine = model.findMedicine(medicineName);
        if (!medicine.isPresent()) {
            throw new CommandException("No such medicine found.");
        }
        try {
            medicine.get().addQuantity(quantity);
            model.reminderForMedicine(medicine.get());
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
        model.addRecord(new MedicinePurchaseRecord(medicine.get(), quantity, cost), Clock.systemDefaultZone());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (
                other instanceof PurchaseMedicineWoPathCommand
                && medicineName.equals(((PurchaseMedicineWoPathCommand) other).medicineName)
                && quantity == ((PurchaseMedicineWoPathCommand) other).quantity
                && cost.equals(((PurchaseMedicineWoPathCommand) other).cost));
    }
}
