package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TABLES;
import static seedu.address.testutil.TypicalRestOrRant.CHICKEN_WINGS;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE2;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W09;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1_W12;
import static seedu.address.testutil.TypicalRestOrRant.TABLE2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.exceptions.MenuItemNotFoundException;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.exceptions.OrderItemNotFoundException;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.exceptions.DailyRevenueNotFoundException;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.exceptions.TableNotFoundException;
import seedu.address.testutil.MenuItemBuilder;
import seedu.address.testutil.OrderItemBuilder;
import seedu.address.testutil.RestOrRantBuilder;
import seedu.address.testutil.StatisticsBuilder;
import seedu.address.testutil.TableBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new RestOrRant(), new RestOrRant(modelManager.getRestOrRant()));
        assertEquals(null, modelManager.getSelectedOrderItem());
        assertEquals(null, modelManager.getSelectedMenuItem());
        assertEquals(null, modelManager.getSelectedTable());
        assertEquals(null, modelManager.getSelectedDailyRevenue());
        assertEquals(null, modelManager.getRecentBill());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setTablesFilePath(Paths.get("tables/file/path"));
        userPrefs.setOrdersFilePath(Paths.get("orders/file/path"));
        userPrefs.setMenuFilePath(Paths.get("menu/file/path"));
        userPrefs.setStatisticsFilePath(Paths.get("statistics/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setTablesFilePath(Paths.get("new/tables/file/path"));
        userPrefs.setOrdersFilePath(Paths.get("new/orders/file/path"));
        userPrefs.setMenuFilePath(Paths.get("new/menu/file/path"));
        userPrefs.setStatisticsFilePath(Paths.get("new/statistics/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setTablesFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setTablesFilePath(null);
    }

    @Test
    public void setTablesFilePath_validPath_setsTablesFilePath() {
        Path path = Paths.get("tables/file/path");
        modelManager.setTablesFilePath(path);
        assertEquals(path, modelManager.getTablesFilePath());
    }

    @Test
    public void setOrdersFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setOrdersFilePath(null);
    }

    @Test
    public void setOrdersFilePath_validPath_setsOrdersFilePath() {
        Path path = Paths.get("orders/file/path");
        modelManager.setOrdersFilePath(path);
        assertEquals(path, modelManager.getOrdersFilePath());
    }

    @Test
    public void setMenuFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setMenuFilePath(null);
    }

    @Test
    public void setMenuFilePath_validPath_setsMenuFilePath() {
        Path path = Paths.get("menu/file/path");
        modelManager.setMenuFilePath(path);
        assertEquals(path, modelManager.getMenuFilePath());
    }

    @Test
    public void setStatisticsFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setStatisticsFilePath(null);
    }

    @Test
    public void setStatisticsFilePath_validPath_setsStatisticsFilePath() {
        Path path = Paths.get("statistics/file/path");
        modelManager.setStatisticsFilePath(path);
        assertEquals(path, modelManager.getStatisticsFilePath());
    }

    @Test
    public void hasTable_nullTable_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasTable(null);
    }

    @Test
    public void hasTable_tableNotInTables_returnsFalse() {
        assertFalse(modelManager.hasTable(TABLE1));
    }

    @Test
    public void hasTable_tableInTables_returnsTrue() {
        modelManager.addTable(TABLE1);
        assertTrue(modelManager.hasTable(TABLE1));
    }

    @Test
    public void deleteTable_tableIsSelectedAndFirstTableInFilteredTablesList_selectionCleared() {
        modelManager.addTable(TABLE1);
        modelManager.setSelectedTable(TABLE1);
        modelManager.deleteTable(TABLE1);
        assertEquals(null, modelManager.getSelectedTable());
    }

    @Test
    public void deleteTable_tableIsSelectedAndSecondTableInFilteredTablesList_firstTableSelected() {
        modelManager.addTable(TABLE1);
        modelManager.addTable(TABLE2);
        assertEquals(Arrays.asList(TABLE1, TABLE2), modelManager.getFilteredTableList());
        modelManager.setSelectedTable(TABLE2);
        modelManager.deleteTable(TABLE2);
        assertEquals(TABLE1, modelManager.getSelectedTable());
    }

    @Test
    public void setTable_tableIsSelected_selectedTableUpdated() {
        modelManager.addTable(TABLE1);
        modelManager.setSelectedTable(TABLE1);
        Table updatedTable = new TableBuilder(TABLE1).withTableStatus("2/4").build();
        modelManager.setTable(TABLE1, updatedTable);
        assertEquals(updatedTable, modelManager.getSelectedTable());
    }

    @Test
    public void getFilteredTableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTableList().remove(0);
    }

    @Test
    public void setSelectedTable_tableNotInFilteredTableList_throwsTableNotFoundException() {
        thrown.expect(TableNotFoundException.class);
        modelManager.setSelectedTable(TABLE1);
    }

    @Test
    public void setSelectedTable_tableInFilteredTableList_setsSelectedTable() {
        modelManager.addTable(TABLE1);
        assertEquals(Collections.singletonList(TABLE1), modelManager.getFilteredTableList());
        modelManager.setSelectedTable(TABLE1);
        assertEquals(TABLE1, modelManager.getSelectedTable());
    }

    @Test
    public void hasOrderItem_nullOrderItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasOrderItem(null);
    }

    @Test
    public void hasOrderItem_orderItemNotInOrderItems_returnsFalse() {
        assertFalse(modelManager.hasOrderItem(TABLE1_W09));
    }

    @Test
    public void hasOrderItem_orderItemInOrderItems_returnsTrue() {
        modelManager.addOrderItem(TABLE1_W09);
        assertTrue(modelManager.hasOrderItem(TABLE1_W09));
    }

    @Test
    public void deleteOrderItem_orderItemIsSelectedAndFirstOrderItemInFilteredOrderItemsList_selectionCleared() {
        modelManager.addOrderItem(TABLE1_W09);
        modelManager.setSelectedOrderItem(TABLE1_W09);
        modelManager.deleteOrderItem(TABLE1_W09);
        assertEquals(null, modelManager.getSelectedOrderItem());
    }

    @Test
    public void deleteOrderItem_orderItemIsSelectedAndSecondOrderItemInFilteredOrderItemsList_firstOrderItemSelected() {
        modelManager.addOrderItem(TABLE1_W09);
        modelManager.addOrderItem(TABLE1_W12);
        assertEquals(Arrays.asList(TABLE1_W09, TABLE1_W12), modelManager.getFilteredOrderItemList());
        modelManager.setSelectedOrderItem(TABLE1_W12);
        modelManager.deleteOrderItem(TABLE1_W12);
        assertEquals(TABLE1_W09, modelManager.getSelectedOrderItem());
    }

    @Test
    public void setOrderItem_orderItemIsSelected_selectedOrderItemUpdated() {
        modelManager.addOrderItem(TABLE1_W09);
        modelManager.setSelectedOrderItem(TABLE1_W09);
        OrderItem updatedOrderItem = new OrderItemBuilder(TABLE1_W09).withQuantity(5).build();
        modelManager.setOrderItem(TABLE1_W09, updatedOrderItem);
        assertEquals(updatedOrderItem, modelManager.getSelectedOrderItem());
    }

    @Test
    public void getFilteredOrderItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredOrderItemList().remove(0);
    }

    @Test
    public void setSelectedOrderItem_orderItemNotInFilteredOrderItemList_throwsOrderItemNotFoundException() {
        thrown.expect(OrderItemNotFoundException.class);
        modelManager.setSelectedOrderItem(TABLE1_W09);
    }

    @Test
    public void setSelectedOrderItem_orderItemInFilteredOrderItemList_setsSelectedOrderItem() {
        modelManager.addOrderItem(TABLE1_W09);
        assertEquals(Collections.singletonList(TABLE1_W09), modelManager.getFilteredOrderItemList());
        modelManager.setSelectedOrderItem(TABLE1_W09);
        assertEquals(TABLE1_W09, modelManager.getSelectedOrderItem());
    }

    @Test
    public void hasMenuItem_nullMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasMenuItem(null);
    }

    @Test
    public void hasMenuItem_menuItemNotInMenuItems_returnsFalse() {
        assertFalse(modelManager.hasMenuItem(CHICKEN_WINGS));
    }

    @Test
    public void hasMenuItem_menuItemInMenuItems_returnsTrue() {
        modelManager.addMenuItem(CHICKEN_WINGS);
        assertTrue(modelManager.hasMenuItem(CHICKEN_WINGS));
    }

    @Test
    public void deleteMenuItem_menuItemIsSelectedAndFirstMenuItemInFilteredMenuItemsList_selectionCleared() {
        modelManager.addMenuItem(CHICKEN_WINGS);
        modelManager.setSelectedMenuItem(CHICKEN_WINGS);
        modelManager.deleteMenuItem(CHICKEN_WINGS);
        assertEquals(null, modelManager.getSelectedMenuItem());
    }

    @Test
    public void deleteMenuItem_menuItemIsSelectedAndSecondMenuItemInFilteredMenuItemsList_firstMenuItemSelected() {
        modelManager.addMenuItem(CHICKEN_WINGS);
        modelManager.addMenuItem(FRENCH_FRIES);
        assertEquals(Arrays.asList(CHICKEN_WINGS, FRENCH_FRIES), modelManager.getFilteredMenuItemList());
        modelManager.setSelectedMenuItem(FRENCH_FRIES);
        modelManager.deleteMenuItem(FRENCH_FRIES);
        assertEquals(CHICKEN_WINGS, modelManager.getSelectedMenuItem());
    }

    @Test
    public void setMenuItem_menuItemIsSelected_selectedMenuItemUpdated() {
        modelManager.addMenuItem(CHICKEN_WINGS);
        modelManager.setSelectedMenuItem(CHICKEN_WINGS);
        MenuItem updatedMenuItem = new MenuItemBuilder(CHICKEN_WINGS).withPrice("12.99").build();
        modelManager.setMenuItem(CHICKEN_WINGS, updatedMenuItem);
        assertEquals(updatedMenuItem, modelManager.getSelectedMenuItem());
    }

    @Test
    public void getFilteredMenuItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredMenuItemList().remove(0);
    }

    @Test
    public void setSelectedMenuItem_menuItemNotInFilteredMenuItemList_throwsMenuItemNotFoundException() {
        thrown.expect(MenuItemNotFoundException.class);
        modelManager.setSelectedMenuItem(CHICKEN_WINGS);
    }

    @Test
    public void setSelectedMenuItem_menuItemInFilteredMenuItemList_setsSelectedMenuItem() {
        modelManager.addMenuItem(CHICKEN_WINGS);
        assertEquals(Collections.singletonList(CHICKEN_WINGS), modelManager.getFilteredMenuItemList());
        modelManager.setSelectedMenuItem(CHICKEN_WINGS);
        assertEquals(CHICKEN_WINGS, modelManager.getSelectedMenuItem());
    }

    @Test
    public void hasDailyRevenue_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasDailyRevenue(null);
    }

    @Test
    public void hasDailyRevenue_dailyRevenueNotInDailyRevenues_returnsFalse() {
        assertFalse(modelManager.hasDailyRevenue(DAILY_REVENUE1));
    }

    @Test
    public void hasDailyRevenue_dailyRevenueInDailyRevenues_returnsTrue() {
        modelManager.addDailyRevenue(DAILY_REVENUE1);
        assertTrue(modelManager.hasDailyRevenue(DAILY_REVENUE1));
    }

    //    @Test TODO
    //    public void deleteDailyRevenue_firstDailyRevenueIsSelectedInFilteredDailyRevenueList_selectionCleared() {
    //        modelManager.addDailyRevenue(DAILY_REVENUE1);
    //        modelManager.setSelectedDailyRevenue(DAILY_REVENUE1);
    //        modelManager.deleteDailyRevenue(DAILY_REVENUE1);
    //        assertEquals(null, modelManager.getSelectedDailyRevenue());
    //    }

    //    @Test TODO
    //    public void deleteDailyRevenue_secondDailyRevenueIsSelectedInFilteredDailyRevenueList_firstDailyRevenueSelected() {
    //        modelManager.addDailyRevenue(DAILY_REVENUE1);
    //        modelManager.addDailyRevenue(DAILY_REVENUE2);
    //        assertEquals(Arrays.asList(DAILY_REVENUE1, DAILY_REVENUE2), modelManager.getFilteredDailyRevenueList());
    //        modelManager.setSelectedDailyRevenue(DAILY_REVENUE2);
    //        modelManager.deleteDailyRevenue(DAILY_REVENUE2);
    //        assertEquals(DAILY_REVENUE1, modelManager.getSelectedDailyRevenue());
    //    }

    @Test
    public void setDailyRevenue_dailyRevenueIsSelected_selectedDailyRevenueUpdated() {
        modelManager.addDailyRevenue(DAILY_REVENUE1);
        modelManager.setSelectedDailyRevenue(DAILY_REVENUE1);
        DailyRevenue updatedDailyRevenue =
                new StatisticsBuilder(DAILY_REVENUE1).withTotalDailyRevenue("150.60").build();
        modelManager.setDailyRevenue(DAILY_REVENUE1, updatedDailyRevenue);
        assertEquals(updatedDailyRevenue, modelManager.getSelectedDailyRevenue());
    }

    @Test
    public void getFilteredDailyRevenueList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredDailyRevenueList().remove(0);
    }

    @Test
    public void setSelectedDailyRevenue_dailyRevenueNotInFilteredList_throwsDailyRevenueNotFoundException() {
        thrown.expect(DailyRevenueNotFoundException.class);
        modelManager.setSelectedDailyRevenue(DAILY_REVENUE1);
    }

    @Test
    public void setSelectedDailyRevenue_dailyRevenueInFilteredDailyRevenueList_setsSelectedDailyRevenue() {
        modelManager.addDailyRevenue(DAILY_REVENUE1);
        assertEquals(Collections.singletonList(DAILY_REVENUE1), modelManager.getFilteredDailyRevenueList());
        modelManager.setSelectedDailyRevenue(DAILY_REVENUE1);
        assertEquals(DAILY_REVENUE1, modelManager.getSelectedDailyRevenue());
    }

    @Test
    public void equals() {
        RestOrRant restOrRant = new RestOrRantBuilder().withTable(TABLE1)
                .withTable(TABLE2)
                .withMenuItem(CHICKEN_WINGS)
                .withOrderItem(TABLE1_W09)
                .withDailyRevenue(DAILY_REVENUE1)
                .build();
        RestOrRant differentRestOrRant = new RestOrRant();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(restOrRant, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(restOrRant, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different restOrRant -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentRestOrRant, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredTableList(table -> table.getTableNumber().equals(new TableNumber("1")));
        assertFalse(modelManager.equals(new ModelManager(restOrRant, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTableList(PREDICATE_SHOW_ALL_TABLES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTablesFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(restOrRant, differentUserPrefs)));
    }
}
