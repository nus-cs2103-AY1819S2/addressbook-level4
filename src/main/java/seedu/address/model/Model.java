package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.threshold.Threshold;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Default {@code Threshold} used for expiry.
     */
    Threshold DEFAULT_EXPIRY_THRESHOLD = new Threshold(Integer.valueOf(10));

    /**
     * Default {@code Threshold} used for low stock.
     */
    Threshold DEFAULT_LOW_STOCK_THRESHOLD = new Threshold(Integer.valueOf(20));

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
     * Returns the user prefs' inventory file path.
     */
    Path getInventoryFilePath();

    /**
     * Sets the user prefs' inventory file path.
     */
    void setInventoryFilePath(Path inventoryFilePath);

    /**
     * Replaces inventory data with the data in {@code inventory}.
     */
    void setInventory(ReadOnlyInventory inventory);

    /** Returns the Inventory */
    ReadOnlyInventory getInventory();

    /**
     * Returns true if a medicine with the same identity as {@code medicine} exists in the inventory.
     */
    boolean hasMedicine(Medicine medicine);

    /**
     * Deletes the given medicine.
     * The medicine must exist in the inventory.
     */
    void deleteMedicine(Medicine target);

    /**
     * Adds the given medicine.
     * {@code medicine} must not already exist in the inventory.
     */
    void addMedicine(Medicine medicine);

    /**
     * Replaces the given medicine {@code target} with {@code editedMedicine}.
     * {@code target} must exist in the inventory.
     * The identity of {@code editedMedicine} must not be the same as another existing medicine in the inventory.
     */
    void setMedicine(Medicine target, Medicine editedMedicine);

    /** Returns predicates used by the warning panel */
    WarningPanelPredicateAccessor getWarningPanelPredicateAccessor();

    /** Returns an unmodifiable view of the filtered medicine list to be used for the medicine panel */
    ObservableList<Medicine> getFilteredMedicineList();

    /** Returns an unmodifiable view of the filtered medicine list for expiry date warning */
    ObservableList<Medicine> getExpiringMedicinesList();

    /** Returns an unmodifiable view of the filtered medicine list for low stock warning */
    ObservableList<Medicine> getLowQuantityMedicinesList();

    /**
     * Updates the filter of the filtered medicine list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedicineList(Predicate<Medicine> predicate);

    /**
     * Updates the filter of the medicine list filtered by expiry date by the give {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpiringMedicineList(Predicate<Medicine> predicate);

    /**
     * Updates the filter of the medicine list filtered by quantity by the give {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLowStockMedicineList(Predicate<Medicine> predicate);

    /**
     * Returns true if the model has previous inventory states to restore.
     */
    boolean canUndoInventory();

    /**
     * Returns true if the model has undone inventory states to restore.
     */
    boolean canRedoInventory();

    /**
     * Restores the model's inventory to its previous state.
     */
    void undoInventory();

    /**
     * Restores the model's inventory to its previously undone state.
     */
    void redoInventory();

    /**
     * Saves the current inventory state for undo/redo.
     */
    void commitInventory();

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
