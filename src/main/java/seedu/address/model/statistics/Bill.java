package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.UniqueOrderItemList;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * Represents a Bill from a UniqueOrderItemList from the RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bill {

    private final Table table;
    private final TableNumber tableNumber;
    private final Day day;
    private final Month month;
    private final Year year;
    private final int totalBill;

    /**
     * Every field must be present and not null.
     */
    public Bill(Table table, Day day, Month month, Year year) {
        requireAllNonNull(table);
        requireAllNonNull(day);
        requireAllNonNull(month);
        requireAllNonNull(year);
        this.table = table;
        this.tableNumber = table.getTableNumber();
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalBill = 0;
    }

    public void calculateBill() {
        // TODO: iterate through the UniqueOrderList from the Table
        // Use getMenuItem().getPrice() after merging with the rest
        //for each orderItem in the UniqueOrderItemList
        //if the table
        Menu menu = new Menu();
        MenuItem menuItem;
        ObservableList<OrderItem> orderItemList = table.getUniqueOrderItemList().asUnmodifiableObservableList();
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getTableNumber() == tableNumber) {
                menuItem = menu.getItemFromCode(orderItem.getMenuItemCode()).get();
                totalBill += Integer.parseInt(menuItem.getPrice().itemPrice) * orderItem.getQuantity();
            }
        }
    }

    public Table getTable() {
        return table;
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
        return Objects.hash(table, day, month, year, totalBill);
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
