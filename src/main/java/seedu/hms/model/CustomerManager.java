package seedu.hms.model;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.hms.commons.core.GuiSettings;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.exceptions.CustomerNotFoundException;

/**
 * Represents the in-memory customer of the hms book data.
 */
public class CustomerManager implements CustomerModel {
    private static final Logger logger = LogsCenter.getLogger(CustomerManager.class);

    private final VersionedHotelManagementSystem versionedHotelManagementSystem;
    private final UserPrefs userPrefs;
    private final FilteredList<Customer> filteredCustomers;
    private final SimpleObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();

    /**
     * Initializes a CustomerManager with the given hotelManagementSystem and userPrefs.
     */
    public CustomerManager(VersionedHotelManagementSystem hotelManagementSystem, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(hotelManagementSystem, userPrefs);

        logger.fine("Initializing with hms book: " + hotelManagementSystem + " and user prefs " + userPrefs);

        versionedHotelManagementSystem = hotelManagementSystem;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(versionedHotelManagementSystem.getCustomerList());
        filteredCustomers.addListener(this::ensureSelectedCustomerIsValid);
    }

    public CustomerManager() {
        this(new VersionedHotelManagementSystem(new HotelManagementSystem()), new UserPrefs());
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
    public Path getHotelManagementSystemFilePath() {
        return userPrefs.getHotelManagementSystemFilePath();
    }

    @Override
    public void setHotelManagementSystemFilePath(Path hotelManagementSystemFilePath) {
        requireNonNull(hotelManagementSystemFilePath);
        userPrefs.setHotelManagementSystemFilePath(hotelManagementSystemFilePath);
    }

    //=========== HotelManagementSystem ================================================================================

    @Override
    public ReadOnlyHotelManagementSystem getHotelManagementSystem() {
        return versionedHotelManagementSystem;
    }

    @Override
    public void setHotelManagementSystem(ReadOnlyHotelManagementSystem hotelManagementSystem) {
        versionedHotelManagementSystem.resetData(hotelManagementSystem);
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return versionedHotelManagementSystem.hasCustomer(customer);
    }

    @Override
    public void deleteCustomer(Customer target) {
        versionedHotelManagementSystem.removeCustomer(target);
    }

    @Override
    public void addCustomer(Customer customer) {
        versionedHotelManagementSystem.addCustomer(customer);
        updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        versionedHotelManagementSystem.setCustomer(target, editedCustomer);
    }

    //=========== Filtered Customer List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Customer} backed by the internal list of
     * {@code versionedHotelManagementSystem}
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
    public boolean canUndoHotelManagementSystem() {
        return versionedHotelManagementSystem.canUndo();
    }

    @Override
    public boolean canRedoHotelManagementSystem() {
        return versionedHotelManagementSystem.canRedo();
    }

    @Override
    public void undoHotelManagementSystem() {
        versionedHotelManagementSystem.undo();
    }

    @Override
    public void redoHotelManagementSystem() {
        versionedHotelManagementSystem.redo();
    }

    @Override
    public void commitHotelManagementSystem() {
        versionedHotelManagementSystem.commit();
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
        return versionedHotelManagementSystem.equals(other.versionedHotelManagementSystem)
            && userPrefs.equals(other.userPrefs)
            && filteredCustomers.equals(other.filteredCustomers)
            && Objects.equals(selectedCustomer.get(), other.selectedCustomer.get());
    }

}
