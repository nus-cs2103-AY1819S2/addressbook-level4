package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;

/**
 * Retrieves the Bill for a Table.
 */
public class BillCommand extends Command {

    public static final String COMMAND_WORD = "bill";
    public static final String COMMAND_ALIAS = "b";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the Bill for a Table. " + "No Parameters. "
            + "Example: " + COMMAND_WORD + "or " + COMMAND_ALIAS;
    public static final String MESSAGE_SUCCESS = "%1$s";
    public static final String MESSAGE_TABLE_DOES_NOT_EXIST = "This table does not exist.";
    public static final String MESSAGE_MENU_ITEM_NOT_PRESENT = "MenuItem is not received.";
    public static final String MESSAGE_ORDER_ITEM_NOT_SERVED = "Not all orders are served yet. Call bill only when "
            + "all orders are served.";

    private Bill bill;
    private Table tableToBill;
    private float totalBill;

    /**
     * Creates a BillCommand to find the total bill of the specified {@code Table}
     */
    public BillCommand() {
    }

    /**
     * Creates a BillCommand from the specified created bill formed in test cases.
     */
    public BillCommand(Bill bill) {
        requireNonNull(bill);
        this.bill = bill;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(mode, model, history);

        tableToBill = model.getSelectedTable();
        if (!model.hasTable(tableToBill)) {
            throw new CommandException(MESSAGE_TABLE_DOES_NOT_EXIST);
        }

        for (OrderItem orderItem : model.getFilteredOrderItemList()) {
            if (!orderItem.getOrderItemStatus().isAllServed()) {
                throw new CommandException(MESSAGE_ORDER_ITEM_NOT_SERVED);
            }
        }

        bill = calculateBill(model);
        model.setRecentBill(bill);
        createOrUpdateRevenue(model, bill);
        updateStatusOfTable(model);
        model.clearOrderItemsFrom(tableToBill.getTableNumber());
        model.updateTables();
        model.updateStatistics();
        model.updateOrders();
        model.updateMenu();
        return new CommandResult(String.format(MESSAGE_SUCCESS, bill), false, false, Mode.BILL_MODE);
    }

    /**
     * Calculates the total bill from the order item list;
     * Updates the popularity of a menu item;
     * Updates the status of the table;
     * Returns a new Bill with a receipt.
     */
    private Bill calculateBill(Model model) throws CommandException {
        ObservableList<OrderItem> observableOrderItemList = model.getFilteredOrderItemList();
        ReadOnlyMenu menu = model.getRestOrRant().getMenu();
        MenuItem menuItem;
        Optional<MenuItem> opt;

        final StringBuilder receipt = new StringBuilder();
        receipt.append("\nTable ").append(tableToBill.getTableNumber()).append("\n\n");

        for (OrderItem orderItem : observableOrderItemList) {
            requireNonNull(orderItem);

            opt = menu.getItemFromCode(orderItem.getMenuItemCode());
            if (!opt.isPresent()) {
                throw new CommandException(MESSAGE_MENU_ITEM_NOT_PRESENT);
            }
            menuItem = opt.get();
            int quantity = orderItem.getQuantityOrdered();
            menu.updateMenuItemQuantity(menuItem, quantity);
            Code code = menuItem.getCode();
            Name name = menuItem.getName();
            Price price = menuItem.getPrice();
            receipt.append(code.toString())
                    .append("  ")
                    .append(name.toString())
                    .append("\n $")
                    .append(price.toString())
                    .append("   x ")
                    .append(quantity)
                    .append("\n\n");

            totalBill += Float.parseFloat(price.toString()) * quantity;
        }
        receipt.append("Total Bill: $ ").append(String.format("%.2f", totalBill)).append("\n");
        return new Bill(tableToBill.getTableNumber(), totalBill, receipt.toString());
    }

    /**
     * Creates or updates the revenue.
     */
    private void createOrUpdateRevenue(Model model, Bill bill) {

        DailyRevenue dailyRevenue =
                new DailyRevenue(bill.getDay(), bill.getMonth(), bill.getYear(), bill.getTotalBill());

        if (model.hasRevenue(dailyRevenue)) {
            ObservableList<Revenue> revenueList = model.getFilteredRevenueList();
            for (Revenue revenue : revenueList) {
                if (revenue.getYear().equals(bill.getYear())
                        && revenue.getMonth().equals(bill.getMonth())
                        && revenue.getDay().equals(bill.getDay())) {
                    revenue.addToRevenue(bill.getTotalBill());
                }
            }
        } else {
            model.addRevenue(dailyRevenue);
        }
    }

    /**
     * Updates the status of table.
     */
    private void updateStatusOfTable(Model model) throws CommandException {
        TableStatus updatedTableStatus = new TableStatus("0/" + tableToBill.getTableStatus().getNumberOfSeats());
        Table updatedTable = new Table(tableToBill.getTableNumber(), updatedTableStatus);
        model.setSelectedTable(null);
        model.setTable(tableToBill, updatedTable);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BillCommand // instanceof handles nulls
                && tableToBill.equals(((BillCommand) other).tableToBill) && totalBill == ((BillCommand) other).totalBill
                && bill.equals(((BillCommand) other).bill));
    }
}
