package seedu.address.model;

import javafx.beans.Observable;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.model.order.ReadOnlyOrders;

/**
 * Unmodifiable view of RestOrRant
 */
public interface ReadOnlyRestOrRant extends Observable {

    ReadOnlyMenu getMenu();

    ReadOnlyOrders getOrders();

}
