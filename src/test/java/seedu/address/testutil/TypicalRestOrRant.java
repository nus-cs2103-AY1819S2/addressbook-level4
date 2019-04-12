package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestOrRant;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.Orders;
import seedu.address.model.statistics.Bill;
import seedu.address.model.statistics.MonthlyRevenue;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.YearlyRevenue;
import seedu.address.model.table.Table;
import seedu.address.model.table.Tables;

/**
 * This class provides a RestOrRant with all typical items
 */
public class TypicalRestOrRant {
    // Menu Items TODO: add quantity ordered
    public static final MenuItem CHICKEN_WINGS = new MenuItemBuilder().withName("Chicken Wings").withCode("W09")
                                                         .withPrice("3.99").withQuantity("0").build();
    public static final MenuItem FRENCH_FRIES = new MenuItemBuilder().withName("French Fries").withCode("W12")
                                                        .withPrice("2.20").withQuantity("1").build();
    public static final MenuItem SALAD = new MenuItemBuilder().withName("Salad").withCode("A05")
                                                 .withPrice("5.50").withQuantity("2").build();
    public static final MenuItem CHEESE_NACHOS = new MenuItemBuilder().withName("Cheese Nachos").withCode("M17")
                                                         .withPrice("7.23").withQuantity("3").build();
    public static final MenuItem SHRIMP_FRIED_RICE = new MenuItemBuilder().withName("Shrimp Fried Rice").withCode("C02")
                                                             .withPrice("8.90").withQuantity("4").build();
    public static final MenuItem MEE_SUA = new MenuItemBuilder().withName("Mee Sua").withCode("T01")
                                                   .withPrice("4.99").withQuantity("5").build();
    public static final MenuItem KIMCHI_FRIED_RICE = new MenuItemBuilder().withName("Kimchi Fried Rice").withCode("K12")
                                                             .withPrice("5.50").withQuantity("6").build();
    public static final MenuItem CREPES = new MenuItemBuilder().withName("Crepes").withCode("F11")
                                                  .withPrice("7.50").withQuantity("7").build();

    // manually added
    public static final MenuItem HONEY_MILK_TEA = new MenuItemBuilder().withName("Honey Milk Tea").withCode("D01")
                                                          .withPrice("4.50").withQuantity("8").build();
    public static final MenuItem AGLIO_OLIO = new MenuItemBuilder().withName("Aglio Olio").withCode("W14")
                                                      .withPrice("7.56").withQuantity("9").build();

    // Order Items
    public static final OrderItem TABLE1_W09 =
            new OrderItemBuilder().withTableNumber("1").withCode("W09").withName("Chicken Wings")
                    .withQuantity(2).build();
    public static final OrderItem TABLE1_W12 =
            new OrderItemBuilder().withTableNumber("1").withCode("W12").withName("French Fries")
                    .withQuantity(2).build();
    public static final OrderItem TABLE2_A05 =
            new OrderItemBuilder().withTableNumber("2").withCode("A05").withName("Salad").withQuantity(1).build();
    public static final OrderItem TABLE2_M17 =
            new OrderItemBuilder().withTableNumber("2").withCode("M17").withName("Cheese Nachos")
                    .withQuantity(1).build();
    public static final OrderItem TABLE2_C02 =
            new OrderItemBuilder().withTableNumber("2").withCode("C02").withName("Shrimp Fried Rice")
                    .withQuantity(2).build();
    public static final OrderItem TABLE4_T01 =
            new OrderItemBuilder().withTableNumber("4").withCode("T01").withName("Mee Sua").withQuantity(3).build();
    public static final OrderItem TABLE7_F11 =
            new OrderItemBuilder().withTableNumber("7").withCode("F11").withName("Crepes").withQuantity(4).build();
    public static final OrderItem TABLE8_K12 =
            new OrderItemBuilder().withTableNumber("8").withCode("K12").withName("Kimchi Fried Rice")
                    .withQuantity(1).build();
    public static final OrderItem TABLE8_W09 =
            new OrderItemBuilder().withTableNumber("8").withCode("W09").withName("Chicken Wings")
                    .withQuantity(3).build();
    public static final OrderItem TABLE8_W12 =
            new OrderItemBuilder().withTableNumber("8").withCode("W12").withName("French Fries")
                    .withQuantity(2).build();

    // Tables (table no and status)
    public static final Table TABLE1 = new TableBuilder().withTableNumber("1").withTableStatus("4/4").build();
    public static final Table TABLE2 = new TableBuilder().withTableNumber("2").withTableStatus("4/5").build();
    public static final Table TABLE3 = new TableBuilder().withTableNumber("3").withTableStatus("0/4").build();
    public static final Table TABLE4 = new TableBuilder().withTableNumber("4").withTableStatus("3/4").build();
    public static final Table TABLE5 = new TableBuilder().withTableNumber("5").withTableStatus("0/5").build();
    public static final Table TABLE6 = new TableBuilder().withTableNumber("6").withTableStatus("0/3").build();
    public static final Table TABLE7 = new TableBuilder().withTableNumber("7").withTableStatus("4/4").build();
    public static final Table TABLE8 = new TableBuilder().withTableNumber("8").withTableStatus("1/4").build();

    // Statistics (day, month, year and total daily revenue)
    public static final Revenue DAILY_REVENUE1 = new StatisticsBuilder().withDay("1").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("700").build();
    public static final Revenue DAILY_REVENUE2 = new StatisticsBuilder().withDay("2").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("600").build();
    public static final Revenue DAILY_REVENUE3 = new StatisticsBuilder().withDay("3").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("500").build();
    public static final Revenue DAILY_REVENUE4 = new StatisticsBuilder().withDay("4").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("400").build();
    public static final Revenue DAILY_REVENUE5 = new StatisticsBuilder().withDay("5").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("300").build();
    public static final Revenue DAILY_REVENUE6 = new StatisticsBuilder().withDay("6").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("200").build();
    public static final Revenue DAILY_REVENUE7 = new StatisticsBuilder().withDay("7").withMonth("3")
                                                       .withYear("2019").withTotalRevenue("100").build();
    public static final Revenue DAILY_REVENUE8 = new StatisticsBuilder().withDay("8").withMonth("3")
            .withYear("2019").withTotalRevenue("50").build();

    // Monthly Revenue (month, year and total monthly revenue)
    public static final MonthlyRevenue MONTHLY_REVENUE1 = new MonthlyRevenueBuilder().withMonth("1")
            .withYear("2019").withTotalMonthlyRevenue("700").build();
    public static final MonthlyRevenue MONTHLY_REVENUE2 = new MonthlyRevenueBuilder().withMonth("2")
            .withYear("2019").withTotalMonthlyRevenue("600").build();
    public static final MonthlyRevenue MONTHLY_REVENUE3 = new MonthlyRevenueBuilder().withMonth("3")
            .withYear("2019").withTotalMonthlyRevenue("500").build();

    // Yearly Revenue (year and total yearly revenue)
    public static final YearlyRevenue YEARLY_REVENUE1 = new YearlyRevenueBuilder().withYear("2017")
            .withTotalYearlyRevenue("700").build();
    public static final YearlyRevenue YEARLY_REVENUE2 = new YearlyRevenueBuilder().withYear("2018")
            .withTotalYearlyRevenue("600").build();
    public static final YearlyRevenue YEARLY_REVENUE3 = new YearlyRevenueBuilder().withYear("2019")
            .withTotalYearlyRevenue("500").build();

    //Bill (day, month, year, totalBill and receipt)
    public static final Bill BILL1 = new BillBuilder().withTableNumber("1").withDay("1").withMonth("3")
            .withYear("2019").withTotalBill("10").withReceipt("").build();
    public static final Bill BILL2 = new BillBuilder().withTableNumber("2").withDay("2").withMonth("3")
            .withYear("2019").withTotalBill("20").withReceipt("").build();
    public static final Bill BILL3 = new BillBuilder().withTableNumber("3").withDay("3").withMonth("3")
            .withYear("2019").withTotalBill("30").withReceipt("").build();

    private TypicalRestOrRant() {
    } // prevents instantiation

    /**
     * Returns an {@code RestOrRant} with all the typical items.
     */
    public static RestOrRant getTypicalRestOrRant() {
        RestOrRant restOrRant = new RestOrRant();
        Menu menu = restOrRant.getMenu();
        Orders orders = restOrRant.getOrders();
        Tables tables = restOrRant.getTables();
        Statistics statistics = restOrRant.getStatistics();

        for (MenuItem menuItem : getTypicalMenuItems()) {
            menu.addMenuItem(menuItem);
        }

        for (OrderItem orderItem : getTypicalOrderItems()) {
            orders.addOrderItem(orderItem);
        }

        for (Table table : getTypicalTables()) {
            tables.addTable(table);
        }

        for (Revenue revenue : getTypicalRevenue()) {
            statistics.addRevenue(revenue);
        }
        return new RestOrRant(orders, menu, tables, statistics);
    }

    public static List<MenuItem> getTypicalMenuItems() {
        return new ArrayList<>(Arrays.asList(CHICKEN_WINGS, FRENCH_FRIES, SALAD, CHEESE_NACHOS, SHRIMP_FRIED_RICE,
                MEE_SUA, KIMCHI_FRIED_RICE, CREPES));
    }

    public static List<OrderItem> getTypicalOrderItems() {
        return new ArrayList<>(
                Arrays.asList(TABLE1_W09, TABLE1_W12, TABLE2_A05, TABLE2_M17, TABLE2_C02, TABLE4_T01, TABLE7_F11,
                        TABLE8_K12));
    }

    public static List<Table> getTypicalTables() {
        return new ArrayList<>(Arrays.asList(TABLE1, TABLE2, TABLE3, TABLE4, TABLE5, TABLE6, TABLE7, TABLE8));
    }

    public static List<Revenue> getTypicalRevenue() {
        return new ArrayList<>(Arrays.asList(DAILY_REVENUE1, DAILY_REVENUE2, DAILY_REVENUE3, DAILY_REVENUE4,
                DAILY_REVENUE5, DAILY_REVENUE6, DAILY_REVENUE7));
    }
}
