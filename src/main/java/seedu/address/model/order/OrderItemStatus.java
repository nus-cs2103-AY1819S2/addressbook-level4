package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents the current status of an {@code OrderItem}, including quantity ordered and quantity left to serve.
 */
public class OrderItemStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Quantities should be numbers between 0 and 2,000,000,000 inclusive.";

    public static final String MESSAGE_OVER_SERVING =
            "Cannot serve %1$s portion(s) when there is/are only %2$s portion(s) left to serve.";

    public static final String MESSAGE_OVER_ORDERING =
            "Cannot add %1$s portion(s) to order as maximum quantity ordered is 2,000,000,000.";

    // quantityUnserved is strictly lesser or equal to quantityOrdered
    private int quantityOrdered;
    private int quantityUnserved;

    /**
     * Constructs an {@code OrderItemStatus} with the quantities given in integers.
     *
     * @param quantityOrdered Quantity of the item ordered by the customer.
     * @param quantityUnserved Quantity of the item that has not been served yet.
     */
    public OrderItemStatus(int quantityOrdered, int quantityUnserved) {
        requireAllNonNull(quantityOrdered, quantityUnserved);
        checkArgument(isValidQuantities(quantityOrdered, quantityUnserved), MESSAGE_CONSTRAINTS);
        this.quantityOrdered = quantityOrdered;
        this.quantityUnserved = quantityUnserved;
    }

    /**
     * Constructs an {@code OrderItemStatus} with the quantity ordered given.
     *
     * @param quantityOrdered Quantity of the item ordered by the customer.
     */
    public OrderItemStatus(int quantityOrdered) {
        requireNonNull(quantityOrdered);
        checkArgument(isValidQuantity(quantityOrdered), MESSAGE_CONSTRAINTS);
        this.quantityOrdered = quantityOrdered;
        this.quantityUnserved = quantityOrdered;
    }

    /**
     * Constructs an {@code OrderItemStatus} with the quantities given in strings (mainly for storage).
     *
     * @param quantityOrdered Quantity of the item ordered by the customer.
     * @param quantityUnserved Quantity of the item that has not been served yet.
     */
    public OrderItemStatus(String quantityOrdered, String quantityUnserved) {
        requireAllNonNull(quantityOrdered, quantityUnserved);
        checkArgument(isValidQuantities(quantityOrdered, quantityUnserved), MESSAGE_CONSTRAINTS);
        this.quantityOrdered = Integer.parseInt(quantityOrdered);
        this.quantityUnserved = Integer.parseInt(quantityUnserved);
    }

    /**
     * Returns true if a given integer is a valid quantity.
     */
    public static boolean isValidQuantity(int quantity) {
        return quantity >= 0 && quantity <= 2000000000;
    }

    /**
     * Returns true if a given string represents a valid quantity.
     */
    public static boolean isValidQuantity(String quantity) {
        try {
            return isValidQuantity(Integer.parseInt(quantity));
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if the given quantities are valid.
     */
    public static boolean isValidQuantities(int quantityOrdered, int quantityToServe) {
        return isValidQuantity(quantityOrdered) && isValidQuantity(quantityToServe)
                && quantityOrdered >= quantityToServe;
    }

    /**
     * Returns true if the given quantities in strings are valid.
     */
    public static boolean isValidQuantities(String quantityOrdered, String quantityToServe) {
        try {
            return isValidQuantities(Integer.parseInt(quantityOrdered), Integer.parseInt(quantityToServe));
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if all the portions of the item have been served.
     */
    public boolean isAllServed() {
        return quantityUnserved == 0;
    }

    /**
     * Returns a new OrderItemStatus where the quantity to serve is updated with the given {@code quantity}.
     */
    public OrderItemStatus serveQuantity(int quantity) {
        if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        if (quantity > quantityUnserved) {
            throw new IllegalArgumentException(String.format(MESSAGE_OVER_SERVING, quantity, quantityUnserved));
        }
        return new OrderItemStatus(quantityOrdered, quantityUnserved - quantity);
    }

    /**
     * Returns a new OrderItemStatus where the quantity ordered is updated with the given {@code quantity}.
     */
    public OrderItemStatus orderQuantity(int quantity) {
        if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        if (quantity > 2000000000 - quantityOrdered) {
            throw new IllegalArgumentException(String.format(MESSAGE_OVER_ORDERING, quantity));
        }
        return new OrderItemStatus(quantityOrdered + quantity, quantityUnserved + quantity);
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public int getQuantityUnserved() {
        return quantityUnserved;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof OrderItemStatus
                && quantityOrdered == (((OrderItemStatus) other).quantityOrdered)
                && quantityUnserved == (((OrderItemStatus) other).quantityUnserved));
    }

    @Override
    public String toString() {
        return String.format("Ordered: %1$s, Remaining to serve: %2$s", quantityOrdered, quantityUnserved);
    }
}
