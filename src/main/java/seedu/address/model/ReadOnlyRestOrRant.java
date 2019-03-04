package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.order.OrderItem;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of RestOrRant
 */
public interface ReadOnlyRestOrRant extends Observable {

    /**
     * Returns an unmodifiable view of the order items list.
     */
    ReadOnlyOrders getOrders();

}
