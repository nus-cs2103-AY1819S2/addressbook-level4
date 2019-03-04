package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.InvalidationListener;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.Orders;
import seedu.address.model.order.ReadOnlyOrders;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RestOrRant implements ReadOnlyRestOrRant {

    private final Menu menu;
    private final Orders orders;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        menu = new Menu();
        orders = new Orders();

    }

    public RestOrRant() {}

    /**
     * Creates an RestOrRant using the data in the {@code toBeCopied}
     */
    public RestOrRant(ReadOnlyRestOrRant toBeCopied) {
        this();
        resetData(toBeCopied.getOrders(), toBeCopied.getMenu());
    }
    
    /**
     * Creates an RestOrRant using the data specified in {@code copyOrders, copyMenu} // TODO: add more parameters
     */
    public RestOrRant(ReadOnlyOrders copyOrders, ReadOnlyMenu copyMenu) {
        this();
        resetData(copyOrders, copyMenu);
    }

    /**
     * Resets the existing data of this {@code RestOrRant} with new data from {@code newOrders, newMenu}. // TODO: add more parameters
     */
    public void resetData(ReadOnlyOrders newOrders, ReadOnlyMenu newMenu) {
        requireAllNonNull(newOrders, newMenu);
        orders.setOrderItems(newOrders.getOrderItemList());
        // TODO: add more lines to set all the variables
        menu.setMenuItems(newMenu.getMenuItemList());
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
     * Notifies listeners that the restOrRant has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.getOrderItemList().size() + " order items" + "\n" + 
                menu.getMenuItemList().size() + " menu items";
        // TODO: refine later
    }

    public Orders getOrders() {
        return orders;
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestOrRant // instanceof handles nulls
                && orders.equals(((RestOrRant) other).orders)
                && menu.equals(((RestOrRant) other).menu));
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, menu);
    }

}
