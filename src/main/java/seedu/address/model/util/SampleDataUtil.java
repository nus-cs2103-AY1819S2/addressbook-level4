package seedu.address.model.util;

import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.OrderItemStatus;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * Contains utility methods for populating {@code RestOrRant} with sample data.
 */
public class SampleDataUtil {

    public static Table[] getSampleTables() {
        return new Table[]{
            new Table("1", "0/2"), new Table("2", "2/2"),
            new Table("3", "1/2"), new Table("4", "0/2"),
            new Table("5", "2/2"), new Table("6", "0/2"),
            new Table("7", "2/2"), new Table("8", "0/2"),
            new Table("9", "0/2"), new Table("10", "2/2"),
            new Table("11", "4/4"), new Table("12", "0/2"),
            new Table("13", "0/0"), new Table("14", "0/2"),
            new Table("15", "0/2"), new Table("16", "2/2"),
            new Table("17", "1/2"), new Table("18", "0/2"),
            new Table("19", "0/2"), new Table("20", "0/2"),
            new Table("21", "0/2"), new Table("22", "3/4"),
            new Table("23", "4/4"), new Table("24", "4/4"),
            new Table("25", "0/4"), new Table("26", "0/4"),
            new Table("27", "0/4"), new Table("28", "0/4"),
            new Table("29", "0/4"), new Table("30", "0/2"),
            new Table("31", "0/2"), new Table("32", "0/2"),
            new Table("33", "0/2"), new Table("34", "0/2"),
            new Table("35", "0/6")
            };
    }

    public static MenuItem[] getSampleMenuItems() {
        return new MenuItem[]{
            new MenuItem(new Name("Chicken Burger"), new Code("W01"), new Price("2.50"), 3),
            new MenuItem(new Name("Fish Burger"), new Code("W02"), new Price("3.00"), 2),
            new MenuItem(new Name("Prawn Burger"), new Code("W03"), new Price("4.00"), 1),
            new MenuItem(new Name("Beef Burger"), new Code("W04"), new Price("4.00"), 0),
            new MenuItem(new Name("Premium Beef Burger"), new Code("W05"), new Price("6.00"), 0),
            new MenuItem(new Name("Lobster Burger"), new Code("W06"), new Price("10.00"), 0),
            new MenuItem(new Name("Cheeseburger"), new Code("W07"), new Price("2.00"), 0),
            new MenuItem(new Name("Hamburger"), new Code("W08"), new Price("1.50"), 0),
            new MenuItem(new Name("Chicken Wings"), new Code("W09"), new Price("4.50"), 0),
            new MenuItem(new Name("Chicken Nuggets"), new Code("W10"), new Price("4.50"), 0),
            new MenuItem(new Name("Chicken Dippers"), new Code("W11"), new Price("4.50"), 6),
            new MenuItem(new Name("French Fries"), new Code("W12"), new Price("2.50"), 3),
            new MenuItem(new Name("Coke"), new Code("W13"), new Price("1.50"), 0),
            new MenuItem(new Name("Sprite"), new Code("W14"), new Price("1.50"), 0),
            new MenuItem(new Name("Ice Lemon Tea"), new Code("W15"), new Price("2.00"), 0)
            };
    }

    public static OrderItem[] getSampleOrderItems() {
        return new OrderItem[]{
            new OrderItem(new TableNumber("22"), new Code("W12"), new Name("French Fries"), new OrderItemStatus(3, 2)),
            new OrderItem(new TableNumber("2"), new Code("W12"), new Name("French Fries"), new OrderItemStatus(2, 0)),
            new OrderItem(new TableNumber("2"), new Code("W07"), new Name("Cheeseburger"), new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("5"), new Code("W03"), new Name("Prawn Burger"), new OrderItemStatus(2, 2)),
            new OrderItem(new TableNumber("5"), new Code("W10"), new Name("Chicken Nuggets"),
                    new OrderItemStatus(1, 0)),
            new OrderItem(new TableNumber("5"), new Code("W09"), new Name("Chicken Wings"), new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("11"), new Code("W02"), new Name("Fish Burger"), new OrderItemStatus(4, 4)),
            new OrderItem(new TableNumber("7"), new Code("W06"), new Name("Lobster Burger"), new OrderItemStatus(2, 2)),
            new OrderItem(new TableNumber("7"), new Code("W12"), new Name("French Fries"), new OrderItemStatus(2, 2)),
            new OrderItem(new TableNumber("17"), new Code("W13"), new Name("Coke"), new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("17"), new Code("W12"), new Name("French Fries"), new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("17"), new Code("W01"), new Name("Chicken Burger"),
                    new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("22"), new Code("W09"), new Name("Chicken Wings"), new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("22"), new Code("W03"), new Name("Prawn Burger"), new OrderItemStatus(1, 1)),
            new OrderItem(new TableNumber("22"), new Code("W02"), new Name("Fish Burger"), new OrderItemStatus(2, 2)),
            new OrderItem(new TableNumber("24"), new Code("W01"), new Name("Chicken Burger"),
                    new OrderItemStatus(3, 3)),
            new OrderItem(new TableNumber("24"), new Code("W12"), new Name("French Fries"), new OrderItemStatus(3, 3)),
            new OrderItem(new TableNumber("5"), new Code("W13"), new Name("Coke"), new OrderItemStatus(1, 1))
            };
    }

    public static Revenue[] getSampleRevenue() {
        return new Revenue[]{
            new Revenue(new Day("26"), new Month("3"), new Year("2019"), (float) 185.0),
            new Revenue(new Day("27"), new Month("3"), new Year("2019"), (float) 100.5),
            new Revenue(new Day("28"), new Month("3"), new Year("2019"), (float) 75.0),
            new Revenue(new Day("29"), new Month("3"), new Year("2019"), (float) 90.0),
            new Revenue(new Day("30"), new Month("3"), new Year("2019"), (float) 122.5),
            new Revenue(new Day("31"), new Month("3"), new Year("2019"), (float) 88.0),
            new Revenue(new Day("1"), new Month("4"), new Year("2019"), (float) 101.5),
            new Revenue(new Day("2"), new Month("4"), new Year("2019"), (float) 90.0),
            new Revenue(new Day("3"), new Month("4"), new Year("2019"), (float) 99.5),
            new Revenue(new Day("4"), new Month("4"), new Year("2019"), (float) 75.0),
            new Revenue(new Day("5"), new Month("4"), new Year("2019"), (float) 90.5),
            new Revenue(new Day("6"), new Month("4"), new Year("2019"), (float) 102.0),
            new Revenue(new Day("7"), new Month("4"), new Year("2019"), (float) 120.0),
            new Revenue(new Day("8"), new Month("4"), new Year("2019"), (float) 98.0),
            new Revenue(new Day("9"), new Month("4"), new Year("2019"), (float) 99.5),
            new Revenue(new Day("10"), new Month("4"), new Year("2019"), (float) 102.5)
        };
    }

    public static ReadOnlyRestOrRant getSampleRestOrRant() {
        RestOrRant sampleRestOrRant = new RestOrRant();
        for (Table sampleTable : getSampleTables()) {
            sampleRestOrRant.getTables().addTable(sampleTable);
        }
        for (MenuItem sampleMenuItem : getSampleMenuItems()) {
            sampleRestOrRant.getMenu().addMenuItem(sampleMenuItem);
        }
        for (OrderItem sampleOrderItem : getSampleOrderItems()) {
            sampleRestOrRant.getOrders().addOrderItem(sampleOrderItem);
        }
        for (Revenue sampleRevenue : getSampleRevenue()) {
            sampleRestOrRant.getStatistics().addRevenue(sampleRevenue);
        }
        return sampleRestOrRant;
    }
}
