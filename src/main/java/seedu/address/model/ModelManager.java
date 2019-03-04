package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.exceptions.MenuItemNotFoundException;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.exceptions.OrderItemNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RestOrRant restOrRant;
    private final UserPrefs userPrefs;
    private final FilteredList<OrderItem> filteredOrderItems;
    private final FilteredList<MenuItem> filteredMenuItems;
    private final SimpleObjectProperty<OrderItem> selectedOrderItem = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<MenuItem> selectedMenuItem = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given restOrRant and userPrefs.
     */
    public ModelManager(ReadOnlyRestOrRant restOrRant, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(restOrRant, userPrefs);

        logger.fine("Initializing with Menu: " + restOrRant + " and user prefs " + userPrefs);

        this.restOrRant = new RestOrRant(restOrRant);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrderItems = new FilteredList<>(this.restOrRant.getOrders().getOrderItemList());
        filteredOrderItems.addListener(this::ensureSelectedOrderItemIsValid);
        filteredMenuItems = new FilteredList<>(this.restOrRant.getMenu().getMenuItemList());
        filteredMenuItems.addListener(this::ensureSelectedMenuItemIsValid);
    }

    public ModelManager() {
        this(new RestOrRant(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override // TODO: add for the other storages
    public Path getOrdersFilePath() {
        return userPrefs.getOrdersFilePath();
    }
    
    @Override
    public Path getMenuFilePath() {
        return userPrefs.getMenuFilePath(); 
    }
    
    @Override
    public void setOrdersFilePath(Path ordersFilePath) {
        requireNonNull(ordersFilePath);
        userPrefs.setOrdersFilePath(ordersFilePath);
    }
    
    @Override
    public void setMenuFilePath(Path menuFilePath) {
        requireNonNull(menuFilePath);
        userPrefs.setMenuFilePath(menuFilePath); 
    }

    //=========== RestOrRant ================================================================================

    @Override
    public void setRestOrRant(ReadOnlyRestOrRant restOrRant) {
        this.restOrRant.resetData(restOrRant.getOrders(), restOrRant.getMenu());
    }

    @Override
    public ReadOnlyRestOrRant getRestOrRant() {
        return restOrRant;
    }

    @Override
    public void updateRestOrRant() { // change mode
        restOrRant.indicateModified();
    }

    //=========== Order ======================================================================================

    @Override
    public boolean hasOrderItem(OrderItem orderItem) {
        requireNonNull(orderItem);
        return restOrRant.getOrders().hasOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(OrderItem target) {
        restOrRant.getOrders().removeOrderItem(target);
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        restOrRant.getOrders().addOrderItem(orderItem);
        updateFilteredOrderItemList(PREDICATE_SHOW_ALL_ORDER_ITEMS);
    }

    @Override
    public void setOrderItem(OrderItem target, OrderItem editedOrderItem) {
        requireAllNonNull(target, editedOrderItem);
        restOrRant.getOrders().setOrderItem(target, editedOrderItem);
    }
    
    //=========== Menu ======================================================================================
    @Override
    public boolean hasMenuItem(MenuItem menuItem) {
        requireNonNull(menuItem);
        return restOrRant.getMenu().hasMenuItem(menuItem);
    }
    
    @Override
    public void deleteMenuItem(MenuItem target) {
        restOrRant.getMenu().removeMenuItem(target);
    }
    
    @Override
    public void addMenuItem(MenuItem menuItem) {
        restOrRant.getMenu().addMenuItem(menuItem);
        updateFilteredMenuItemList(PREDICATE_SHOW_ALL_MENU_ITEMS);
    }
    
    @Override
    public void setMenuItem(MenuItem target, MenuItem editedItem) {
        requireAllNonNull(target, editedItem);
        
        restOrRant.getMenu().setMenuItem(target, editedItem);
    }

    //=========== Filtered Order Item List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code OrderItem} backed by the internal list of
     * {@code Orders}
     */
    @Override
    public ObservableList<OrderItem> getFilteredOrderItemList() {
        return filteredOrderItems;
    }

    @Override
    public void updateFilteredOrderItemList(Predicate<OrderItem> predicate) {
        requireNonNull(predicate);
        filteredOrderItems.setPredicate(predicate);
    }
    
    //=========== Filtered MenuItem List Accessors =============================================================
    
    /**
     * Returns an unmodifiable view of the list of {@code MenuItem} backed by the internal list of
     * {@code restOrRant}
     */
    @Override
    public ObservableList<MenuItem> getFilteredMenuItemList() {
        return filteredMenuItems;
    }
    
    @Override
    public void updateFilteredMenuItemList(Predicate<MenuItem> predicate) {
        requireNonNull(predicate);
        filteredMenuItems.setPredicate(predicate);
    }

    //=========== Selected order item ===========================================================================

    @Override
    public ReadOnlyProperty<OrderItem> selectedOrderItemProperty() {
        return selectedOrderItem;
    }

    @Override
    public OrderItem getSelectedOrderItem() {
        return selectedOrderItem.getValue();
    }

    @Override
    public void setSelectedOrderItem(OrderItem orderItem) {
        if (orderItem != null && !filteredOrderItems.contains(orderItem)) {
            throw new OrderItemNotFoundException();
        }
        selectedOrderItem.setValue(orderItem);
    }

    //=========== Selected menu item ===========================================================================
    
    @Override
    public ReadOnlyProperty<MenuItem> selectedMenuItemProperty() {
        return selectedMenuItem;
    }
    
    @Override
    public MenuItem getSelectedMenuItem() {
        return selectedMenuItem.getValue();
    }
    
    @Override
    public void setSelectedMenuItem(MenuItem menuItem) {
        if (menuItem != null && !filteredMenuItems.contains(menuItem)) {
            throw new MenuItemNotFoundException();
        }
        selectedMenuItem.setValue(menuItem);
    }

    /**
     * Ensures {@code selectedOrderItem} is a valid person in {@code filteredOrderItems}.
     */
    private void ensureSelectedOrderItemIsValid(ListChangeListener.Change<? extends OrderItem> change) {
        while (change.next()) {
            if (selectedOrderItem.getValue() == null) {
                // null is always a valid selected order item, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedOrderItemReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedOrderItem.getValue());
            if (wasSelectedOrderItemReplaced) {
                // Update selectedOrderItem to its new value.
                int index = change.getRemoved().indexOf(selectedOrderItem.getValue());
                selectedOrderItem.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedOrderItemRemoved = change.getRemoved().stream()
                    .anyMatch(removedOrderItem -> selectedOrderItem.getValue().isSameOrderItem(removedOrderItem));
            if (wasSelectedOrderItemRemoved) {
                // Select the order item that came before it in the list,
                // or clear the selection if there is no such order item.
                selectedOrderItem.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedMenuItem} is a valid menu item in {@code filteredMenuItems}.
     */
    private void ensureSelectedMenuItemIsValid(ListChangeListener.Change<? extends  MenuItem> change) {
        while (change.next()) {
            if (selectedMenuItem.getValue() == null) {
                // null is always a valid selected menu item, so we do not need to check that it is valid anymore.
                return;
            }
            
            boolean wasSelectedMenuItemReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedMenuItem.getValue());
            if (wasSelectedMenuItemReplaced) {
                // Update selectedMenuItem to its new value.
                int index = change.getRemoved().indexOf(selectedMenuItem.getValue());
                selectedMenuItem.setValue(change.getAddedSubList().get(index));
                continue;
            }
            
            boolean wasSelectedMenuItemRemoved = change.getRemoved().stream()
                    .anyMatch(removedMenuItem -> selectedMenuItem.getValue().isSameMenuItem(removedMenuItem));
            if (wasSelectedMenuItemRemoved) {
                // Select the menu item that came before it in the list,
                // or clear the selection if there is no such menu item.
                selectedMenuItem.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return restOrRant.equals(other.restOrRant)
                && userPrefs.equals(other.userPrefs)
                && filteredOrderItems.equals(other.filteredOrderItems)
                && Objects.equals(selectedOrderItem.get(), other.selectedOrderItem.get())
                && filteredMenuItems.equals(other.filteredMenuItems)
                && Objects.equals(selectedMenuItem.get(), other.selectedMenuItem.get());
    }

    @Override
    public void changeMode() {
        restOrRant.changeMode();
    }
}
