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
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.exceptions.RestaurantNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Restaurant> filteredRestaurants;
    private final SimpleObjectProperty<Restaurant> selectedRestaurant = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRestaurants = new FilteredList<>(versionedAddressBook.getRestaurantList());
        filteredRestaurants.addListener(this::ensureSelectedRestaurantIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasRestaurant(Restaurant restaurant) {
        requireNonNull(restaurant);
        return versionedAddressBook.hasRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant target) {
        versionedAddressBook.removeRestaurant(target);
    }

    @Override
    public void addRestaurant(Restaurant restaurant) {
        versionedAddressBook.addRestaurant(restaurant);
        updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
    }

    @Override
    public void setRestaurant(Restaurant target, Restaurant editedRestaurant) {
        requireAllNonNull(target, editedRestaurant);

        versionedAddressBook.setRestaurant(target, editedRestaurant);
    }

    //=========== Filtered Restaurant List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Restaurant} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Restaurant> getFilteredRestaurantList() {
        return filteredRestaurants;
    }

    @Override
    public void updateFilteredRestaurantList(Predicate<Restaurant> predicate) {
        requireNonNull(predicate);
        filteredRestaurants.setPredicate(predicate);
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

    //=========== Selected restaurant ===========================================================================

    @Override
    public ReadOnlyProperty<Restaurant> selectedRestaurantProperty() {
        return selectedRestaurant;
    }

    @Override
    public Restaurant getSelectedRestaurant() {
        return selectedRestaurant.getValue();
    }

    @Override
    public void setSelectedRestaurant(Restaurant restaurant) {
        if (restaurant != null && !filteredRestaurants.contains(restaurant)) {
            throw new RestaurantNotFoundException();
        }
        selectedRestaurant.setValue(restaurant);
    }

    /**
     * Ensures {@code selectedRestaurant} is a valid restaurant in {@code filteredRestaurants}.
     */
    private void ensureSelectedRestaurantIsValid(ListChangeListener.Change<? extends Restaurant> change) {
        while (change.next()) {
            if (selectedRestaurant.getValue() == null) {
                // null is always a valid selected restaurant, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedRestaurantReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedRestaurant.getValue());
            if (wasSelectedRestaurantReplaced) {
                // Update selectedRestaurant to its new value.
                int index = change.getRemoved().indexOf(selectedRestaurant.getValue());
                selectedRestaurant.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedRestaurantRemoved = change.getRemoved().stream()
                    .anyMatch(removedRestaurant -> selectedRestaurant.getValue().isSameRestaurant(removedRestaurant));
            if (wasSelectedRestaurantRemoved) {
                // Select the restaurant that came before it in the list,
                // or clear the selection if there is no such restaurant.
                selectedRestaurant.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredRestaurants.equals(other.filteredRestaurants)
                && Objects.equals(selectedRestaurant.get(), other.selectedRestaurant.get());
    }

}
