package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import seedu.address.model.table.TableNumber;

/**
 * Represents a Bill from a UniqueOrderItemList from the RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bill {

    private final TableNumber tableNumber;
    private final Day day;
    private final Month month;
    private final Year year;
    private float totalBill;

    /**
     * Every field must be present and not null.
     */
    public Bill(TableNumber tableNumber, Day day, Month month, Year year, float totalBill) {
        requireAllNonNull(tableNumber);
        requireAllNonNull(day);
        requireAllNonNull(month);
        requireAllNonNull(year);
        requireAllNonNull(totalBill);
        this.tableNumber = tableNumber;
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalBill = totalBill;
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public float getTotalBill() {
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
        return Objects.hash(tableNumber, day, month, year, totalBill);
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
