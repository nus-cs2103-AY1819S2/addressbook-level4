package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Map;
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
import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;
import seedu.address.model.place.exceptions.PlaceNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Place> filteredPlaces;
    private final SimpleObjectProperty<Place> selectedPlace = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPlaces = new FilteredList<>(versionedAddressBook.getPlaceList());
        filteredPlaces.addListener(this::ensureSelectedPlaceIsValid);
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
    public boolean hasPlace(Place place) {
        requireNonNull(place);
        return versionedAddressBook.hasPlace(place);
    }

    @Override
    public void deletePlace(Place target) {
        versionedAddressBook.removePlace(target);
    }

    @Override
    public void addPlace(Place place) {
        versionedAddressBook.addPlace(place);
        updateFilteredPlaceList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPlace(Place target, Place editedPlace) {
        requireAllNonNull(target, editedPlace);

        versionedAddressBook.setPlace(target, editedPlace);
    }

    //=========== Filtered Place List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Place} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Place> getFilteredPlaceList() {
        return filteredPlaces;
    }

    @Override
    public void updateFilteredPlaceList(Predicate<Place> predicate) {
        requireNonNull(predicate);
        filteredPlaces.setPredicate(predicate);
    }

    @Override
    public Map<CountryCode, Integer> generateChartCountry() {
        return versionedAddressBook.generateCountryChart();
    }

    @Override
    public Map<Rating, Integer> generateChartRating() {
        return versionedAddressBook.generateRatingChart();
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

    //=========== Selected place ===========================================================================

    @Override
    public ReadOnlyProperty<Place> selectedPlaceProperty() {
        return selectedPlace;
    }

    @Override
    public Place getSelectedPlace() {
        return selectedPlace.getValue();
    }

    @Override
    public void setSelectedPlace(Place place) {
        if (place != null && !filteredPlaces.contains(place)) {
            throw new PlaceNotFoundException();
        }
        selectedPlace.setValue(place);
    }

    /**
     * Ensures {@code selectedPlace} is a valid place in {@code filteredPlaces}.
     */
    private void ensureSelectedPlaceIsValid(ListChangeListener.Change<? extends Place> change) {
        while (change.next()) {
            if (selectedPlace.getValue() == null) {
                // null is always a valid selected place, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPlaceReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPlace.getValue());
            if (wasSelectedPlaceReplaced) {
                // Update selectedPlace to its new value.
                int index = change.getRemoved().indexOf(selectedPlace.getValue());
                selectedPlace.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPlaceRemoved = change.getRemoved().stream()
                    .anyMatch(removedPlace -> selectedPlace.getValue().isSamePlace(removedPlace));
            if (wasSelectedPlaceRemoved) {
                // Select the place that came before it in the list,
                // or clear the selection if there is no such place.
                selectedPlace.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && filteredPlaces.equals(other.filteredPlaces)
                && Objects.equals(selectedPlace.get(), other.selectedPlace.get());
    }

}
