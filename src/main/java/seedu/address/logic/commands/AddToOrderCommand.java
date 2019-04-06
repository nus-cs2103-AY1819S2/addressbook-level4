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
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.OrderItemStatus;
import seedu.address.model.table.TableNumber;

/**
 * Adds an order item to Orders.
 */
public class AddToOrderCommand extends Command {

    public static final String COMMAND_WORD = "addToOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds order item(s) to the selected table's order. "
            + "Parameters: ITEM_CODE QUANTITY [ITEM_CODE QUANTITY]...\n" + "Example: " + COMMAND_WORD
            + " W09 2 C18 1 C02 1";

    public static final String MESSAGE_SUCCESS = "Order items added or updated:\n%1$s";
    public static final String MESSAGE_FAILURE = "Unable to add %1$s: %2$s\nAll items after this were not added.";
    public static final String MESSAGE_INVALID_ITEM_CODE = "The item code is invalid.";

    private final List<Code> itemCodes;
    private final List<Integer> itemQuantities;

    /**
     * Creates an AddToOrderCommand to add order items specified by the item codes and quantities.
     */
    public AddToOrderCommand(List<Code> itemCodes, List<Integer> itemQuantities) {
        requireAllNonNull(itemCodes, itemQuantities);
        this.itemCodes = itemCodes;
        this.itemQuantities = itemQuantities;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<OrderItem> orderItems = new ArrayList<>();
        TableNumber tableNumber = model.getSelectedTable().getTableNumber();

        for (int i = 0; i < itemCodes.size(); i++) {
            if (itemQuantities.get(i) == 0) {
                continue;
            }

            Optional<MenuItem> menuItemOptional = model.getRestOrRant().getMenu().getItemFromCode(itemCodes.get(i));
            if (!menuItemOptional.isPresent()) {
                model.updateFilteredOrderItemList(orderItem -> orderItem.getTableNumber().equals(tableNumber));
                throw new CommandException(String.format(MESSAGE_FAILURE, itemCodes.get(i), MESSAGE_INVALID_ITEM_CODE));
            }
            Optional<OrderItem> orderItemOptional = model.getRestOrRant().getOrders().getOrderItem(tableNumber,
                    itemCodes.get(i));

            try {
                OrderItem orderItem;
                if (!orderItemOptional.isPresent()) {
                    orderItem = new OrderItem(tableNumber, itemCodes.get(i),
                            model.getRestOrRant().getMenu().getNameFromItem(menuItemOptional.get()),
                            new OrderItemStatus(itemQuantities.get(i)));
                    model.addOrderItem(orderItem);
                } else { // add quantity to existing order item
                    OrderItem oldItem = orderItemOptional.get();
                    orderItem = new OrderItem(oldItem, oldItem.getOrderItemStatus()
                            .orderQuantity(itemQuantities.get(i)));
                    model.setOrderItem(oldItem, orderItem);
                }
                orderItems.add(orderItem);
            } catch (IllegalArgumentException e) { // illegal quantities
                throw new CommandException(String.format(MESSAGE_FAILURE, itemCodes.get(i), e.getMessage()));
            }
        }

        model.updateFilteredOrderItemList(orderItem -> orderItem.getTableNumber().equals(tableNumber));
        return new CommandResult(String.format(MESSAGE_SUCCESS, orderItems));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddToOrderCommand // instanceof handles nulls
                && itemCodes.equals(((AddToOrderCommand) other).itemCodes) && itemQuantities.equals((
                        (AddToOrderCommand) other).itemQuantities));
    }
}
