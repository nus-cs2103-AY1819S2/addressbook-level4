package seedu.travel.model;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.travel.commons.core.GuiSettings;
import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.exceptions.PlaceNotFoundException;

/**
 * Represents the in-memory model of the travel book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedTravelBuddy versionedTravelBuddy;
    private final UserPrefs userPrefs;
    private final FilteredList<Place> filteredPlaces;
    private final SimpleObjectProperty<Place> selectedPlace = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given travelBuddy and userPrefs.
     */
    public ModelManager(ReadOnlyTravelBuddy travelBuddy, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(travelBuddy, userPrefs);

        logger.fine("Initializing with travel book: " + travelBuddy + " and user prefs " + userPrefs);

        versionedTravelBuddy = new VersionedTravelBuddy(travelBuddy);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPlaces = new FilteredList<>(versionedTravelBuddy.getPlaceList());
        filteredPlaces.addListener(this::ensureSelectedPlaceIsValid);
    }

    public ModelManager() {
        this(new TravelBuddy(), new UserPrefs());
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
    public Path getTravelBuddyFilePath() {
        return userPrefs.getTravelBuddyFilePath();
    }

    @Override
    public void setTravelBuddyFilePath(Path travelBuddyFilePath) {
        requireNonNull(travelBuddyFilePath);
        userPrefs.setTravelBuddyFilePath(travelBuddyFilePath);
    }

    //=========== TravelBuddy ================================================================================

    @Override
    public void setTravelBuddy(ReadOnlyTravelBuddy travelBuddy) {
        versionedTravelBuddy.resetData(travelBuddy);
    }

    @Override
    public ReadOnlyTravelBuddy getTravelBuddy() {
        return versionedTravelBuddy;
    }

    @Override
    public boolean hasPlace(Place place) {
        requireNonNull(place);
        return versionedTravelBuddy.hasPlace(place);
    }

    @Override
    public void deletePlace(Place target) {
        versionedTravelBuddy.removePlace(target);
    }

    @Override
    public void addPlace(Place place) {
        versionedTravelBuddy.addPlace(place);
        updateFilteredPlaceList(PREDICATE_SHOW_ALL_PLACES);
    }

    @Override
    public void setPlace(Place target, Place editedPlace) {
        requireAllNonNull(target, editedPlace);

        versionedTravelBuddy.setPlace(target, editedPlace);
    }

    //=========== Filtered Place List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Place} backed by the internal list of
     * {@code versionedTravelBuddy}
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

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoTravelBuddy() {
        return versionedTravelBuddy.canUndo();
    }

    @Override
    public boolean canRedoTravelBuddy() {
        return versionedTravelBuddy.canRedo();
    }

    @Override
    public void undoTravelBuddy() {
        versionedTravelBuddy.undo();
    }

    @Override
    public void redoTravelBuddy() {
        versionedTravelBuddy.redo();
    }

    @Override
    public void commitTravelBuddy() {
        versionedTravelBuddy.commit();
    }

    @Override
    public void commitChart() {
        versionedTravelBuddy.commitChart();
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
        return versionedTravelBuddy.equals(other.versionedTravelBuddy)
                && userPrefs.equals(other.userPrefs)
                && filteredPlaces.equals(other.filteredPlaces)
                && Objects.equals(selectedPlace.get(), other.selectedPlace.get());
    }

}
