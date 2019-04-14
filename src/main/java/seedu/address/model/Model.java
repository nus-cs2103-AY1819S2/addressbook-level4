package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.apparel.Apparel;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Apparel> PREDICATE_SHOW_ALL_APPARELS = unused -> true;

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
    void setAddressBook(ReadOnlyFashionMatch addressBook);

    /** Returns the FashionMatch */
    ReadOnlyFashionMatch getAddressBook();

    /**
     * Returns true if a apparel with the same identity as {@code apparel} exists in the address book.
     */
    boolean hasApparel(Apparel apparel);

    /**
     * Deletes the given apparel.
     * The apparel must exist in the address book.
     */
    void deleteApparel(Apparel target);

    /**
     * Adds the given apparel.
     * {@code apparel} must not already exist in the address book.
     */
    void addApparel(Apparel apparel);

    /**
     * Replaces the given apparel {@code target} with {@code editedApparel}.
     * {@code target} must exist in the address book.
     * The apparel identity of {@code editedApparel} must not
     * be the same as another existing apparel in the address book.
     */
    void setPerson(Apparel target, Apparel editedApparel);

    /** Returns an unmodifiable view of the filtered apparel list */
    ObservableList<Apparel> getFilteredApparelList();

    /**
     * Updates the filter of the filtered apparel list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredApparelList(Predicate<Apparel> predicate);

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
     * Selected apparel in the filtered apparel list.
     * null if no apparel is selected.
     */
    ReadOnlyProperty<Apparel> selectedPersonProperty();

    /**
     * Returns the selected apparel in the filtered apparel list.
     * null if no apparel is selected.
     */
    Apparel getSelectedApparel();

    /**
     * Sets the selected apparel in the filtered apparel list.
     */
    void setSelectedPerson(Apparel apparel);

    /**
     * To get the apparel statistics.
     */
    String getTotalColor();
    String getFavApparel();
    String getFavColor();
    String getLeastFavApparel();
    String getCleanOrDirty();
}
