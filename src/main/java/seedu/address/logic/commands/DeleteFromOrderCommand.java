package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.TableNumber;

/**
 * Removes an order item from Orders.
 */
public class DeleteFromOrderCommand extends Command {

    public static final String COMMAND_WORD = "deleteFromOrder";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Removes the order item from the selected table's order. " + "Parameters: ITEM_CODE\n"
                    + "Example: " + COMMAND_WORD + " W09";

    public static final String MESSAGE_SUCCESS = "Order item deleted:\n%1$s";
    public static final String MESSAGE_INVALID_ITEM_CODE = "The item code [%1$s] is invalid";
    public static final String MESSAGE_INVALID_ORDER_ITEM =
            "There is no order item with item code [%1$s] in this table's order";

    private final Code itemCode;

    /**
     * Creates a DeleteFromOrderCommand to remove the order item specified by the item code.
     */
    public DeleteFromOrderCommand(Code itemCode) {
        requireAllNonNull(itemCode);
        this.itemCode = itemCode;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        TableNumber tableNumber = model.getSelectedTable().getTableNumber();

        Optional<MenuItem> itemOptional = model.getRestOrRant().getMenu().getItemFromCode(itemCode);
        if (!itemOptional.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_ITEM_CODE, itemCode));
        }
        Optional<OrderItem> itemToDelete = model.getRestOrRant().getOrders().getOrderItem(tableNumber, itemCode);
        if (!itemToDelete.isPresent()) {
            throw new CommandException(String.format(MESSAGE_INVALID_ORDER_ITEM, itemCode));
        }

        model.deleteOrderItem(itemToDelete.get());

        // model.updateFilteredOrderItemList(orderItem -> orderItem.getTableNumber().equals(tableNumber));
        return new CommandResult(String.format(MESSAGE_SUCCESS, itemToDelete.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteFromOrderCommand // instanceof handles nulls
                && itemCode.equals(((DeleteFromOrderCommand) other).itemCode));
    }
}
