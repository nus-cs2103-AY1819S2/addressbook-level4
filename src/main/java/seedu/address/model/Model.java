package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.restaurant.Restaurant;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Restaurant> PREDICATE_SHOW_ALL_RESTAURANTS = unused -> true;

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
     * Returns the user prefs' food diary file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' food diary file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces food diary data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a restaurant with the same identity as {@code restaurant} exists in the food diary.
     */
    boolean hasRestaurant(Restaurant restaurant);

    /**
     * Deletes the given restaurant.
     * The restaurant must exist in the food diary.
     */
    void deleteRestaurant(Restaurant target);

    /**
     * Adds the given restaurant.
     * {@code restaurant} must not already exist in the food diary.
     */
    void addRestaurant(Restaurant restaurant);

    /**
     * Replaces the given restaurant {@code target} with {@code editedRestaurant}.
     * {@code target} must exist in the food diary.
     * The restaurant identity of {@code editedRestaurant} must not be the same as another existing restaurant
     * in the food diary.
     */
    void setRestaurant(Restaurant target, Restaurant editedRestaurant);

    /** Returns an unmodifiable view of the filtered restaurant list */
    ObservableList<Restaurant> getFilteredRestaurantList();

    /**
     * Updates the filter of the filtered restaurant list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRestaurantList(Predicate<Restaurant> predicate);

    /**
     * Returns true if the model has previous food diary states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone food diary states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's food diary to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's food diary to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current food diary state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected restaurant in the filtered restaurant list.
     * null if no restaurant is selected.
     */
    ReadOnlyProperty<Restaurant> selectedRestaurantProperty();

    /**
     * Returns the selected restaurant in the filtered restaurant list.
     * null if no restaurant is selected.
     */
    Restaurant getSelectedRestaurant();

    /**
     * Sets the selected restaurant in the filtered restaurant list.
     */
    void setSelectedRestaurant(Restaurant restaurant);
}
