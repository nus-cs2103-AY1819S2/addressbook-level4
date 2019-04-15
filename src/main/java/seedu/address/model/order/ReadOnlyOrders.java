package seedu.address.model.order;

import java.util.Optional;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.menu.Code;
import seedu.address.model.table.TableNumber;

/**
 * Unmodifiable view of RestOrRant's orders
 */
public interface ReadOnlyOrders extends Observable {

    /**
     * Returns an unmodifiable view of the order items list.
     */
    ObservableList<OrderItem> getOrderItemList();

    /**
     * Retrieves the order item in the list that has table number {@code tableNumber} and item code {@code itemCode}.
     */
    Optional<OrderItem> getOrderItem(TableNumber tableNumber, Code itemCode);
}
