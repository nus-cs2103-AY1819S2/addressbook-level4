package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.TableNumber;

/**
 * Adds an order to Orders.
 */
public class AddOrderCommand extends Command {

    public static final String COMMAND_WORD = "addToOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds order item(s) to the selected table's order. "
            + "Parameters: ITEM_CODE QUANTITY [ITEM_CODE QUANTITY]...\n"
            + "Example: " + COMMAND_WORD + " W09 2 C18 1 C02 1";

    public static final String MESSAGE_SUCCESS = "New order items added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_ORDER_ITEM = "This order item already exists in the table's order";
    public static final String MESSAGE_INVALID_ITEM_CODE = "The item code [%1$s] is invalid";

    private final List<String> itemCodes;
    private final List<Integer> itemQuantities;

    /**
     * Creates an AddOrderCommand to add order items specified by the item codes and quantities.
     */
    public AddOrderCommand(List<String> itemCodes, List<Integer> itemQuantities) {
        requireAllNonNull(itemCodes, itemQuantities);
        this.itemCodes = itemCodes;
        this.itemQuantities = itemQuantities;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (int i = 0; i < itemCodes.size(); i++) {
            Optional<MenuItem> itemOptional = model.getRestOrRant().getMenu().getItemFromCode(itemCodes.get(i));
            if (!itemOptional.isPresent()) {
                throw new CommandException(String.format(MESSAGE_INVALID_ITEM_CODE, itemCodes.get(i)));
            }
            OrderItem orderItem = new OrderItem(new TableNumber("1"), itemOptional.get(), itemQuantities.get(i));
            if (model.hasOrderItem(orderItem)) {
                throw new CommandException(MESSAGE_DUPLICATE_ORDER_ITEM);
            }
        }

        for (OrderItem orderItem : orderItems) {
            model.addOrderItem(orderItem);
        }
        model.updateOrders();
        return new CommandResult(String.format(MESSAGE_SUCCESS, orderItems));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddOrderCommand // instanceof handles nulls
                && itemCodes.equals(((AddOrderCommand) other).itemCodes)
                && itemQuantities.equals(((AddOrderCommand) other).itemQuantities));
    }
}
