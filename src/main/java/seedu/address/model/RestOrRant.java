package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.Orders;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.order.UniqueOrderItemList;
import seedu.address.model.table.Table;
import seedu.address.model.table.Tables;
import seedu.address.model.table.UniqueTableList;
import seedu.address.model.person.Person; // TODO: remove once the other components stop relying on person methods
import seedu.address.model.person.UniquePersonList; // TODO: remove once the other components stop relying on person methods

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RestOrRant implements ReadOnlyRestOrRant {

    private final Orders orders;
    private final Tables tables;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        orders = new Orders();
        tables = new Tables();
    }

    public RestOrRant() {}

    /**
     * Creates an RestOrRant using the data in the {@code toBeCopied}
     */
    public RestOrRant(ReadOnlyRestOrRant toBeCopied) {
        this();
        resetData(toBeCopied.getOrders());
    }

    /**
     * Creates an RestOrRant using the data specified in {@code copyOrders} // TODO: add more parameters
     */
    public RestOrRant(ReadOnlyOrders copyOrders) {
        this();
        resetData(copyOrders);
    }
    
    /**
     * Resets the existing data of this {@code RestOrRant} with new data from {@code newOrders}. // TODO: add more parameters
     */
    public void resetData(ReadOnlyOrders newOrders) {
        requireNonNull(newOrders);

        orders.setOrderItems(newOrders.getOrderItemList());
        // TODO: add more lines to set all the variables
    }

    public void changeMode() {
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
     * Notifies listeners that the RestOrRant has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.getOrderItemList().size() + " order items";
        // TODO: refine later
    }

    @Override
    public Orders getOrders() {
        return orders;
    }

    @Override
    public Tables getTables() {
        return tables;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestOrRant // instanceof handles nulls
                && orders.equals(((RestOrRant) other).orders));
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders);
    }
}
