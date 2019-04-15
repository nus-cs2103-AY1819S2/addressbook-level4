package seedu.address.testutil;

import seedu.address.model.RestOrRant;
import seedu.address.model.menu.MenuItem;
import seedu.address.model.order.OrderItem;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.table.Table;

/**
 * A utility class to help with building RestOrRant objects.
 * Example usage: <br>
 *     {@code RestOrRant ab = new RestOrRantBuilder().build();}
 */
public class RestOrRantBuilder {

    private RestOrRant restOrRant;

    public RestOrRantBuilder() {
        restOrRant = new RestOrRant();
    }

    public RestOrRantBuilder(RestOrRant restOrRant) {
        this.restOrRant = restOrRant;
    }

    /**
     * Adds a new {@code OrderItem} to the {@code RestOrRant} that we are building.
     */
    public RestOrRantBuilder withOrderItem(OrderItem orderItem) {
        restOrRant.getOrders().addOrderItem(orderItem);
        return this;
    }

    /**
     * Adds a new {@code MenuItem} to the {@code RestOrRant} that we are building.
     */
    public RestOrRantBuilder withMenuItem(MenuItem menuItem) {
        restOrRant.getMenu().addMenuItem(menuItem);
        return this;
    }

    /**
     * Adds a new {@code Table} to the {@code RestOrRant} that we are building.
     */
    public RestOrRantBuilder withTable(Table table) {
        restOrRant.getTables().addTable(table);
        return this;
    }

    /**
     * Adds a new {@code Revenue} to the {@code RestOrRant} that we are building.
     */
    public RestOrRantBuilder withRevenue(Revenue revenue) {
        restOrRant.getStatistics().addRevenue(revenue);
        return this;
    }

    public RestOrRant build() {
        return restOrRant;
    }
}
