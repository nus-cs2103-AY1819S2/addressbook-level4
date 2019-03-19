package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestOrRant;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.MenuItemBuilder;

public class AddToMenuCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullMenuItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddToMenuCommand(null);
    }

    @Test
    public void execute_menuItemAcceptedByModel_addSuccessful() throws Exception {
        AddToMenuCommandTest.ModelStubAcceptingMenuItemAdded modelStub =
                new AddToMenuCommandTest.ModelStubAcceptingMenuItemAdded();
        MenuItem validMenuItem = new MenuItemBuilder().build();

        CommandResult commandResult = new AddToMenuCommand(validMenuItem).execute(
                Mode.MENU_MODE, modelStub, commandHistory);

        assertEquals(String.format(AddToMenuCommand.MESSAGE_SUCCESS, validMenuItem),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMenuItem), modelStub.menuItemsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateMenuItem_throwsCommandException() throws Exception {
        MenuItem validMenuItem = new MenuItemBuilder().build();
        AddToMenuCommand addToMenuCommand = new AddToMenuCommand(validMenuItem);
        AddToMenuCommandTest.ModelStub modelStub =
                new AddToMenuCommandTest.ModelStubWithMenuItem(validMenuItem);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddToMenuCommand.MESSAGE_DUPLICATE_MENU_ITEM);
        addToMenuCommand.execute(Mode.MENU_MODE, modelStub, commandHistory);
    }

    @Test
    public void equals() {
        MenuItem burger = new MenuItemBuilder().withName("Burger").build();
        MenuItem sandwich = new MenuItemBuilder().withName("Sandwich").build();
        AddToMenuCommand addBurgerCommand = new AddToMenuCommand(burger);
        AddToMenuCommand addSandwichCommand = new AddToMenuCommand(sandwich);

        // same object -> returns true
        assertTrue(addBurgerCommand.equals(addBurgerCommand));

        // same values -> returns true
        AddToMenuCommand addBurgerCommandCopy = new AddToMenuCommand(burger);
        assertTrue(addBurgerCommand.equals(addBurgerCommandCopy));

        // different types -> returns false
        assertFalse(addBurgerCommand.equals(1));

        // null -> returns false
        assertFalse(addBurgerCommand.equals(null));

        // different person -> returns false
        assertFalse(addBurgerCommand.equals(addSandwichCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getOrdersFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrdersFilePath(Path ordersFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTablesFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTablesFilePath(Path tablesFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMenuFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuFilePath(Path menuFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getStatisticsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatisticsFilePath(Path menuFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRestOrRant(ReadOnlyRestOrRant newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRestOrRant getRestOrRant() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMenuItem(MenuItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuItem(MenuItem target, MenuItem editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<MenuItem> getFilteredMenuItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMenuItemList(Predicate<MenuItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<MenuItem> selectedMenuItemProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public MenuItem getSelectedMenuItem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMenu() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOrderItem(OrderItem orderItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addOrderItem(OrderItem orderItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrderItem(OrderItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrderItem(OrderItem target, OrderItem editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<OrderItem> getFilteredOrderItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredOrderItemList(Predicate<OrderItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<OrderItem> selectedOrderItemProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public OrderItem getSelectedOrderItem() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedOrderItem(OrderItem orderItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateOrders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTable(Table table) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTable(Table table) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TableNumber addTable(TableStatus tableStatus) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTable(Table target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTable(Table target, Table editedItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Table> getFilteredTableList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTableList(Predicate<Table> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Table> selectedTableProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Table getSelectedTable() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedTable(Table table) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTables() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDailyRevenue(DailyRevenue dailyRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDailyRevenue(DailyRevenue dailyRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecentBill(Bill target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDailyRevenue(DailyRevenue target, DailyRevenue editedRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Bill getRecentBill() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DailyRevenue> getFilteredDailyRevenueList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDailyRevenueList(Predicate<DailyRevenue> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<DailyRevenue> selectedDailyRevenueProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Bill> recentBillProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public DailyRevenue getSelectedDailyRevenue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedDailyRevenue(DailyRevenue dailyRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<DailyRevenue> getDailyRevenueList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDailyRevenue(DailyRevenue dailyRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStatistics() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithMenuItem extends AddToMenuCommandTest.ModelStub {
        private final MenuItem menuItem;

        ModelStubWithMenuItem(MenuItem menuItem) {
            requireNonNull(menuItem);
            this.menuItem = menuItem;
        }

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            requireNonNull(menuItem);
            return this.menuItem.isSameMenuItem(menuItem);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingMenuItemAdded extends AddToMenuCommandTest.ModelStub {
        final ArrayList<MenuItem> menuItemsAdded = new ArrayList<>();

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            requireNonNull(menuItem);
            return menuItemsAdded.stream().anyMatch(menuItem::isSameMenuItem);
        }

        @Override
        public void addMenuItem(MenuItem menuItem) {
            requireNonNull(menuItem);
            menuItemsAdded.add(menuItem);
        }

        @Override
        public void updateMode() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRestOrRant getRestOrRant() {
            return new RestOrRant();
        }
    }

}
