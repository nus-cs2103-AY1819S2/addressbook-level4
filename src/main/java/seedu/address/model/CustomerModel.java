package seedu.address.model;

import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.model.customer.Customer;

/**
 * The API of the Model component.
 */
public interface CustomerModel extends Model {

    /**
     * Selected customer in the filtered customer list.
     * null if no customer is selected.
     */
    ReadOnlyProperty<Customer> selectedCustomerProperty();

    /**
     * Returns the selected customer in the filtered customer list.
     * null if no customer is selected.
     */
    Customer getSelectedCustomer();

    /**
     * Sets the selected customer in the filtered customer list.
     */
    void setSelectedCustomer(Customer customer);

    /**
     * Returns an unmodifiable view of the filtered customer list
     */
    ObservableList<Customer> getFilteredCustomerList();

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCustomerList(Predicate<Customer> predicate);

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasCustomer(Customer customer);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deleteCustomer(Customer target);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addCustomer(Customer customer);

    /**
     * Replaces the given customer {@code target} with {@code editedCustomer}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the
     * address book.
     */
    void setCustomer(Customer target, Customer editedCustomer);
}
