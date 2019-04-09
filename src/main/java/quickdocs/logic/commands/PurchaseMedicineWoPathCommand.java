package quickdocs.logic.commands;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;

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
        try {
            model.purchaseMedicine(medicineName, quantity, cost);
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
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
