package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.menu.Code;
import seedu.address.model.table.TableNumber;

/**
 * Wraps all order-related data
 * Duplicates are not allowed (by .isSameOrderItem comparison)
 */
public class Orders implements ReadOnlyOrders {

    private final UniqueOrderItemList orderItems;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orderItems = new UniqueOrderItemList();
    }

    public Orders() {
    }

    /**
     * Creates a new instance of RestOrRant orders using the order items in the {@code toBeCopied}
     */
    public Orders(ReadOnlyOrders toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order item list with {@code orderItems}.
     * {@code orderItems} must not contain duplicate order items.
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems.setOrderItems(orderItems);
        indicateModified();
    }

    /**
     * Resets the existing data of this RestOrRant's {@code Orders} with {@code newData}.
     */
    public void resetData(ReadOnlyOrders newData) {
        requireNonNull(newData);

        setOrderItems(newData.getOrderItemList());
    }

    //// order item-level operations

    /**
     * Returns true if a order item with the same identity as {@code orderItem} exists in the RestOrRant orders.
     */
    public boolean hasOrderItem(OrderItem orderItem) {
        requireNonNull(orderItem);
        return orderItems.contains(orderItem);
    }

    /**
     * Adds an order item to the RestOrRant's orders.
     * The order item must not already exist in the orders.
     */
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        indicateModified();
    }

    /**
     * Replaces the given order item {@code target} in the list with {@code editedOrderItem}.
     * {@code target} must exist in the RestOrRant's orders.
     * The order item identity of {@code editedOrderItem} must not be the same as another existing order item in orders.
     */
    public void setOrderItem(OrderItem target, OrderItem editedOrderItem) {
        requireNonNull(editedOrderItem);

        orderItems.setOrderItem(target, editedOrderItem);
        indicateModified();
    }

    /**
     * Retrieves the order item in the list with the table number {@code tableNumber} and item code {@code itemCode}.
     * {@code orderItem} must exist in the RestOrRant's orders.
     */
    public Optional<OrderItem> getOrderItem(TableNumber tableNumber, Code itemCode) {
        requireAllNonNull(tableNumber, itemCode);
        return orderItems.getOrderItem(tableNumber, itemCode);
    }

    /**
     * Removes {@code key} from this RestOrRant's {@code Orders}.
     * {@code key} must exist in the orders.
     */
    public void removeOrderItem(OrderItem key) {
        orderItems.remove(key);
        indicateModified();
    }

    /**
     * Removes all order items from the given {@code tableNumber} from this RestOrRant's {@code Orders}.
     */
    public void clearOrderItemsFrom(TableNumber tableNumber) {
        ArrayList<OrderItem> itemsToDelete = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getTableNumber().equals(tableNumber)) {
                itemsToDelete.add(orderItem);
            }
        }
        for (OrderItem item : itemsToDelete) {
            orderItems.remove(item);
        }
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the orders have been modified.
     */
    public void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return orderItems.asUnmodifiableObservableList().size() + " order items";
        // TODO: refine later
    }

    @Override
    public ObservableList<OrderItem> getOrderItemList() {
        return orderItems.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Orders // instanceof handles nulls
                && orderItems.equals(((Orders) other).orderItems));
    }

    @Override
    public int hashCode() {
        return orderItems.hashCode();
    }
}
