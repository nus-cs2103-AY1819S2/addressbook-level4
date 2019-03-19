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
     * Constructor for bill called in a specific tableMode.
     * It obtains its Day, Month and Year from the actual date (Date).
     */
    public Bill(TableNumber tableNumber, float totalBill, String receipt) {
        requireAllNonNull(tableNumber, totalBill, receipt);
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
     * Every field must be present and not null.
     * Constructor for StatisticsBuilder (under test cases).
     * Even though date obtains the actual date and time the bill was called,
     * Day, Month and Year are assigned by the constructor arguments.
     */
    public Bill(Day day, Month month, Year year, TableNumber tableNumber, float totalBill, String receipt) {
        requireAllNonNull(day, month, year, tableNumber, totalBill, receipt);
        this.day = day;
        this.month = month;
        this.year = year;
        this.tableNumber = tableNumber;
        this.totalBill = totalBill;
        this.receipt = receipt;
        date = new Date();
    }

    /**
     * Formats the current date and time and returns it as a String in the format dd.mm.yy at hh.mm.ss a zzz.
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
     * Gets the receipt that appends the formatted date.
     */
    public String getReceipt() {
        StringBuilder newReceipt = new StringBuilder();
        newReceipt.append(receipt).append(getFormattedDate());
        return newReceipt.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && this.getDay().equals(((Day) other).day) && this.getMonth().equals(((Month) other).month)
                && this.getYear().equals(((Year) other).year) && this.getTotalBill() == ((Bill) other).getTotalBill());
        // state check
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tableNumber, day, month, year, totalBill, receipt, date);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Total bill for Table ")
                .append(getTableNumber())
                .append(": $")
                .append(String.format("%.2f", getTotalBill()))
                .append(" [Date: ")
                .append(getFormattedDate())
                .append("] ");
        return builder.toString();
    }

}
