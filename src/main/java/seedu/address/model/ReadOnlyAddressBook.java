package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.customer.Customer;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the customers list.
     * This list will not contain any duplicate customers.
     */
    ObservableList<Customer> getCustomerList();

}
