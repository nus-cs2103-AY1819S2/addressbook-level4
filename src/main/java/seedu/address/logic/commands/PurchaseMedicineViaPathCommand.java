package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.Arrays;

/**
 * A command to record purchase of a medicine into the model
 */
public class PurchaseMedicineViaPathCommand extends PurchaseMedicineCommand {

    private final String[] path;
    private final int quantity;
    private final int cost;

    public PurchaseMedicineViaPathCommand(String[] path, int quantity, int cost) {
        this.path = path;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        //model handle purchase
        //model update purchase history
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (
                other instanceof PurchaseMedicineViaPathCommand
                && Arrays.equals(path, ((PurchaseMedicineViaPathCommand) other).path)
                && quantity == ((PurchaseMedicineViaPathCommand) other).quantity
                && cost == ((PurchaseMedicineViaPathCommand) other).cost);
    }
}
