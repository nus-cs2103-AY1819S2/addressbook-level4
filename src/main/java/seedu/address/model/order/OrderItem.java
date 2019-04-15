package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.menu.Code;
import seedu.address.model.menu.Name;
import seedu.address.model.table.TableNumber;

/**
 * Represents an order item in an Order from the RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OrderItem {

    private final TableNumber tableNumber;
    private final Code menuItemCode;
    private final Name menuItemName;
    private final OrderItemStatus itemStatus;

    /**
     * Every field must be present and not null.
     */
    public OrderItem(TableNumber tableNumber, Code menuItemCode, Name menuItemName, OrderItemStatus itemStatus) {
        requireAllNonNull(tableNumber, menuItemCode, menuItemName, itemStatus);
        this.tableNumber = tableNumber;
        this.menuItemCode = menuItemCode;
        this.menuItemName = menuItemName;
        this.itemStatus = itemStatus;
    }

    /**
     * Constructs a new order item with the identity of {@code itemToUpdate} but with the status modified.
     */
    public OrderItem(OrderItem itemToUpdate, OrderItemStatus newStatus) {
        requireAllNonNull(itemToUpdate, newStatus);
        this.tableNumber = itemToUpdate.getTableNumber();
        this.menuItemCode = itemToUpdate.getMenuItemCode();
        this.menuItemName = itemToUpdate.getMenuItemName();
        this.itemStatus = newStatus;
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public Code getMenuItemCode() {
        return menuItemCode;
    }

    public Name getMenuItemName() {
        return menuItemName;
    }

    public int getQuantityOrdered() {
        return itemStatus.getQuantityOrdered();
    }

    public int getQuantityToServe() {
        return itemStatus.getQuantityUnserved();
    }

    public OrderItemStatus getOrderItemStatus() {
        return itemStatus;
    }

    /**
     * Returns true if both order items have the same menu item code, menu item name and table number.
     * This defines a weaker notion of equality between two order items.
     */
    public boolean isSameOrderItem(OrderItem otherOrderItem) {
        if (otherOrderItem == this) {
            return true;
        }

        return otherOrderItem != null && otherOrderItem.getTableNumber().equals(getTableNumber())
                && otherOrderItem.getMenuItemCode().equals(getMenuItemCode()) && otherOrderItem.getMenuItemName()
                .equals(getMenuItemName());
    }

    /**
     * Returns true if both order items have the same table number, menu item code, menu item name and item status.
     * This defines a stronger notion of equality between two order items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OrderItem)) {
            return false;
        }

        OrderItem otherOrderItem = (OrderItem) other;
        return isSameOrderItem(otherOrderItem) && otherOrderItem.getOrderItemStatus().equals(getOrderItemStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tableNumber, menuItemCode, menuItemName, itemStatus);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Table ")
                .append(getTableNumber())
                .append("] ")
                .append(getMenuItemCode())
                .append(" ")
                .append(getMenuItemName())
                .append(" | ")
                .append(getOrderItemStatus());
        return builder.toString();
    }

}
