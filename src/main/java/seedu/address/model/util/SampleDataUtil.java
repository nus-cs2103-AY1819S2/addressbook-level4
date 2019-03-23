package seedu.address.model.util;

import seedu.address.model.ReadOnlyRestOrRant;
import seedu.address.model.RestOrRant;
import seedu.address.model.menu.Code;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.menu.Name;
import seedu.address.model.menu.Price;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;

/**
 * Contains utility methods for populating {@code RestOrRant} with sample data.
 */
public class SampleDataUtil {

    public static Table[] getSampleTables() {
        return new Table[]{new Table("1", "4/4"), 
            new Table("2", "4/5"), new Table("3", "0/4")};
    }

    public static MenuItem[] getSampleMenuItems() {
        return new MenuItem[]{new MenuItem(new Name("Chicken Wings"), new Code("W09"), new Price("3.99")),
            new MenuItem(new Name("French Fries"), new Code("W12"), new Price("2.20")),
            new MenuItem(new Name("Salad"), new Code("A05"), new Price("5.50"))};
    }

    public static OrderItem[] getSampleOrderItems() {
        return new OrderItem[]{new OrderItem(new TableNumber("1"), new Code("W09"), 
                new Name("Chicken Wings"), 3),
            new OrderItem(new TableNumber("1"), new Code("W12"), new Name("French Fries"), 2),
            new OrderItem(new TableNumber("2"), new Code("A05"), new Name("Salad"), 1)};
    }

    public static DailyRevenue[] getSampleDailyReveue() {
        return new DailyRevenue[]{new DailyRevenue(new Day("14"), new Month("6"), new Year("1997"), (float) 123.45)};
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
        for (DailyRevenue sampleDailyRevenue : getSampleDailyReveue()) {
            sampleRestOrRant.getStatistics().addDailyRevenue(sampleDailyRevenue);
        }
        return sampleRestOrRant;
    }
}
