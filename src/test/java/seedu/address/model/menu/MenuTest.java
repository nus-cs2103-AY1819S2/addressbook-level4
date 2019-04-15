package seedu.address.model.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.CHEESE_NACHOS;
import static seedu.address.testutil.TypicalRestOrRant.CHICKEN_WINGS;
import static seedu.address.testutil.TypicalRestOrRant.CREPES;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;
import static seedu.address.testutil.TypicalRestOrRant.SALAD;
import static seedu.address.testutil.TypicalRestOrRant.SHRIMP_FRIED_RICE;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalMenuItems;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.menu.exceptions.DuplicateMenuItemException;
import seedu.address.testutil.MenuItemBuilder;

public class MenuTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Menu menu = new Menu();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), menu.getMenuItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        menu.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyMenu_replacesData() {
        Menu newData = new Menu();
        for (MenuItem menuItem : getTypicalMenuItems()) {
            newData.addMenuItem(menuItem);
        }
        menu.resetData(newData);
        assertEquals(newData, menu);
    }

    @Test
    public void resetData_withDuplicateMenuItems_throwsDuplicateMenuItemException() {
        // Two order items with the same identity fields
        MenuItem editedMenuItem = new MenuItemBuilder(FRENCH_FRIES).withName(FRENCH_FRIES.getName().itemName)
                                          .withCode(FRENCH_FRIES.getCode().itemCode)
                                          .withPrice(FRENCH_FRIES.getPrice().itemPrice).build();
        List<MenuItem> newMenuItems = Arrays.asList(FRENCH_FRIES, editedMenuItem);
        MenuTest.MenuStub newData = new MenuTest.MenuStub(newMenuItems);

        thrown.expect(DuplicateMenuItemException.class);
        menu.resetData(newData);
    }

    @Test
    public void hasMenuItem_nullOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        menu.hasMenuItem(null);
    }

    @Test
    public void hasMenuItem_menuItemNotInMenu_returnsFalse() {
        assertFalse(menu.hasMenuItem(CHEESE_NACHOS));
    }

    @Test
    public void hasMenuItem_menuItemInMenu_returnsTrue() {
        menu.addMenuItem(CHICKEN_WINGS);
        assertTrue(menu.hasMenuItem(CHICKEN_WINGS));
    }

    @Test
    public void hasMenuItem_menuItemWithSameIdentityFieldsInMenu_returnsTrue() {
        menu.addMenuItem(CREPES);
        MenuItem editedMenuItem = new MenuItemBuilder(CREPES).withName(CREPES.getName().itemName)
                                          .withCode(CREPES.getCode().itemCode).withPrice(CREPES.getPrice().itemPrice)
                                          .build();
        assertTrue(menu.hasMenuItem(editedMenuItem));
    }

    @Test
    public void getMenuItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        menu.getMenuItemList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        menu.addListener(listener);
        menu.addMenuItem(SHRIMP_FRIED_RICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        menu.addListener(listener);
        menu.removeListener(listener);
        menu.addMenuItem(SALAD);
        assertEquals(0, counter.get());
    }

    @Test
    public void updateMenuItemQuantity_zeroQuantity_throwsIllegalValueException() {
        thrown.expect(IllegalArgumentException.class);
        menu.updateMenuItemQuantity(FRENCH_FRIES, 0);
    }

    /**
     * A stub ReadOnlyOrders whose order item list can violate interface constraints.
     */
    private static class MenuStub implements ReadOnlyMenu {
        private final ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();

        MenuStub(Collection<MenuItem> menuItems) {
            this.menuItems.setAll(menuItems);
        }

        @Override
        public ObservableList<MenuItem> getMenuItemList() {
            return menuItems;
        }

        @Override
        public Optional<MenuItem> getItemFromCode(Code code) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Name getNameFromItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Code getCodeFromItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Price getPriceFromItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMenuItemQuantity(MenuItem menuItem, int quantity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuItems(List<MenuItem> menuItems) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
