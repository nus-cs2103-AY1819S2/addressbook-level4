package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

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
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        //model handle purchase
        //model records purchase
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
