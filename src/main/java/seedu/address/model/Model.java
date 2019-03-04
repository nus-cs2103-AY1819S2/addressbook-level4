package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
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
     * Sets the user prefs' Orders file path.
     */
    void setOrdersFilePath(Path restOrRantFilePath);

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
     * Deletes the given order item from Orders.
     * The order item must exist in the RestOrRant's Orders.
     */
    void deleteOrderItem(OrderItem target);

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

    /** Returns an unmodifiable view of the filtered order item list */
    ObservableList<OrderItem> getFilteredOrderItemList();

    /**
     * Updates the filter of the filtered order item list to filter by the given {@code predicate}.
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
     * Notifies the listeners that the RestOrRant orders has been modified.
     */
    void updateOrders();

    /**
     * Changes the current mode of the RestOrRant.
     */
    void changeMode();
}
