package seedu.address.model;

import javafx.beans.Observable;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.table.ReadOnlyTables;

/**
 * Unmodifiable view of RestOrRant
 */
public interface ReadOnlyRestOrRant extends Observable {

    /**
     * Returns an unmodifiable view of the menu list.
     */
    ReadOnlyMenu getMenu();

    /**
     * Returns an unmodifiable view of the order list.
     */
    ReadOnlyOrders getOrders();

    /**
     * Returns an unmodifiable view of the statistic list.
     */
    ReadOnlyStatistics getStatistics();

    /**
     * Returns an unmodifiable view of the table list.
     */
    ReadOnlyTables getTables();

}
