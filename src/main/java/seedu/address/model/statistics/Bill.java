package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * Represents a Bill from a UniqueOrderItemList from the RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bill {

    private ObservableList<OrderItem> orderItemList;
    private final TableNumber tableNumber;
    private final Day day;
    private final Month month;
    private final Year year;
    private int totalBill;

    /**
     * Every field must be present and not null.
     */
    public Bill(Table table, ObservableList<OrderItem> orderItemList, Day day, Month month, Year year) {
        requireAllNonNull(table);
        requireAllNonNull(orderItemList);
        requireAllNonNull(day);
        requireAllNonNull(month);
        requireAllNonNull(year);
        this.tableNumber = table.getTableNumber();
        this.orderItemList = orderItemList;
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalBill = 0;
    }

    public Bill(TableNumber tableNumber, Day day, Month month, Year year, int bill) {
        requireAllNonNull(tableNumber);
        requireAllNonNull(day);
        requireAllNonNull(month);
        requireAllNonNull(year);
        requireAllNonNull(bill);
        this.tableNumber = tableNumber;
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalBill = bill;
    }

    public void calculateBill() {
        Menu menu = new Menu();
        MenuItem menuItem;
        Optional<MenuItem> opt;
        for (OrderItem orderItem : orderItemList) {
            if (tableNumber.equals(orderItem.getTableNumber())) {
                opt = menu.getItemFromCode(orderItem.getMenuItemCode());
                if (opt.isPresent()) {
                    menuItem = opt.get();
                    totalBill += Integer.parseInt(menuItem.getPrice().itemPrice) * orderItem.getQuantity();
                }
            }
        }
    }

    public ObservableList<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public Day getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(orderItemList, tableNumber, day, month, year, totalBill);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Table ")
                .append(getTableNumber())
                .append("] [Date: ")
                .append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear())
                .append("] [Total Cost of the Bill: $")
                .append(getTotalBill())
                .append("] ");
        return builder.toString();
    }

}
