package seedu.address.model.statistics;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import seedu.address.model.table.TableNumber;

/**
 * Represents a Bill from a UniqueOrderItemList from the RestOrRant.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bill {

    private final TableNumber tableNumber;
    private float totalBill;
    private Date date;

    /**
     * Every field must be present and not null.
     */
    public Bill(TableNumber tableNumber, float totalBill) {
        requireAllNonNull(tableNumber);
        requireAllNonNull(totalBill);
        this.tableNumber = tableNumber;
        this.totalBill = totalBill;
        date = new Date();
    }

    public  String getFormattedDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        return dateFormatter.format(date);
    }

    public TableNumber getTableNumber() {
        return tableNumber;
    }

    public float getTotalBill() {
        return totalBill;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tableNumber, date, totalBill);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Table ")
                .append(getTableNumber())
                .append("] [Total Cost of the Bill: $")
                .append(getTotalBill())
                .append("] [Date: ")
                .append(getFormattedDate())
                .append("] ");
        return builder.toString();
    }

}
