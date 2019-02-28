package seedu.address.model.order.exceptions;

/**
 * Signals that the operation will result in duplicate OrderItems (OrderItems are considered duplicates if they
 * have the same identity).
 */
public class DuplicateOrderItemException extends RuntimeException {
    public DuplicateOrderItemException() {
        super("Operation would result in duplicate order items");
    }
}
