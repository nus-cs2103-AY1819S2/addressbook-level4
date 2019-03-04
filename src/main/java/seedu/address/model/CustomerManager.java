package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.exceptions.CustomerNotFoundException;

/**
 * Represents the in-memory customer of the address book data.
 */
public class CustomerManager implements CustomerModel {
    private static final Logger logger = LogsCenter.getLogger(CustomerManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;
    private final SimpleObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();

    /**
     * Initializes a CustomerManager with the given addressBook and userPrefs.
     */
    public CustomerManager(VersionedAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = addressBook;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(versionedAddressBook.getCustomerList());
        filteredCustomers.addListener(this::ensureSelectedCustomerIsValid);
    }

    public CustomerManager() {
        this(new VersionedAddressBook(new AddressBook()), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return versionedAddressBook.hasCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        versionedAddressBook.removeCustomer(target);
    }

    @Override
    public void addCustomer(Customer customer) {
        versionedAddressBook.addCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        versionedAddressBook.setCustomer(target, editedCustomer);
    }

    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredCustomerList() {
        return filteredCustomers;
    }

    @Override
    public void updateFilteredCustomerList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        filteredCustomers.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected customer ===========================================================================

    @Override
    public ReadOnlyProperty<Customer> selectedCustomerProperty() {
        return selectedCustomer;
    }

    @Override
    public Customer getSelectedCustomer() {
        return selectedCustomer.getValue();
    }

    @Override
    public void setSelectedCustomer(Customer customer) {
        if (customer != null && !filteredCustomers.contains(customer)) {
            throw new CustomerNotFoundException();
        }
        selectedCustomer.setValue(customer);
    }

    /**
     * Ensures {@code selectedCustomer} is a valid customer in {@code filteredCustomers}.
     */
    private void ensureSelectedCustomerIsValid(ListChangeListener.Change<? extends Customer> change) {
        while (change.next()) {
            if (selectedCustomer.getValue() == null) {
                // null is always a valid selected customer, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedCustomerReplaced =
                change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedCustomer.getValue());
            if (wasSelectedCustomerReplaced) {
                // Update selectedCustomer to its new value.
                int index = change.getRemoved().indexOf(selectedCustomer.getValue());
                selectedCustomer.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedCustomerRemoved = change.getRemoved().stream()
                .anyMatch(removedCustomer -> selectedCustomer.getValue().isSameCustomer(removedCustomer));
            if (wasSelectedCustomerRemoved) {
                // Select the customer that came before it in the list,
                // or clear the selection if there is no such customer.
                selectedCustomer.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CustomerManager)) {
            return false;
        }

        // state check
        CustomerManager other = (CustomerManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredCustomers.equals(other.filteredCustomers)
            && Objects.equals(selectedCustomer.get(), other.selectedCustomer.get());
    }

}
