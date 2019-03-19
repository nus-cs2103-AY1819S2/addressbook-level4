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
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.table.Table;
import seedu.address.model.table.Tables;

/**
 * This class provides a RestOrRant with all typical items
 */
public class TypicalRestOrRant {
    // Menu Items TODO: add quantity ordered
    public static final MenuItem CHICKEN_WINGS = new MenuItemBuilder().withName("Chicken Wings").withCode("W09")
                                                         .withPrice("3.99").build();
    public static final MenuItem FRENCH_FRIES = new MenuItemBuilder().withName("French Fries").withCode("W12")
                                                        .withPrice("2.20").build();
    public static final MenuItem SALAD = new MenuItemBuilder().withName("Salad").withCode("A05")
                                                 .withPrice("5.50").build();
    public static final MenuItem CHEESE_NACHOS = new MenuItemBuilder().withName("Cheese Nachos").withCode("M17")
                                                         .withPrice("7.23").build();
    public static final MenuItem SHRIMP_FRIED_RICE = new MenuItemBuilder().withName("Shrimp Fried Rice").withCode("C02")
                                                             .withPrice("8.90").build();
    public static final MenuItem MEE_SUA = new MenuItemBuilder().withName("Mee Soi").withCode("T01")
                                                   .withPrice("4.99").build();
    public static final MenuItem KIMCHI_FRIED_RICE = new MenuItemBuilder().withName("Kimchi Fried Rice").withCode("K12")
                                                             .withPrice("5.50").build();
    public static final MenuItem CREPES = new MenuItemBuilder().withName("Crepes").withCode("F11")
                                                  .withPrice("7.50").build();

    // manually added
    public static final MenuItem HONEY_MILK_TEA = new MenuItemBuilder().withName("Honey Milk Tea").withCode("D01")
                                                          .withPrice("4.50").build();
    public static final MenuItem AGLIO_OLIO = new MenuItemBuilder().withName("Aglio Olio").withCode("W14")
                                                      .withPrice("7.56").build();

    // Order Items TODO: add item name
    public static final OrderItem TABLE1_W09 =
            new OrderItemBuilder().withTableNumber("1").withCode("W09").withQuantity(2).build();
    public static final OrderItem TABLE1_W12 =
            new OrderItemBuilder().withTableNumber("1").withCode("W12").withQuantity(2).build();
    public static final OrderItem TABLE2_A05 =
            new OrderItemBuilder().withTableNumber("2").withCode("A05").withQuantity(1).build();
    public static final OrderItem TABLE2_M17 =
            new OrderItemBuilder().withTableNumber("2").withCode("M17").withQuantity(1).build();
    public static final OrderItem TABLE2_C02 =
            new OrderItemBuilder().withTableNumber("2").withCode("C02").withQuantity(2).build();
    public static final OrderItem TABLE4_T01 =
            new OrderItemBuilder().withTableNumber("4").withCode("T01").withQuantity(3).build();
    public static final OrderItem TABLE7_F11 =
            new OrderItemBuilder().withTableNumber("7").withCode("F11").withQuantity(4).build();
    public static final OrderItem TABLE8_K12 =
            new OrderItemBuilder().withTableNumber("1").withCode("K12").withQuantity(1).build();
    public static final OrderItem TABLE8_W09 =
            new OrderItemBuilder().withTableNumber("8").withCode("W09").withQuantity(3).build();
    public static final OrderItem TABLE8_W12 =
            new OrderItemBuilder().withTableNumber("8").withCode("W12").withQuantity(2).build();

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
    public static final DailyRevenue DAILY_REVENUE1 = new StatisticsBuilder().withDay("1").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("700").build();
    public static final DailyRevenue DAILY_REVENUE2 = new StatisticsBuilder().withDay("2").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("600").build();
    public static final DailyRevenue DAILY_REVENUE3 = new StatisticsBuilder().withDay("3").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("500").build();
    public static final DailyRevenue DAILY_REVENUE4 = new StatisticsBuilder().withDay("4").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("400").build();
    public static final DailyRevenue DAILY_REVENUE5 = new StatisticsBuilder().withDay("5").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("300").build();
    public static final DailyRevenue DAILY_REVENUE6 = new StatisticsBuilder().withDay("3").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("200").build();
    public static final DailyRevenue DAILY_REVENUE7 = new StatisticsBuilder().withDay("1").withMonth("3")
                                                       .withYear("2019").withTotalDailyRevenue("100").build();

    public static final Bill BILL1 = new StatisticsBuilder().withTableNumber("1").withDay("1").withMonth("3")
            .withYear("2019").withTotalBill("10").withReceipt("").buildBill();
    public static final Bill BILL2 = new StatisticsBuilder().withTableNumber("2").withDay("2").withMonth("3")
            .withYear("2019").withTotalBill("20").withReceipt("").buildBill();
    public static final Bill BILL3 = new StatisticsBuilder().withTableNumber("3").withDay("3").withMonth("3")
            .withYear("2019").withTotalBill("30").withReceipt("").buildBill();

    // Manually added - Person's details found in {@code CommandTestUtil}
    //    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
    //                                             .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //                                             .withTags(VALID_TAG_FRIEND).build();
    //    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //                                             .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
    //                                             .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
    //                                             .build();

    //  public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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

        for (DailyRevenue dailyRevenue : getTypicalDailyRevenue()) {
            statistics.addDailyRevenue(dailyRevenue);
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

    public static List<DailyRevenue> getTypicalDailyRevenue() {
        return new ArrayList<>(Arrays.asList(DAILY_REVENUE1, DAILY_REVENUE2, DAILY_REVENUE3, DAILY_REVENUE4,
                DAILY_REVENUE5, DAILY_REVENUE6, DAILY_REVENUE7));
    }
}
