package seedu.address.model;

import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.place.CountryCode;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Place> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a place with the same identity as {@code place} exists in the address book.
     */
    boolean hasPlace(Place place);

    /**
     * Deletes the given place.
     * The place must exist in the address book.
     */
    void deletePlace(Place target);

    /**
     * Adds the given place.
     * {@code place} must not already exist in the address book.
     */
    void addPlace(Place place);

    /**
     * Replaces the given place {@code target} with {@code editedPlace}.
     * {@code target} must exist in the address book.
     * The place identity of {@code editedPlace} must not be the same as another existing place in the address book.
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
     * Generates the country chart.
     */
    Map<CountryCode, Integer> generateChartCountry();

    /**
     * Generates the rating chart.
     */
    Map<Rating, Integer> generateChartRating();

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

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
