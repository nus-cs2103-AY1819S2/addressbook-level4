package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TABLENUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

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
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;


/**
 * Retrieves the Bill for a Table.
 */
public class BillCommand extends Command {

    public static final String COMMAND_WORD = "bill";
    public static final String COMMAND_ALIAS = "b";

    private final TableNumber tableNumber;
    private Table toBill;
    private static Bill bill;
    private final Day day;
    private final Month month;
    private final Year year;
    private float totalBill;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves the Bill for a Table. "
            + "Parameters: "
            + PREFIX_TABLENUMBER + "TABLE NUMBER "
            + PREFIX_DAY + "DAY "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TABLENUMBER + "1 "
            + PREFIX_DAY + "30 "
            + PREFIX_MONTH + "12 "
            + PREFIX_YEAR + "2019 ";

    public static final String MESSAGE_SUCCESS = "Bill Calculated: $ %1$s";
    public static final String MESSAGE_TABLE_DOES_NOT_EXIST = "This table does not exist.";
    public static final String MESSAGE_TABLE_MISMATCH = "TableNumber is different from the received table.";
    public static final String MESSAGE_MENUITEM_NOT_PRESENT ="MenuItem is not received."; 

    /**
     * Creates a BillCommand to find the total bill of the specified {@code Table}
     */
    public BillCommand(TableNumber tableNumber, Day day, Month month, Year year) {
        requireNonNull(tableNumber);
        requireNonNull(day);
        requireNonNull(month);
        requireNonNull(year);
        this.tableNumber = tableNumber;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        toBill = model.getSelectedTable();

        if (!model.hasTable(toBill)) {
            throw new CommandException(MESSAGE_TABLE_DOES_NOT_EXIST);
        }

        if (!tableNumber.equals(toBill.getTableNumber())) {
            throw new CommandException(MESSAGE_TABLE_MISMATCH);
        }

        ObservableList<OrderItem> orderItemList = model.getFilteredOrderItemList();

        bill = calculateBill(orderItemList, model.getRestOrRant().getMenu());

        model.updateRestOrRant();
        return new CommandResult(String.format(MESSAGE_SUCCESS, bill));
    }

    private Bill calculateBill(ObservableList<OrderItem> orderItemList, ReadOnlyMenu menu) throws CommandException {
        MenuItem menuItem;
        Optional<MenuItem> opt;
        for (OrderItem orderItem : orderItemList) {
            if (!tableNumber.equals(orderItem.getTableNumber())) {
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
        return new Bill(tableNumber, day, month, year, totalBill);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BillCommand // instanceof handles nulls
                && toBill.equals(((BillCommand) other).toBill));
    }
}
