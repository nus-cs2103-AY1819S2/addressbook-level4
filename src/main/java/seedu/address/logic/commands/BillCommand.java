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
import seedu.address.model.table.Table;

/**
 * Retrieves the Bill for a Table.
 */
public class BillCommand extends Command {

    public static final String COMMAND_WORD = "bill";
    public static final String COMMAND_ALIAS = "b";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the Bill for a Table. " + "No Parameters. "
            + "Example: " + COMMAND_WORD + "or " + COMMAND_ALIAS;
    public static final String MESSAGE_SUCCESS = "Bill Calculated: $ %1$s";
    public static final String MESSAGE_TABLE_DOES_NOT_EXIST = "This table does not exist.";
    public static final String MESSAGE_TABLE_MISMATCH = "TableNumber is different from the received table.";
    public static final String MESSAGE_MENUITEM_NOT_PRESENT = "MenuItem is not received.";
    private static Bill bill;
    private Table toBill;
    private float totalBill;

    /**
     * Creates a BillCommand to find the total bill of the specified {@code Table}
     */
    public BillCommand() {

    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        toBill = model.getSelectedTable();

        if (!model.hasTable(toBill)) {
            throw new CommandException(MESSAGE_TABLE_DOES_NOT_EXIST);
        }

        ObservableList<OrderItem> orderItemList = model.getFilteredOrderItemList();

        bill = calculateBill(orderItemList, model.getRestOrRant().getMenu());

        model.updateMode();
        return new CommandResult(String.format(MESSAGE_SUCCESS, bill));
    }

    private Bill calculateBill(ObservableList<OrderItem> orderItemList, ReadOnlyMenu menu) throws CommandException {
        MenuItem menuItem;
        Optional<MenuItem> opt;
        for (OrderItem orderItem : orderItemList) {
            if (!toBill.getTableNumber().equals(orderItem.getTableNumber())) {
                throw new CommandException(MESSAGE_TABLE_MISMATCH);
            }
            opt = menu.getItemFromCode(orderItem.getMenuItemCode());
            if (!opt.isPresent()) {
                throw new CommandException(MESSAGE_MENUITEM_NOT_PRESENT);
            }
            menuItem = opt.get();
            //menu.updateMenuItemQuantity(orderItem.getMenuItemCode(), orderItem.getQuantity());
            totalBill += Float.parseFloat(menuItem.getPrice().toString()) * orderItem.getQuantity();
        }
        return new Bill(toBill.getTableNumber(), totalBill);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BillCommand // instanceof handles nulls
                && toBill.equals(((BillCommand) other).toBill));
    }
}
