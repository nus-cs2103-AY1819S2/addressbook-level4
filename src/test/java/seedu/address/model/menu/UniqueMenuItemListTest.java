package seedu.address.model.menu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.CHICKEN_WINGS;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.menu.exceptions.DuplicateMenuItemException;
import seedu.address.model.menu.exceptions.MenuItemNotFoundException;
import seedu.address.testutil.MenuItemBuilder;

public class UniqueMenuItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMenuItemList uniqueMenuItemList = new UniqueMenuItemList();

    @Test
    public void contains_nullMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.contains(null);
    }

    @Test
    public void contains_menuItemNotInList_returnsFalse() {
        assertFalse(uniqueMenuItemList.contains(CHICKEN_WINGS));
    }

    @Test
    public void contains_menuItemInList_returnsTrue() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        assertTrue(uniqueMenuItemList.contains(CHICKEN_WINGS));
    }

    @Test
    public void add_nullMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.add(null);
    }

    @Test
    public void add_duplicateMenuItem_throwsDuplicateMenuItemException() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        thrown.expect(DuplicateMenuItemException.class);
        uniqueMenuItemList.add(CHICKEN_WINGS);
    }

    @Test
    public void add_menuItemWithDifferentCode_returnsTrue() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        MenuItem newItem = new MenuItemBuilder(CHICKEN_WINGS).withCode(FRENCH_FRIES.getCode().itemCode).build();
        uniqueMenuItemList.add(newItem);
        assertTrue(uniqueMenuItemList.contains(newItem));
    }

    @Test
    public void add_menuItemWithSameCode_throwsDuplicateMenuItemException() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        MenuItem newItem = new MenuItemBuilder(FRENCH_FRIES).withCode(CHICKEN_WINGS.getCode().itemCode).build();
        thrown.expect(DuplicateMenuItemException.class);
        uniqueMenuItemList.add(newItem);
    }

    @Test
    public void add_newMenuItem_returnTrue() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        uniqueMenuItemList.add(FRENCH_FRIES);
        assertTrue(uniqueMenuItemList.contains(FRENCH_FRIES));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMenuItemList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void setMenuItem_nullTargetMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.setMenuItem(null, CHICKEN_WINGS);
    }

    @Test
    public void setMenuItem_nullEditedMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.setMenuItem(CHICKEN_WINGS, null);
    }

    @Test
    public void setMenuItem_targetMenuItemNotInList_throwsOrderItemNotFoundException() {
        thrown.expect(MenuItemNotFoundException.class);
        uniqueMenuItemList.setMenuItem(CHICKEN_WINGS, CHICKEN_WINGS);
    }

    @Test
    public void setMenuItem_editedMenuItemIsSameMenuItem_success() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        uniqueMenuItemList.setMenuItem(CHICKEN_WINGS, CHICKEN_WINGS);
        UniqueMenuItemList expectedUniqueMenuItemList = new UniqueMenuItemList();
        expectedUniqueMenuItemList.add(CHICKEN_WINGS);
        assertEquals(expectedUniqueMenuItemList, uniqueMenuItemList);
    }

    @Test
    public void setMenuItem_editedMenuItemHasSameIdentity_success() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        MenuItem editedMenuItem = new MenuItemBuilder(CHICKEN_WINGS).withName(CHICKEN_WINGS.getName().itemName)
                                          .withCode(CHICKEN_WINGS.getCode().itemCode)
                                          .withPrice(CHICKEN_WINGS.getPrice().itemPrice)
                                          .withQuantity(CHICKEN_WINGS.itemQuantityOrdered()).build();
        uniqueMenuItemList.setMenuItem(CHICKEN_WINGS, editedMenuItem);
        UniqueMenuItemList expectedUniqueMenuItemList = new UniqueMenuItemList();
        expectedUniqueMenuItemList.add(editedMenuItem);
        assertEquals(expectedUniqueMenuItemList, uniqueMenuItemList);
    }

    @Test
    public void setMenuItem_editedMenuItemHasDifferentIdentity_success() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        uniqueMenuItemList.setMenuItem(CHICKEN_WINGS, FRENCH_FRIES);
        UniqueMenuItemList expectedUniqueMenuItemList = new UniqueMenuItemList();
        expectedUniqueMenuItemList.add(FRENCH_FRIES);
        assertEquals(expectedUniqueMenuItemList, uniqueMenuItemList);
    }

    @Test
    public void setMenuItem_editedMenuItemHasNonUniqueIdentity_throwsDuplicateMenuItemException() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        uniqueMenuItemList.add(FRENCH_FRIES);
        thrown.expect(DuplicateMenuItemException.class);
        uniqueMenuItemList.setMenuItem(CHICKEN_WINGS, FRENCH_FRIES);
    }

    @Test
    public void remove_nullMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.remove(null);
    }

    @Test
    public void remove_menuItemDoesNotExist_throwsMenuItemNotFoundException() {
        thrown.expect(MenuItemNotFoundException.class);
        uniqueMenuItemList.remove(CHICKEN_WINGS);
    }

    @Test
    public void remove_existingMenuItem_removesMenuItem() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        uniqueMenuItemList.remove(CHICKEN_WINGS);
        UniqueMenuItemList expectedUniqueMenuItemList = new UniqueMenuItemList();
        assertEquals(expectedUniqueMenuItemList, uniqueMenuItemList);
    }

    @Test
    public void setMenuItems_nullUniqueMenuItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.setMenuItems((UniqueMenuItemList) null);
    }

    @Test
    public void setMenuItem_uniqueMenuItemList_replacesOwnListWithProvidedUniqueMenuItemList() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        UniqueMenuItemList expectedUniqueMenuItemList = new UniqueMenuItemList();
        expectedUniqueMenuItemList.add(FRENCH_FRIES);
        uniqueMenuItemList.setMenuItems(expectedUniqueMenuItemList);
        assertEquals(expectedUniqueMenuItemList, uniqueMenuItemList);
    }

    @Test
    public void setMenuItem_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMenuItemList.setMenuItems((List<MenuItem>) null);
    }

    @Test
    public void setMenuItem_list_replacesOwnListWithProvidedList() {
        uniqueMenuItemList.add(CHICKEN_WINGS);
        List<MenuItem> menuItemList = Collections.singletonList(FRENCH_FRIES);
        uniqueMenuItemList.setMenuItems(menuItemList);
        UniqueMenuItemList expectedUniqueMenuItemList = new UniqueMenuItemList();
        expectedUniqueMenuItemList.add(FRENCH_FRIES);
        assertEquals(expectedUniqueMenuItemList, uniqueMenuItemList);
    }

    @Test
    public void setMenuItem_listWithDuplicateMenuItem_throwsDuplicateOrderItemException() {
        List<MenuItem> listWithDuplicateMenuItem = Arrays.asList(CHICKEN_WINGS, CHICKEN_WINGS);
        thrown.expect(DuplicateMenuItemException.class);
        uniqueMenuItemList.setMenuItems(listWithDuplicateMenuItem);
    }

    @Test
    public void updateMenuItemQuantity_validQuantity() {
        uniqueMenuItemList.add(FRENCH_FRIES);
        uniqueMenuItemList.updateMenuItemQuantity(FRENCH_FRIES, 10);
        UniqueMenuItemList expectedMenuItemList = new UniqueMenuItemList();
        expectedMenuItemList.add(new MenuItem(FRENCH_FRIES.getName(), FRENCH_FRIES.getCode(), FRENCH_FRIES.getPrice(),
                FRENCH_FRIES.getQuantity() + 10));
        assertEquals(uniqueMenuItemList, expectedMenuItemList);
    }

}
