package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.InvalidationListener;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.Orders;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.table.Tables;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class RestOrRant implements ReadOnlyRestOrRant {

    private final Menu menu;
    private final Orders orders;
    private final Tables tables;
    private final Statistics statistics;
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
        tables = new Tables();
        statistics = new Statistics();
    }

    public RestOrRant() {
    }

    /**
     * Creates a RestOrRant using the data in the {@code toBeCopied}.
     */
    public RestOrRant(ReadOnlyRestOrRant toBeCopied) {
        this();
        resetData(toBeCopied.getOrders(), toBeCopied.getMenu(), toBeCopied.getTables(), toBeCopied.getStatistics());
    }

    /**
     * Creates an RestOrRant using the data specified in {@code copyOrders, copyMenu, copyTables, copyStatistics}.
     */
    public RestOrRant(ReadOnlyOrders copyOrders, ReadOnlyMenu copyMenu, ReadOnlyTables copyTables,
                      ReadOnlyStatistics copyStatistics) {
        this();
        resetData(copyOrders, copyMenu, copyTables, copyStatistics);
    }

    /**
     * Resets the existing data of this {@code RestOrRant} with new data from {@code newOrders, newMenu, newTables,
     * newStatistics}.
     */
    public void resetData(ReadOnlyOrders newOrders, ReadOnlyMenu newMenu, ReadOnlyTables newTables,
                          ReadOnlyStatistics newStatistics) {
        requireAllNonNull(newOrders, newMenu, newTables, newStatistics);
        orders.setOrderItems(newOrders.getOrderItemList());
        menu.setMenuItems(newMenu.getMenuItemList());
        tables.setTables(newTables.getTableList());
        statistics.setDailyRevenues(newStatistics.getDailyRevenueList());
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
        return orders.getOrderItemList().size() + " order items"
                + "\n" + menu.getMenuItemList().size() + " menu items"
                + "\n" + tables.getTableList().size() + "tables"
                + "\n" + statistics.getDailyRevenueList().size() + " daily revenues recorded";
    }

    @Override
    public Orders getOrders() {
        return orders;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public Tables getTables() {
        return tables;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestOrRant // instanceof handles nulls
                && orders.equals(((RestOrRant) other).orders) && menu.equals(((RestOrRant) other).menu) && tables
                .equals(((RestOrRant) other).tables)) && statistics.equals(((RestOrRant) other).statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, menu, tables, statistics);
    }

}
