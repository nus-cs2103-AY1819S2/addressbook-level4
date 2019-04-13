package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<MenuItem> PREDICATE_SHOW_ALL_MENU_ITEMS = unused -> true;
    Predicate<OrderItem> PREDICATE_SHOW_ALL_ORDER_ITEMS = unused -> true;
    Predicate<Table> PREDICATE_SHOW_ALL_TABLES = unused -> true;
    Predicate<Revenue> PREDICATE_SHOW_ALL_REVENUE = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' Orders file path.
     */
    Path getOrdersFilePath();

    /**
     * Sets the user prefs' Orders file path.
     */
    void setOrdersFilePath(Path ordersFilePath);

    /**
     * Returns the user pref's Menu file path.
     */
    Path getMenuFilePath();

    /**
     * Sets the user pref's Menu file path.
     */
    void setMenuFilePath(Path menuFilePath);

    /**
     * Returns the user prefs' Tables file path.
     */
    Path getTablesFilePath();

    /**
     * Sets the user pref's Tables file path.
     */
    void setTablesFilePath(Path tablesFilePath);

    /**
     * Returns the user prefs' statistics file path.
     */
    Path getStatisticsFilePath();

    /**
     * Sets the user pref's statistics file path.
     */
    void setStatisticsFilePath(Path statisticsFilePath);

    /**
     * Returns the RestOrRant
     */
    ReadOnlyRestOrRant getRestOrRant();

    /**
     * Replaces RestOrRant data with the data in {@code restOrRant}.
     */
    void setRestOrRant(ReadOnlyRestOrRant restOrRant);

    /**
     * Returns true if a table with the same identity as {@code table} exists in the RestOrRant's Tables.
     */
    boolean hasTable(Table table);

    /**
     * Deletes the given table from Tables.
     * The table must  exist in the Tables.
     */
    void deleteTable(Table table);

    /**
     * Adds the given table to Tables.
     * {@code table} must not already exist in Tables.
     */
    void addTable(Table table);

    /**
     * Adds the table with given TableNumber to Tables.
     * {@code table} must not already exist in Tables
     * Returns TableNumber of table added.
     */
    TableNumber addTable(TableStatus tableStatus);

    /**
     * Replaces the given table {@code target} with {@code editedTable}.
     * {@code target} must exisdt in the RestOrRant's Tables.
     * The table identity og {@code editedTable} must not be the same as  another existing table in Tables.
     */
    void setTable(Table target, Table editedTable);

    /**
     * Replaces the current list of tables with the new one {@code tables}.
     */
    void setTables(List<Table> tables);

    /**
     * Checks and returns true if all tables are unoccupied.
     */
    boolean isRestaurantEmpty();

    /**
     * Returns an unmodifiable view of the filtered table list
     */
    ObservableList<Table> getFilteredTableList();

    /**
     * Updates the filter of the filtered table list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null/
     */
    void updateFilteredTableList(Predicate<Table> predicate);

    /**
     * Selected person in the filtered table list.
     * null if no table is selected.
     */
    ReadOnlyProperty<Table> selectedTableProperty();

    /**
     * Returns the selected table in the filtered table list.
     * null if no table is selected.
     */
    Table getSelectedTable();

    /**
     * Sets the selected table in the filtered table list.
     *
     * @param table
     */
    void setSelectedTable(Table table);

    /**
     * Notifies the listeners that the RestOrRant's tables has been modified
     */
    void updateTables();

    /**
     * Returns true if an order item with the same identity as {@code orderItem} exists in the RestOrRant's Orders.
     */
    boolean hasOrderItem(OrderItem orderItem);

    /**
     * Deletes the given order item from Orders.
     * The order item must exist in the RestOrRant's Orders.
     */
    void deleteOrderItem(OrderItem target);

    /**
     * Clears all order items from the given table number from Orders.
     */
    void clearOrderItemsFrom(TableNumber tableNumber);

    /**
     * Adds the given order item to Orders.
     * {@code orderItem} must not already exist in the RestOrRant's Orders.
     */
    void addOrderItem(OrderItem orderItem);

    /**
     * Replaces the given order item {@code target} with {@code editedOrderItem}.
     * {@code target} must exist in the RestOrRant's orders.
     * The order item identity of {@code editedOrderItem} must not be the same as another existing order item in Orders.
     */
    void setOrderItem(OrderItem target, OrderItem editedOrderItem);

    /**
     * Returns an unmodifiable view of the filtered order item list
     */
    ObservableList<OrderItem> getFilteredOrderItemList();

    /**
     * Updates the filter of the filtered order item list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderItemList(Predicate<OrderItem> predicate);

    /**
     * Selected person in the filtered order item list.
     * null if no order item is selected.
     */
    ReadOnlyProperty<OrderItem> selectedOrderItemProperty();

    /**
     * Returns the selected order item in the filtered order item list.
     * null if no order item is selected.
     */
    OrderItem getSelectedOrderItem();

    /**
     * Sets the selected order item in the filtered order item list.
     */
    void setSelectedOrderItem(OrderItem orderItem);

    /**
     * Notifies the listeners that the RestOrRant orders has been modified to update the storage.
     */
    void updateOrders();

    /**
     * Returns true if a menu item with the same identity as {@code menuItem} exists in the menu.
     */
    boolean hasMenuItem(MenuItem menuItem);

    /**
     * Deletes the given menu item.
     * The menu item must exist in the menu.
     */
    void deleteMenuItem(MenuItem menuItem);

    /**
     * Adds the given menu item to the menu.
     * {@code menuItem} must not already exist in the menu.
     */
    void addMenuItem(MenuItem menuItem);

    /**
     * Replaces the given menu item {@code target} with {@code editedItem}.
     * {@code target} must exist in the menu.
     * The item identity of {@code editedItem} must not be the same as another existing menu item in the menu.
     */
    void setMenuItem(MenuItem target, MenuItem editedItem);

    /**
     * Returns an unmodifiable view of the filtered menu item list
     */
    ObservableList<MenuItem> getFilteredMenuItemList();

    /**
     * Returns an unmodifiable view of the filtered menu item list sorted by quantity ordered (decreasing)
     */
    ObservableList<MenuItem> getFilteredSortedMenuItemList();

    /**
     * Replaces all menu items with {@code menuItems}.
     */
    void setMenuItems(List<MenuItem> menuItems);

    /**
     * Updates the filter of the filtered menu item list to filter by the given {@code predicate}
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMenuItemList(Predicate<MenuItem> predicate);

    /**
     * Selected menu item in the menu item list.
     * null if no menu item is selected.
     */
    ReadOnlyProperty<MenuItem> selectedMenuItemProperty();

    /**
     * Returns the selected menu item in the filtered menu item list.
     * null if no person is selected.
     */
    MenuItem getSelectedMenuItem();

    /**
     * Sets the selected menu item in the filtered menu item list.
     */
    void setSelectedMenuItem(MenuItem menuItem);

    /**
     * Notifies the listeners that the RestOrRant menu has been modified to update the storage.
     */
    void updateMenu();

    /**
     * Returns true if a revenue with the same identity as {@code revenue} exists in the statistics.
     */
    boolean hasRevenue(Revenue revenue);

    /**
     * Deletes the given revenue item from Statistics.
     * The revenue must exist in the RestOrRant's Statistics.
     */
    void deleteRevenue(Revenue target);

    /**
     * Adds the given revenue to the revenue list.
     */
    void addRevenue(Revenue revenue);

    /**
     * Replaces the given revenue {@code target} with {@code editedRevenue}.
     * {@code target} must exist in the revenue list.
     */
    void setRevenue(Revenue target, Revenue editedRevenue);

    /**
     * Returns an unmodifiable view of the filtered revenue list
     */
    ObservableList<Revenue> getFilteredRevenueList();

    /**
     * Updates the filter of the filtered revenue list to filter by the given {@code predicate}
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRevenueList(Predicate<Revenue> predicate);

    /**
     * Selected  revenue in the revenue list.
     * null if no revenue is selected.
     */
    ReadOnlyProperty<Revenue> selectedRevenueProperty();

    /**
     * Returns the selected revenue in the filtered revenue list.
     * null if no Revenue is selected.
     */
    Revenue getSelectedRevenue();

    /**
     * Sets the selected revenue in the filtered revenue list.
     */
    void setSelectedRevenue(Revenue revenue);

    /**
     * Get the Revenue list
     */
    ObservableList<Revenue> getRevenueList();

    /**
     * Recent bill.
     * null if no bill is selected.
     */
    ReadOnlyProperty<Bill> recentBillProperty();

    /**
     * Gets the recent Bill.
     */
    Bill getRecentBill();

    /**
     * Sets the selected Bill to be the recent bill.
     */
    void setRecentBill(Bill bill);

    //    /**
    //     * Resets the RestOrRant Orders to only contain the orderItemList.
    //     */
    //    void billUpdateOrders(ObservableList<OrderItem> orderItemList);

    /**
     * Notifies the listeners that the RestOrRant statistics has been modified to update the storage.
     */
    void updateStatistics();
}
