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
 * Adds an order item to Orders.
 */
public class ServeCommand extends Command {

    public static final String COMMAND_WORD = "serve";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the serving status of the specified item. "
            + "Parameters: ITEM_CODE [QUANTITY]\n" + "Example: " + COMMAND_WORD + " W09 2";

    public static final String MESSAGE_SUCCESS = "Order item served:\n%1$s";
    public static final String MESSAGE_FAILURE = "Unable to serve %1$s: %2$s";
    public static final String MESSAGE_INVALID_ITEM_CODE = "The item code is invalid.";
    public static final String MESSAGE_INVALID_ORDER_ITEM =
            "There is no order item with this item code in this table's order";
    public static final String MESSAGE_ZERO_QUANTITY = "Nothing is served.";

    private final Code itemCode;
    private final Integer itemQuantity;

    /**
     * Creates an ServeCommand to update the serving status of the specified order item.
     */
    public ServeCommand(Code itemCode, Integer itemQuantity) {
        requireAllNonNull(itemCode, itemQuantity);
        this.itemCode = itemCode;
        this.itemQuantity = itemQuantity;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        TableNumber tableNumber = model.getSelectedTable().getTableNumber();

        if (itemQuantity == 0) {
            return new CommandResult(MESSAGE_ZERO_QUANTITY);
        }

        Optional<MenuItem> menuItemOptional = model.getRestOrRant().getMenu().getItemFromCode(itemCode);
        if (!menuItemOptional.isPresent()) {
            throw new CommandException(String.format(MESSAGE_FAILURE, itemCode, MESSAGE_INVALID_ITEM_CODE));
        }
        Optional<OrderItem> orderItemOptional = model.getRestOrRant().getOrders().getOrderItem(tableNumber, itemCode);
        if (!orderItemOptional.isPresent()) {
            throw new CommandException(String.format(MESSAGE_FAILURE, itemCode, MESSAGE_INVALID_ORDER_ITEM));
        }

        OrderItem oldItem = orderItemOptional.get();
        OrderItem newItem;
        try {
            newItem = new OrderItem(oldItem, oldItem.getOrderItemStatus().serveQuantity(itemQuantity));
            model.setOrderItem(oldItem, newItem);
        } catch (IllegalArgumentException e) { // illegal quantity
            throw new CommandException(String.format(MESSAGE_FAILURE, itemCode, e.getMessage()));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newItem));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ServeCommand // instanceof handles nulls
                && itemCode.equals(((ServeCommand) other).itemCode) && itemQuantity.equals((
                        (ServeCommand) other).itemQuantity));
    }
}
