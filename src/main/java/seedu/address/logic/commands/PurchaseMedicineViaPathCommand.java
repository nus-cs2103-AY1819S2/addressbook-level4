package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A command to record purchase of a medicine into the model
 */
public class PurchaseMedicineViaPathCommand extends PurchaseMedicineCommand {

    private final String[] path;
    private final int quantity;
    private final BigDecimal cost;

    public PurchaseMedicineViaPathCommand(String[] path, int quantity, BigDecimal cost) {
        this.path = path;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        try {
            model.purchaseMedicine(path, quantity, cost);
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (
                other instanceof PurchaseMedicineViaPathCommand
                && Arrays.equals(path, ((PurchaseMedicineViaPathCommand) other).path)
                && quantity == ((PurchaseMedicineViaPathCommand) other).quantity
                && cost.equals(((PurchaseMedicineViaPathCommand) other).cost));
    }
}
