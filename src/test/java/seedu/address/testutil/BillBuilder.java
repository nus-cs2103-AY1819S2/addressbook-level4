package seedu.address.testutil;

import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.TableNumber;

/**
 * A utility class to help with building Bill objects.
 */
public class BillBuilder {

    public static final String DEFAULT_DAY = "1";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_TOTAL_BILL = "0.00";
    public static final String DEFAULT_TABLE_NUMBER = "1";
    public static final String DEFAULT_RECEIPT = "";

    private Day day;
    private Month month;
    private Year year;
    private TableNumber tableNumber;
    private float totalBill;
    private String receipt;

    public BillBuilder() {
        day = new Day(DEFAULT_DAY);
        month = new Month(DEFAULT_MONTH);
        year = new Year(DEFAULT_YEAR);
        tableNumber = new TableNumber(DEFAULT_TABLE_NUMBER);
        totalBill = Float.parseFloat(DEFAULT_TOTAL_BILL);
        receipt = DEFAULT_RECEIPT;
    }

    /**
     * Initializes the BillBuilder with the data of {@code itemToCopy}.
     */
    public BillBuilder(Bill itemToCopy) {
        day = itemToCopy.getDay();
        month = itemToCopy.getMonth();
        year = itemToCopy.getYear();
        tableNumber = itemToCopy.getTableNumber();
        totalBill = itemToCopy.getTotalBill();
        receipt = itemToCopy.getReceipt();
    }

    /**
     * Sets the {@code Day} of the {@code Bill} that we are building.
     */
    public BillBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Month} of the {@code Bill} that we are building.
     */
    public BillBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code Bill} that we are building.
     */
    public BillBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code TableNumber} of the {@code Bill} that we are building.
     */
    public BillBuilder withTableNumber(String tableNumber) {
        this.tableNumber = new TableNumber(tableNumber);
        return this;
    }

    /**
     * Sets the {@code totalBill} of the {@code Bill} that we are building.
     */
    public BillBuilder withTotalBill(String totalBill) {
        this.totalBill = Float.parseFloat(totalBill);
        return this;
    }

    /**
     * Sets the {@code receipt} of the {@code Bill} that we are building.
     */
    public BillBuilder withReceipt(String receipt) {
        this.receipt = receipt;
        return this;
    }

    public Bill build() {
        return new Bill(day, month, year, tableNumber, totalBill, receipt);
    }

}
