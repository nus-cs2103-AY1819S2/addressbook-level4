package seedu.address.model.Statistics;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.order.OrderItem;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of Statistics
 */
public interface ReadOnlyStatistics extends Observable {

    /**
     * Returns an unmodifiable view of the order item list for the bill.
     */
    ObservableList<Bill> getBillList();
}
