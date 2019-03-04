package seedu.address.model.order;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an order item in an Order from the RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OrderItem {

    private final int tableNumber;
    private final String menuItem; // TODO: implement MenuItem and change type
    private final int quantityOrdered; // TODO: implement ItemStatus and change type, quantity part of status

    /**
     * Every field must be present and not null.
     * TODO: create constructor with default status as unserved
     */
    public OrderItem(int tableNumber, String menuItem, int quantityOrdered) {
        requireAllNonNull(tableNumber, menuItem, quantityOrdered);
        this.tableNumber = tableNumber;
        this.menuItem = menuItem;
        this.quantityOrdered = quantityOrdered;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public String getMenuItem() {
        return menuItem;
    } // TODO: get the individual menu item details

    public int getQuantity() {
        return quantityOrdered;
    }

    /**
     * Returns true if both order items have the same menu item and quantity ordered.
     * This defines a weaker notion of equality between two order items.
     */
    public boolean isSameOrderItem(OrderItem otherOrderItem) {
        if (otherOrderItem == this) {
            return true;
        }

        return otherOrderItem != null
                && otherOrderItem.getQuantity() == getQuantity()
                && otherOrderItem.getMenuItem().equals(getMenuItem());
    }

    /**
     * Returns true if both order items have the same table number, menu item and quantity ordered.
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
        return isSameOrderItem(otherOrderItem) && otherOrderItem.getTableNumber() == getTableNumber();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tableNumber, menuItem, quantityOrdered);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Table ")
                .append(getTableNumber())
                .append("] ")
                .append(getMenuItem())
                .append(" | Qty Ordered: ")
                .append(getQuantity());
        return builder.toString();
    }

}
