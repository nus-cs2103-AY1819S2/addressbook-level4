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
import seedu.address.testutil.StatisticsBuilder;

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

    @Test
    public void execute_dailyRevenueAcceptedByModel_addSuccessful() throws Exception {

        BillCommandTest.ModelStubAcceptingDailyRevenueAdded modelStub =
                new BillCommandTest.ModelStubAcceptingDailyRevenueAdded();

        DailyRevenue validDailyRevenue = new StatisticsBuilder().withTotalDailyRevenue("$300.50").build();
        Bill validBill = new StatisticsBuilder().buildBill();

        CommandResult commandResult = new BillCommand(validBill).execute(
                Mode.MENU_MODE, modelStub, commandHistory);

        validBill = modelStub.updateBill(validBill);
        modelStub.updateDailyRevenue(validBill);

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
     * A Model stub that always accept the daily revenue being added.
     */
    private class ModelStubAcceptingDailyRevenueAdded extends BillCommandTest.ModelStub {
        final ArrayList<DailyRevenue> dailyRevenuesAdded = new ArrayList<>();

        /**
         * Updates the total daily revenue when a bill is calculated.
         */
        public void updateDailyRevenue(Bill bill) {
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
}
