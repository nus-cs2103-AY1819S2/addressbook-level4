package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<MenuItem> PREDICATE_SHOW_ALL_MENU_ITEMS = unused -> true;
    Predicate<OrderItem> PREDICATE_SHOW_ALL_ORDER_ITEMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Table> PREDICATE_SHOW_ALL_TABLES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

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
     * Returns the user pref's Menu file path.
     */
    Path getMenuFilePath();
    // TODO: add get file path for each feature

    /**
     * Returns the user prefs' Tables file path.
     */
    Path getTablesFilePath();

    /**
     * Sets the user prefs' Orders file path.
     */
    void setOrdersFilePath(Path restOrRantFilePath);

    /**
     * Sets the user pref's Menu file path.
     */
    void setMenuFilePath(Path menuFilePath);
    // TODO: add set file path for each feature

    /**
     * Replaces RestOrRant data with the data in {@code restOrRant}.
     */
    void setRestOrRant(ReadOnlyRestOrRant restOrRant);

    /** Returns the RestOrRant */
    ReadOnlyRestOrRant getRestOrRant();

    /**
     * Notifies the listeners that the RestOrRant (mode) has been modified.
     */
    void updateRestOrRant();

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
     */
    void addTable(TableNumber tableNumber);

    /**
     * Replaces the given table {@code target} with {@code editedTable}.
     * {@code target} must exisdt in the RestOrRant's Tables.
     * The table identity og {@code editedTable} must not be the same as  another existing table in Tables.
     */
    void setTable(Table target, Table editedTable);

    /** Returns an unmodifiable view of the filtered table list */
    ObservableList<Table> getFilteredTableList();

    /**
     * Updates the filter of the filtered table list to filter by the given {@code predicate}.
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
     * @param table
     */
    void setSelectedTable(Table table);

    /**
     * Returns true if an order item with the same identity as {@code orderItem} exists in the RestOrRant's Orders.
     */
    boolean hasOrderItem(OrderItem orderItem);
    
    /**
     * Returns true if a menu item with the same identity as {@code menuItem} exists in the menu.
     */
    boolean hasMenuItem(MenuItem menuItem);

    /**
     * Deletes the given order item from Orders.
     * The order item must exist in the RestOrRant's Orders.
     */
    void deleteOrderItem(OrderItem target);

    /**
     * Deletes the given menu item.
     * The menu item must exist in the menu.
     */
    void deleteMenuItem(MenuItem menuItem);

    /**
     * Adds the given order item to Orders.
     * {@code orderItem} must not already exist in the RestOrRant's Orders.
     */
    void addOrderItem(OrderItem orderItem);
    
    /**
     * Adds the given menu item to the menu.
     * {@code menuItem} must not already exist in the menu.
     */
    void addMenuItem(MenuItem menuItem);

    /**
     * Replaces the given order item {@code target} with {@code editedOrderItem}.
     * {@code target} must exist in the RestOrRant's orders.
     * The order item identity of {@code editedOrderItem} must not be the same as another existing order item in Orders.
     */
    void setOrderItem(OrderItem target, OrderItem editedOrderItem);

    /**
     * Replaces the given menu item {@code target} with {@code editedItem}.
     * {@code target} must exist in the menu.
     * The item identity of {@code editedItem} must not be the same as another existing menu item in the menu.
     */
    void setMenuItem(MenuItem target, MenuItem editedItem);

    /** Returns an unmodifiable view of the filtered order item list */
    ObservableList<OrderItem> getFilteredOrderItemList();

    /** Returns an unmodifiable view of the filtered menu item list */
    ObservableList<MenuItem> getFilteredMenuItemList();

    /**
     * Updates the filter of the filtered order item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderItemList(Predicate<OrderItem> predicate);
    
    /**
     * Updates the filter of the filtered menu item list to filter by the given {@code predicate}
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMenuItemList(Predicate<MenuItem> predicate);

    /**
     * Selected person in the filtered order item list.
     * null if no order item is selected.
     */
    ReadOnlyProperty<OrderItem> selectedOrderItemProperty();
    
    /**
     * Selected menu item in the menu item list.
     * null if no menu item is selected.
     */
    ReadOnlyProperty<MenuItem> selectedMenuItemProperty();

    /**
     * Returns the selected order item in the filtered order item list.
     * null if no order item is selected.
     */
    OrderItem getSelectedOrderItem();

    /**
     * Returns the selected menu item in the filtered menu item list.
     * null if no person is selected.
     */
    MenuItem getSelectedMenuItem();

    /**
     * Sets the selected order item in the filtered order item list.
     */
    void setSelectedOrderItem(OrderItem orderItem);

    /**
     * Sets the selected menu item in the filtered menu item list.
     */
    void setSelectedMenuItem(MenuItem menuItem);

    /** 
     * Notifies the listeners that the RestOrRant orders has been modified.
     */
    void updateOrders();

    /**
     * Changes the current mode of the RestOrRant.
     */
    void changeMode();

}
