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
    private String receipt;
    private Date date;
    private Day day;
    private Month month;
    private Year year;

    /**
     * Every field must be present and not null.
     */
    public Bill(TableNumber tableNumber, float totalBill, String receipt) {
        requireAllNonNull(tableNumber);
        requireAllNonNull(totalBill);
        requireAllNonNull(receipt);
        this.tableNumber = tableNumber;
        this.totalBill = totalBill;
        this.receipt = receipt;
        date = new Date();
        String dateParser = getFormattedDate();
        day = new Day(dateParser.substring(0, 2));
        month = new Month(dateParser.substring(3, 5));
        year = new Year(dateParser.substring(6, 10));

    }

    /**
     * Formats the current date and time and returns it as a String.
     */
    public String getFormattedDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm:ss a zzz");
        return dateFormatter.format(date);
    }

    /**
     * Gets the day.
     */
    public Day getDay() {
        return day;
    }

    /**
     * Gets the month.
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Gets the year.
     */
    public Year getYear() {
        return year;
    }

    /**
     * Gets the tableNumber.
     */
    public TableNumber getTableNumber() {
        return tableNumber;
    }

    /**
     * Gets the totalBill.
     */
    public float getTotalBill() {
        return totalBill;
    }

    /**
     * Gets the receipt.
     */
    public String getReceipt() {
        return receipt;
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
