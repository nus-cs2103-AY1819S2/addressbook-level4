package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<MenuItem> PREDICATE_SHOW_ALL_MENU_ITEMS = unused -> true;
    Predicate<OrderItem> PREDICATE_SHOW_ALL_ORDER_ITEMS = unused -> true;

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
     * Changes the current mode of the RestOrRant.
     */
    void changeMode();

}
