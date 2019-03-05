package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * A command to record purchase of a medicine without a path
 */
public class PurchaseMedicineWoPathCommand extends PurchaseMedicineCommand {

    private final String medicineName;
    private final int quantity;
    private final int cost;

    public PurchaseMedicineWoPathCommand(String medicineName, int quantity, int cost) {
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
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (
                other instanceof PurchaseMedicineWoPathCommand
                && medicineName.equals(((PurchaseMedicineWoPathCommand) other).medicineName)
                && quantity == ((PurchaseMedicineWoPathCommand) other).quantity
                && cost == ((PurchaseMedicineWoPathCommand) other).cost
                );
    }
}
