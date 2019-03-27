package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_FRIES;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.ReadOnlyUserPrefs;
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
import seedu.address.testutil.MenuItemBuilder;
import seedu.address.testutil.OrderItemBuilder;
import seedu.address.testutil.TableBuilder;

public class AddToOrderCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullItemCodes_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddToOrderCommand(null, new ArrayList<>());
    }

    @Test
    public void constructor_nullItemQuantities_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddToOrderCommand(new ArrayList<>(), null);
    }

    @Test
    public void execute_orderItemsAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingOrderItemAdded modelStub = new ModelStubAcceptingOrderItemAdded();
        List<Code> itemCodes = new ArrayList<>();
        itemCodes.add(new Code(VALID_CODE_CHICKEN));
        List<Integer> itemQuantities = new ArrayList<>();
        itemQuantities.add(3);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItemBuilder().build());

        // adding single order item
        CommandResult commandResult =
                new AddToOrderCommand(itemCodes, itemQuantities).execute(Mode.TABLE_MODE, modelStub, commandHistory);

        assertEquals(String.format(AddToOrderCommand.MESSAGE_SUCCESS, orderItems), commandResult.getFeedbackToUser());
        assertEquals(orderItems, modelStub.orderItemsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // adding multiple order items
        modelStub = new ModelStubAcceptingOrderItemAdded();
        itemCodes.add(new Code(VALID_CODE_FRIES));
        itemQuantities.add(3);
        orderItems.add(new OrderItemBuilder().withCode(VALID_CODE_FRIES).build());
        commandResult =
                new AddToOrderCommand(itemCodes, itemQuantities).execute(Mode.TABLE_MODE, modelStub, commandHistory);

        assertEquals(String.format(AddToOrderCommand.MESSAGE_SUCCESS, orderItems), commandResult.getFeedbackToUser());
        assertEquals(orderItems, modelStub.orderItemsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_invalidItemCode_throwsCommandException() throws Exception {
        List<Code> itemCodes = Collections.singletonList(new Code(VALID_CODE_CHICKEN));
        List<Integer> itemQuantities = Collections.singletonList(3);
        AddToOrderCommand addToOrderCommand = new AddToOrderCommand(itemCodes, itemQuantities);
        ModelStub modelStub = new ModelStubWithoutItemCode();

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(AddToOrderCommand.MESSAGE_INVALID_ITEM_CODE, new Code(VALID_CODE_CHICKEN)));
        addToOrderCommand.execute(Mode.TABLE_MODE, modelStub, commandHistory);
    }

    @Test
    public void execute_existingOrderItem_addSuccessful() throws Exception {
        List<Code> itemCodes = Collections.singletonList(new Code(VALID_CODE_CHICKEN));
        List<Integer> itemQuantities = Collections.singletonList(3);
        OrderItem validOrderItem = new OrderItemBuilder().build();
        ModelStubWithOrderItem modelStub = new ModelStubWithOrderItem(validOrderItem);

        CommandResult commandResult =
                new AddToOrderCommand(itemCodes, itemQuantities).execute(Mode.TABLE_MODE, modelStub, commandHistory);

        assertEquals(String.format(AddToOrderCommand.MESSAGE_SUCCESS, Collections.singletonList(validOrderItem)),
                commandResult.getFeedbackToUser());
        assertEquals(new OrderItemBuilder().withQuantity(6).build(), modelStub.getOrderItem());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        List<Code> codeW09 = Collections.singletonList(new Code(VALID_CODE_CHICKEN));
        List<Integer> quantityW09 = Collections.singletonList(2);
        List<Code> codeW12 = Collections.singletonList(new Code(VALID_CODE_FRIES));
        List<Integer> quantityW12 = Collections.singletonList(3);
        AddToOrderCommand addW09Command = new AddToOrderCommand(codeW09, quantityW09);
        AddToOrderCommand addW12Command = new AddToOrderCommand(codeW12, quantityW12);

        // same object -> returns true
        assertTrue(addW09Command.equals(addW09Command));

        // same values -> returns true
        AddToOrderCommand addW09CommandCopy = new AddToOrderCommand(codeW09, quantityW09);
        assertTrue(addW09Command.equals(addW09CommandCopy));

        // different types -> returns false
        assertFalse(addW09Command.equals(1));

        // null -> returns false
        assertFalse(addW09Command.equals(null));

        // different orderItem -> returns false
        assertFalse(addW09Command.equals(addW12Command));
    }

    /**
     * A default model stub that have all of the methods failing, except getSelectedTable() which returns table 1.
     * This model stub assumes that the program is in Table Mode for table 1.
     */
    private class ModelStub implements Model {
        private final Table table = new TableBuilder().build();

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public ReadOnlyRestOrRant getRestOrRant() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRestOrRant(ReadOnlyRestOrRant restOrRant) {
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
        public void clearOrderItemsFrom(TableNumber tableNumber) {
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
        public void setTables(List<Table> tables) {
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

        @Override
        public Name getNameFromItem(MenuItem menuItem) {
            return menuItem.getName();
        }
    }

    /**
     * A default orders stub that has all methods failing, except getItemFromCode() which returns an empty Optional.
     */
    private class OrdersStub implements ReadOnlyOrders {

        @Override
        public ObservableList<OrderItem> getOrderItemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<OrderItem> getOrderItem(TableNumber tableNumber, Code itemCode) {
            return Optional.empty();
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
    private class OrdersStubWithOrderItem extends OrdersStub {
        private final OrderItem orderItem;

        OrdersStubWithOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            this.orderItem = orderItem;
        }

        @Override
        public Optional<OrderItem> getOrderItem(TableNumber tableNumber, Code itemCode) {
            return Optional.of(orderItem);
        }
    }

    /**
     * A default RestOrRant stub that has all of the methods failing, except getMenu() and getOrders().
     */
    private class RestOrRantStub implements ReadOnlyRestOrRant {
        @Override
        public ReadOnlyMenu getMenu() {
            return new MenuStub();
        }

        @Override
        public ReadOnlyOrders getOrders() {
            return new OrdersStub();
        }

        @Override
        public ReadOnlyStatistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTables getTables() {
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
     * A RestOrRant stub whose menu will return an item for any given code.
     */
    private class RestOrRantStubWithItemCodes extends RestOrRantStub {
        @Override
        public ReadOnlyMenu getMenu() {
            return new MenuStubWithItemCodes();
        }
    }

    /**
     * A RestOrRant stub that contains the existing order item.
     */
    private class RestOrRantStubWithOrderItem extends RestOrRantStubWithItemCodes {
        private final OrderItem orderItem;

        RestOrRantStubWithOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            this.orderItem = orderItem;
        }

        @Override
        public ReadOnlyOrders getOrders() {
            return new OrdersStubWithOrderItem(orderItem);
        }
    }

    /**
     * A Model stub that does not contain any menu items (and thus all codes are invalid).
     */
    private class ModelStubWithoutItemCode extends ModelStub {
        @Override
        public ReadOnlyRestOrRant getRestOrRant() {
            return new RestOrRantStub();
        }
    }

    /**
     * A Model stub that contains a single orderItem.
     */
    private class ModelStubWithOrderItem extends ModelStub {
        private OrderItem orderItem;

        ModelStubWithOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            this.orderItem = orderItem;
        }

        @Override
        public ReadOnlyRestOrRant getRestOrRant() {
            return new RestOrRantStubWithOrderItem(orderItem);
        }

        @Override
        public boolean hasOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            return this.orderItem.isSameOrderItem(orderItem);
        }

        @Override
        public void setOrderItem(OrderItem target, OrderItem editedOrderItem) {
            orderItem = editedOrderItem;
        }

        public OrderItem getOrderItem() {
            return orderItem;
        }

        @Override
        public void updateFilteredOrderItemList(Predicate<OrderItem> predicate) {
        }
    }

    /**
     * A Model stub that always accept the orderItem being added.
     */
    private class ModelStubAcceptingOrderItemAdded extends ModelStub {
        final ArrayList<OrderItem> orderItemsAdded = new ArrayList<>();

        @Override
        public ReadOnlyRestOrRant getRestOrRant() {
            return new RestOrRantStubWithItemCodes();
        }

        @Override
        public boolean hasOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            return orderItemsAdded.stream().anyMatch(orderItem::isSameOrderItem);
        }

        @Override
        public void addOrderItem(OrderItem orderItem) {
            requireNonNull(orderItem);
            orderItemsAdded.add(orderItem);
        }

        @Override
        public void updateFilteredOrderItemList(Predicate<OrderItem> predicate) {
        }
    }

}
