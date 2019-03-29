package seedu.travel.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.travel.commons.core.GuiSettings;
import seedu.travel.model.place.Place;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Place> PREDICATE_SHOW_ALL_PLACES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' TravelBuddy file path.
     */
    Path getTravelBuddyFilePath();

    /**
     * Sets the user prefs' TravelBuddy file path.
     */
    void setTravelBuddyFilePath(Path travelBuddyFilePath);

    /**
     * Replaces TravelBuddy data with the data in {@code travelBuddy}.
     */
    void setTravelBuddy(ReadOnlyTravelBuddy travelBuddy);

    /** Returns the TravelBuddy */
    ReadOnlyTravelBuddy getTravelBuddy();

    /**
     * Returns true if a place with the same identity as {@code place} exists in the TravelBuddy.
     */
    boolean hasPlace(Place place);

    /**
     * Deletes the given place.
     * The place must exist in the TravelBuddy.
     */
    void deletePlace(Place target);

    /**
     * Adds the given place.
     * {@code place} must not already exist in the TravelBuddy.
     */
    void addPlace(Place place);

    /**
     * Replaces the given place {@code target} with {@code editedPlace}.
     * {@code target} must exist in the TravelBuddy.
     * The place identity of {@code editedPlace} must not be the same as another existing place in the TravelBuddy.
     */
    void setPlace(Place target, Place editedPlace);

    /** Returns an unmodifiable view of the filtered place list */
    ObservableList<Place> getFilteredPlaceList();

    /**
     * Updates the filter of the filtered place list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlaceList(Predicate<Place> predicate);

    /**
     * Returns true if the model has previous TravelBuddy states to restore.
     */
    boolean canUndoTravelBuddy();

    /**
     * Returns true if the model has undone TravelBuddy states to restore.
     */
    boolean canRedoTravelBuddy();

    /**
     * Restores the model's TravelBuddy to its previous state.
     */
    void undoTravelBuddy();

    /**
     * Restores the model's TravelBuddy to its previously undone state.
     */
    void redoTravelBuddy();

    /**
     * Saves the current TravelBuddy state for undo/redo.
     */
    void commitTravelBuddy();

    /**
     * Saves the current Chart state.
     */
    void commitChart();

    /**
     * Selected place in the filtered place list.
     * null if no place is selected.
     */
    ReadOnlyProperty<Place> selectedPlaceProperty();

    /**
     * Returns the selected place in the filtered place list.
     * null if no place is selected.
     */
    Place getSelectedPlace();

    /**
     * Sets the selected place in the filtered place list.
     */
    void setSelectedPlace(Place place);
}
