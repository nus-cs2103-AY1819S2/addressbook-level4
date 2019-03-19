package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.DailyRevenue;
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
    public static final String MESSAGE_TABLE_MISMATCH = "TableNumber is different from the received table.";
    public static final String MESSAGE_MENUITEM_NOT_PRESENT = "MenuItem is not received.";
    public static final String MESSAGE_INCORRECT_MODE = "Incorrect Mode, unable to execute command. Enter tableMode "
            + "[TABLE_NUMBER]";

    private static Bill bill;
    private Table tableToBill;
    private float totalBill;

    /**
     * Creates a BillCommand to find the total bill of the specified {@code Table}
     */
    public BillCommand() {
    }

    /**
     * Creates a BillCommand to find the total bill of the specified {@code Table}
     */
    public BillCommand(Bill bill) {
        this.bill = bill;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!mode.equals(Mode.TABLE_MODE)) {
            throw new CommandException(MESSAGE_INCORRECT_MODE);
        }

        tableToBill = model.getSelectedTable();

        if (!model.hasTable(tableToBill)) {
            throw new CommandException(MESSAGE_TABLE_DOES_NOT_EXIST);
        }

        ObservableList<OrderItem> orderItemList = model.getFilteredOrderItemList();

        bill = calculateBill(orderItemList, model.getRestOrRant().getMenu());

        model.setRecentBill(bill);

        DailyRevenue dailyRevenue =
                new DailyRevenue(bill.getDay(), bill.getMonth(), bill.getYear(), bill.getTotalBill());
        if (model.hasDailyRevenue(dailyRevenue)) {
            updateDailyRevenue(model, bill);
        } else {
            model.addDailyRevenue(dailyRevenue);
        }

        updateStatusOfTable(model);

        model.updateMode();
        return new CommandResult(String.format(MESSAGE_SUCCESS, bill), false, false, Mode.BILL_MODE);
    }

    /**
     * Calculates the total bill from the order item list;
     * Updates the popularity of a menu item;
     * Updates the status of the table;
     * Returns a new Bill with a receipt.
     */
    private Bill calculateBill(ObservableList<OrderItem> orderItemList, ReadOnlyMenu menu) throws CommandException {
        MenuItem menuItem;
        Optional<MenuItem> opt;

        final StringBuilder receipt = new StringBuilder();
        receipt.append("Table ").append(tableToBill.getTableNumber()).append("\n");

        for (OrderItem orderItem : orderItemList) {
            if (!tableToBill.getTableNumber().equals(orderItem.getTableNumber())) {
                throw new CommandException(MESSAGE_TABLE_MISMATCH);
            }
            opt = menu.getItemFromCode(orderItem.getMenuItemCode());
            if (!opt.isPresent()) {
                throw new CommandException(MESSAGE_MENUITEM_NOT_PRESENT);
            }
            menuItem = opt.get();
            //TODO: Update the quantity of the menu item for its popularity
            //menu.updateMenuItemQuantity(orderItem.getMenuItemCode(), orderItem.getQuantity());
            receipt.append(menuItem.getCode().itemCode)
                    .append("  ")
                    .append(menuItem.getName().itemName)
                    .append("\n $")
                    .append(menuItem.getPrice().itemPrice)
                    .append("   x ")
                    .append(orderItem.getQuantity())
                    .append("\n");

            totalBill += Float.parseFloat(menuItem.getPrice().toString()) * orderItem.getQuantity();
        }
        receipt.append("Total Bill: $ ").append(totalBill).append("\n");
        return new Bill(tableToBill.getTableNumber(), totalBill, receipt.toString());
    }

    /**
     * Updates the daily revenue.
     */
    private void updateDailyRevenue(Model model, Bill bill) {
        ObservableList<DailyRevenue> dailyRevenuesList = model.getFilteredDailyRevenueList();
        for (DailyRevenue dailyRevenue : dailyRevenuesList) {
            if (dailyRevenue.getYear().equals(bill.getYear())
                    && dailyRevenue.getMonth().equals(bill.getMonth())
                    && dailyRevenue.getDay().equals(bill.getDay())) {
                dailyRevenue.addToRevenue(bill);
            }
        }
    }

    /**
     * Updates the status of table.
     */
    private void updateStatusOfTable(Model model) throws CommandException {
        TableStatus updatedTableStatus = tableToBill.getTableStatus();
        updatedTableStatus.changeOccupancy("0");
        Table updatedTable = new Table(tableToBill.getTableNumber(), updatedTableStatus);
        model.setTable(tableToBill, updatedTable);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BillCommand // instanceof handles nulls
                && tableToBill.equals(((BillCommand) other).tableToBill));
    }
}
