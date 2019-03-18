package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.table.Table;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<OrderItem> PREDICATE_MATCHING_NO_ORDER_ITEMS = unused -> false;
    private static final Predicate<MenuItem> PREDICATE_MATCHING_NO_MENU_ITEMS = unused -> false;
    private static final Predicate<Table> PREDICATE_MATCHING_NO_TABLES = unused -> false;
    private static final Predicate<DailyRevenue> PREDICATE_MATCHING_NO_DAILY_REVENUE = unused -> false;

    /**
     * Updates {@code model}'s order item filtered list to display only {@code toDisplay}.
     */
    public static void setOrderItemFilteredList(Model model, List<OrderItem> toDisplay) {
        Optional<Predicate<OrderItem>> predicate =
                toDisplay.stream().map(ModelHelper::getOrderItemPredicateMatching).reduce(Predicate::or);
        model.updateFilteredOrderItemList(predicate.orElse(PREDICATE_MATCHING_NO_ORDER_ITEMS));
    }

    /**
     * @see ModelHelper#setOrderItemFilteredList(Model, List)
     */
    public static void setOrderItemFilteredList(Model model, OrderItem... toDisplay) {
        setOrderItemFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code OrderItem} equals to {@code other}.
     */
    private static Predicate<OrderItem> getOrderItemPredicateMatching(OrderItem other) {
        return item -> item.equals(other);
    }

    /**
     * Updates {@code model}'s menu item filtered list to display only {@code toDisplay}.
     */
    public static void setMenuItemFilteredList(Model model, List<MenuItem> toDisplay) {
        Optional<Predicate<MenuItem>> predicate =
                toDisplay.stream().map(ModelHelper::getMenuItemPredicateMatching).reduce(Predicate::or);
        model.updateFilteredMenuItemList(predicate.orElse(PREDICATE_MATCHING_NO_MENU_ITEMS));
    }

    /**
     * @see ModelHelper#setMenuItemFilteredList(Model, List)
     */
    public static void setMenuItemFilteredList(Model model, MenuItem... toDisplay) {
        setMenuItemFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code MenuItem} equals to {@code other}.
     */
    private static Predicate<MenuItem> getMenuItemPredicateMatching(MenuItem other) {
        return item -> item.equals(other);
    }

    /**
     * Updates {@code model}'s table filtered list to display only {@code toDisplay}.
     */
    public static void setTableFilteredList(Model model, List<Table> toDisplay) {
        Optional<Predicate<Table>> predicate =
                toDisplay.stream().map(ModelHelper::getTablePredicateMatching).reduce(Predicate::or);
        model.updateFilteredTableList(predicate.orElse(PREDICATE_MATCHING_NO_TABLES));
    }

    /**
     * @see ModelHelper#setTableFilteredList(Model, List)
     */
    public static void setTableFilteredList(Model model, Table... toDisplay) {
        setTableFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Table} equals to {@code other}.
     */
    private static Predicate<Table> getTablePredicateMatching(Table other) {
        return item -> item.equals(other);
    }


    /**
     * Updates {@code model}'s daily revenue filtered list to display only {@code toDisplay}.
     */
    public static void setDailyRevenueFilteredList(Model model, List<DailyRevenue> toDisplay) {
        Optional<Predicate<DailyRevenue>> predicate =
                toDisplay.stream().map(ModelHelper::getDailyRevenuePredicateMatching).reduce(Predicate::or);
        model.updateFilteredDailyRevenueList(predicate.orElse(PREDICATE_MATCHING_NO_DAILY_REVENUE));
    }

    /**
     * @see ModelHelper#setDailyRevenueFilteredList(Model, List)
     */
    public static void setDailyRevenueFilteredList(Model model, DailyRevenue... toDisplay) {
        setDailyRevenueFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code DailyRevenue} equals to {@code other}.
     */
    private static Predicate<DailyRevenue> getDailyRevenuePredicateMatching(DailyRevenue other) {
        return item -> item.equals(other);
    }
}
