package seedu.address.testutil;

import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.TableNumber;

/**
 * A utility class to help with building Statistics objects.
 */
// TODO: do this class after storage is ok
public class StatisticsBuilder {

    public static final String DEFAULT_DAY = "1";
    public static final String DEFAULT_MONTH = "1";
    public static final String DEFAULT_YEAR = "2019";
    public static final String DEFAULT_DAILY_REVENUE = "0.00";
    public static final String DEFAULT_TOTAL_BILL = "0.00";
    public static final String DEFAULT_TABLE_NUMBER = "1";
    public static final String DEFAULT_RECEIPT = "";

    private Day day;
    private Month month;
    private Year year;
    private float totalDailyRevenue;
    private TableNumber tableNumber;
    private float totalBill;
    private String receipt;

    public StatisticsBuilder() {
        day = new Day(DEFAULT_DAY);
        month = new Month(DEFAULT_MONTH);
        year = new Year(DEFAULT_YEAR);
        totalDailyRevenue = Float.parseFloat(DEFAULT_DAILY_REVENUE);
        tableNumber = new TableNumber(DEFAULT_TABLE_NUMBER);
        totalBill = Float.parseFloat(DEFAULT_TOTAL_BILL);
        receipt = DEFAULT_RECEIPT;
    }

    /**
     * Initializes the StatisticsBuilder with the data of {@code itemToCopy}.
     */
    public StatisticsBuilder(DailyRevenue itemToCopy) {
        day = itemToCopy.getDay();
        month = itemToCopy.getMonth();
        year = itemToCopy.getYear();
        totalDailyRevenue = itemToCopy.getTotalDailyRevenue();
        tableNumber = null;
        totalBill = Float.parseFloat(DEFAULT_TOTAL_BILL);
        receipt = null;
    }

    /**
     * Initializes the StatisticsBuilder with the data of {@code itemToCopy}.
     */
    public StatisticsBuilder(Bill itemToCopy) {
        day = itemToCopy.getDay();
        month = itemToCopy.getMonth();
        year = itemToCopy.getYear();
        totalDailyRevenue = Float.parseFloat(DEFAULT_DAILY_REVENUE);
        tableNumber = itemToCopy.getTableNumber();
        totalBill = itemToCopy.getTotalBill();
        receipt = itemToCopy.getReceipt();
    }

    /**
     * Sets the {@code Day} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Month} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withMonth(String month) {
        this.month = new Month(month);
        return this;
    }

    /**
     * Sets the {@code Year} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withYear(String year) {
        this.year = new Year(year);
        return this;
    }

    /**
     * Sets the {@code totalDailyRevenue} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withTotalDailyRevenue(String totalDailyRevenue) {
        this.totalDailyRevenue = Float.parseFloat(totalDailyRevenue);
        return this;
    }

    /**
     * Sets the {@code TableNumber} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withTableNumber(String tableNumber) {
        this.tableNumber = new TableNumber(tableNumber);
        return this;
    }

    /**
     * Sets the {@code totalBill} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withTotalBill(String totalBill) {
        this.totalBill = Float.parseFloat(totalBill);
        return this;
    }

    /**
     * Sets the {@code receipt} of the {@code DailyRevenue} that we are building.
     */
    public StatisticsBuilder withReceipt(String receipt) {
        this.receipt = receipt;
        return this;
    }

    public DailyRevenue build() {
        return new DailyRevenue(day, month, year, totalDailyRevenue);
    }

    public Bill buildBill() {
        return new Bill(day, month, year, tableNumber, totalBill, receipt);
    }

}
