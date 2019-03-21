package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestOrRant;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;
import seedu.address.model.table.UniqueTableList;
import seedu.address.model.table.exceptions.DuplicateTableException;
import seedu.address.model.table.exceptions.TableNotFoundException;
import seedu.address.testutil.MenuItemBuilder;
import seedu.address.testutil.StatisticsBuilder;
import seedu.address.testutil.TableBuilder;

public class BillCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new BillCommand(null);
    }

    /**
    @Test
    public void execute_dailyRevenueAcceptedByModel_addSuccessful() throws Exception {

        BillCommandTest.ModelStubAcceptingDailyRevenueAdded modelStub =
                new BillCommandTest.ModelStubAcceptingDailyRevenueAdded();

        DailyRevenue validDailyRevenue = new StatisticsBuilder().withDay("2").withMonth("3").withYear("2019")
                .withTotalDailyRevenue("300.50").build();
        Bill validBill = new StatisticsBuilder().withTableNumber("1").withDay("2").withMonth("3").withYear("2019")
                .withTotalBill("20.50").buildBill();

        CommandResult commandResult = new BillCommand(validBill).execute(
                Mode.TABLE_MODE, modelStub, commandHistory);

        validBill = modelStub.updateBill(validBill);
        modelStub.updateDailyRevenueList(validBill);

        assertEquals(validBill, modelStub.getRecentBill());

        assertEquals(String.format(BillCommand.MESSAGE_SUCCESS, validBill),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validDailyRevenue), modelStub.dailyRevenuesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_dailyRevenueAcceptedByModel_updateSuccessful() throws Exception {

        BillCommandTest.ModelStubAcceptingDailyRevenueAdded modelStub =
                new BillCommandTest.ModelStubAcceptingDailyRevenueAdded();

        DailyRevenue validDailyRevenue = new StatisticsBuilder().withDay("2").withMonth("3").withYear("2019")
                .withTotalDailyRevenue("300.50").build();
        Bill validBill = new StatisticsBuilder().withTableNumber("1").withDay("2").withMonth("3").withYear("2019")
                .withTotalBill("20.50").buildBill();

        CommandResult commandResult = new BillCommand(validBill).execute(
                Mode.TABLE_MODE, modelStub, commandHistory);

        validBill = modelStub.updateBill(validBill);
        modelStub.updateDailyRevenueList(validBill);

        assertEquals(validBill, modelStub.getRecentBill());

        assertEquals(String.format(BillCommand.MESSAGE_SUCCESS, validBill),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validDailyRevenue), modelStub.dailyRevenuesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Bill bill1 = new StatisticsBuilder().withTotalBill("45.50").buildBill();
        Bill bill2 = new StatisticsBuilder().withTotalBill("42.75").buildBill();
        BillCommand addBill1Command = new BillCommand(bill1);
        BillCommand addBill2Command = new BillCommand(bill2);

        // same object -> returns true
        assertTrue(addBill1Command.equals(addBill1Command));

        // same values -> returns true
        BillCommand addBill1CommandCopy = new BillCommand(bill1);
        assertTrue(addBill1CommandCopy.equals(addBill1CommandCopy));

        // different types -> returns false
        assertFalse(addBill1CommandCopy.equals(1));

        // null -> returns false
        assertFalse(addBill1CommandCopy.equals(null));

        // different bill -> returns false
        assertFalse(addBill1CommandCopy.equals(addBill2Command));
    }
    */

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        private final Table table = new TableBuilder().build();
        private ReadOnlyRestOrRant restOrRant = new RestOrRantStub();

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
        public Path getMenuFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuFilePath(Path menuFilePath) {
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
        public Path getOrdersFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrdersFilePath(Path ordersFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getStatisticsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatisticsFilePath(Path statisticsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRestOrRant(ReadOnlyRestOrRant restOrRant) {
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
        public void addOrderItem(OrderItem orderItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOrderItem(OrderItem orderItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteOrderItem(OrderItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setOrderItem(OrderItem target, OrderItem editedOrderItem) {
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
        public boolean hasDailyRevenue(DailyRevenue dailyRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDailyRevenue(DailyRevenue target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTable(Table table) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TableNumber addTable(TableStatus tableStatus) {
            TableNumber addedTableNumber = restOrRant.getTables().addTable(tableStatus);
            return addedTableNumber;
        }

        @Override
        public boolean hasTable(Table table) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTable(Table target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTable(Table target, Table editedTable) {
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
            return table;
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
        public void addMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMenuItem(MenuItem menuItem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMenuItem(MenuItem target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMenuItem(MenuItem target, MenuItem editedMenuItem) {
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
        public void addDailyRevenue(DailyRevenue dailyRevenue) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDailyRevenue(DailyRevenue target, DailyRevenue editedDailyRevenue) {
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
        public ReadOnlyProperty<Bill> recentBillProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Bill getRecentBill() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRecentBill(Bill bill) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStatistics() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A default menu stub that has all methods failing, except getItemFromCode() which returns an empty Optional.
     */
    private class MenuStub implements ReadOnlyMenu {
        @Override
        public ObservableList<MenuItem> getMenuItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<MenuItem> getItemFromCode(Code code) {
            return Optional.empty();
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
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A menu stub that always returns an item for any given code.
     */
    private class MenuStubWithItemCodes extends MenuStub {
        @Override
        public Optional<MenuItem> getItemFromCode(Code code) {
            return Optional.of(new MenuItemBuilder().build());
        }
    }

    /**
     * A RestOrRant stub whose menu will return an item for any given code.
     */
    private class RestOrRantStubWithItemCodes extends RestOrRantStub {
        @Override
        public ReadOnlyMenu getMenu() {
            return new MenuStubWithItemCodes();
        }
    }

    /**
     * A default table stub that has all methods failing, except setTable which sets the targeted table as the
     * new table provided.
     */
    private class TableStub implements ReadOnlyTables {

        private int nextTableNumber = 1;
        private UniqueTableList tableList = new UniqueTableList();

        @Override
        public ObservableList<Table> getTableList() {
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
            tableList.add(new Table(new TableNumber(String.valueOf(nextTableNumber)), tableStatus));
            nextTableNumber++;
            return new TableNumber(String.valueOf(nextTableNumber - 1));
        }

        @Override
        public Optional<Table> getTableFromNumber(TableNumber tableNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTables(List<Table> tableList){
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTable(Table target, Table editedTable) {
            if (!target.isSameTable(editedTable)) {
                throw new DuplicateTableException();
            }
        }

        @Override
        public void removeTable(Table key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isOccupied(TableNumber tableNumber) {
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

    /**
     * A Model stub that always accept the table being added.
     */
    private class TableStubWithTableList extends TableStub {
        final ArrayList<Table> tableAdded = new ArrayList<>();
        //        @Override
        //        public ObservableList<Table> getTableList() {
        //            return tableAdded.add(new);
        //        }

        @Override
        public void addTable(Table table) {
            requireNonNull(table);
            tableAdded.add(table);
        }
    }

    /**
     * A Model stub that contains a single orderItem.
     */
    private class ModelStubWithOrderItem extends ModelStub {
        private final OrderItem orderItem;

        ModelStubWithOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            this.orderItem = orderItem;
        }

        @Override
        public ReadOnlyRestOrRant getRestOrRant() {
            return new RestOrRantStubWithItemCodes();
        }

        @Override
        public boolean hasOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            return this.orderItem.isSameOrderItem(orderItem);
        }
    }


    /**
     * A Model stub that always accept the daily revenue being added.
     */
    private class ModelStubAcceptingDailyRevenueAdded extends BillCommandTest.ModelStub {
        final ArrayList<DailyRevenue> dailyRevenuesAdded = new ArrayList<>();

        /**
         * Updates the total daily revenue list when a bill is calculated.
         */
        public void updateDailyRevenueList(Bill bill) {
            DailyRevenue dailyRevenue =
                    new DailyRevenue(bill.getDay(), bill.getMonth(), bill.getYear(), bill.getTotalBill());
            if (hasDailyRevenue(dailyRevenue)) {
                setDailyRevenue(dailyRevenue, new DailyRevenue(bill.getDay(), bill.getMonth(), bill.getYear(),
                        dailyRevenue.getTotalDailyRevenue() + bill.getTotalBill()));
            } else {
                addDailyRevenue(dailyRevenue);
            }
        }

        /**
         * Updates the total price of the bill.
         */
        public Bill updateBill(Bill bill) {
            final StringBuilder receipt = new StringBuilder();
            receipt.append("Table ").append(getSelectedTable().getTableNumber()).append("\n");
            float totalBill = 0;
            for (OrderItem orderItem : getFilteredOrderItemList()) {
                MenuItem menuItem = getRestOrRant().getMenu().getItemFromCode(orderItem.getMenuItemCode()).get();
                receipt.append(menuItem.getCode().itemCode)
                        .append("  ")
                        .append(menuItem.getName().itemName)
                        .append("\n $")
                        .append(menuItem.getPrice().itemPrice)
                        .append("   x ")
                        .append(orderItem.getQuantity())
                        .append("\n")
                        .append("Total Bill: $ ")
                        .append(bill.getTotalBill())
                        .append("\n");
                totalBill += Float.parseFloat(menuItem.getPrice().toString()) * orderItem.getQuantity();

            }

            return new Bill(bill.getDay(), bill.getMonth(), bill.getYear(), bill.getTableNumber(),
                    totalBill, bill.getReceipt());
        }

        @Override
        public boolean hasDailyRevenue(DailyRevenue dailyRevenue) {
            requireNonNull(dailyRevenue);
            return dailyRevenuesAdded.stream().anyMatch(dailyRevenue::isSameDailyRevenue);
        }

        @Override
        public void addDailyRevenue(DailyRevenue dailyRevenue) {
            requireNonNull(dailyRevenue);
            dailyRevenuesAdded.add(dailyRevenue);
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

    /**
     * A default RestOrRant stub that has all of the methods failing, except getMenu() which returns an empty menu.
     */
    private class RestOrRantStub implements ReadOnlyRestOrRant {
        @Override
        public ReadOnlyMenu getMenu() {
            return new MenuStub();
        }

        @Override
        public ReadOnlyOrders getOrders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStatistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTables getTables() {
            return new TableStub();
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
