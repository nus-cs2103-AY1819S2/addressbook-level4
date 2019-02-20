package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.medicine.Medicine;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Medicine> PREDICATE_SHOW_ALL_MEDICINES = unused -> true;

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
     * Returns true if a medicine with the same identity as {@code medicine} exists in the address book.
     */
    boolean hasMedicine(Medicine medicine);

    /**
     * Deletes the given medicine.
     * The medicine must exist in the address book.
     */
    void deleteMedicine(Medicine target);

    /**
     * Adds the given medicine.
     * {@code medicine} must not already exist in the address book.
     */
    void addMedicine(Medicine medicine);

    /**
     * Replaces the given medicine {@code target} with {@code editedMedicine}.
     * {@code target} must exist in the address book.
     * The medicine identity of {@code editedMedicine} must not be the same as another existing medicine in the address book.
     */
    void setMedicine(Medicine target, Medicine editedMedicine);

    /** Returns an unmodifiable view of the filtered medicine list */
    ObservableList<Medicine> getFilteredMedicineList();

    /**
     * Updates the filter of the filtered medicine list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedicineList(Predicate<Medicine> predicate);

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
     * Selected medicine in the filtered medicine list.
     * null if no medicine is selected.
     */
    ReadOnlyProperty<Medicine> selectedMedicineProperty();

    /**
     * Returns the selected medicine in the filtered medicine list.
     * null if no medicine is selected.
     */
    Medicine getSelectedMedicine();

    /**
     * Sets the selected medicine in the filtered medicine list.
     */
    void setSelectedMedicine(Medicine medicine);
}
