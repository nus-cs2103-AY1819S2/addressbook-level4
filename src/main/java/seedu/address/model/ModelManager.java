package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;

/**
 * Represents the in-memory model of the inventory data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedInventory versionedInventory;
    private final UserPrefs userPrefs;
    private final FilteredList<Medicine> filteredMedicines;
    private final FilteredList<Medicine> medicinesExpiring;
    private final FilteredList<Medicine> medicinesLowQuantity;
    private final SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given inventory and userPrefs.
     */
    public ModelManager(ReadOnlyInventory inventory, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(inventory, userPrefs);

        logger.fine("Initializing with inventory: " + inventory + " and user prefs " + userPrefs);

        versionedInventory = new VersionedInventory(inventory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMedicines = new FilteredList<>(versionedInventory.getMedicineList());
        filteredMedicines.addListener(this::ensureSelectedMedicineIsValid);
        medicinesExpiring = new FilteredList<>(versionedInventory.getMedicineList());
        medicinesLowQuantity = new FilteredList<>(versionedInventory.getMedicineList());
        setPredicates();

    }

    public ModelManager() {
        this(new Inventory(), new UserPrefs());
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
    public Path getInventoryFilePath() {
        return userPrefs.getInventoryFilePath();
    }

    @Override
    public void setInventoryFilePath(Path inventoryFilePath) {
        requireNonNull(inventoryFilePath);
        userPrefs.setInventoryFilePath(inventoryFilePath);
    }

    //=========== Inventory ================================================================================

    @Override
    public void setInventory(ReadOnlyInventory inventory) {
        versionedInventory.resetData(inventory);
    }

    @Override
    public ReadOnlyInventory getInventory() {
        return versionedInventory;
    }

    @Override
    public boolean hasMedicine(Medicine medicine) {
        requireNonNull(medicine);
        return versionedInventory.hasMedicine(medicine);
    }

    @Override
    public void deleteMedicine(Medicine target) {
        versionedInventory.removeMedicine(target);
    }

    @Override
    public void addMedicine(Medicine medicine) {
        versionedInventory.addMedicine(medicine);
        updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
    }

    @Override
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        versionedInventory.setMedicine(target, editedMedicine);
    }

    //=========== Filtered Medicine List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Medicine} backed by the internal list of
     * {@code versionedInventory}
     */
    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return filteredMedicines;
    }

    @Override
    public ObservableList<Medicine> getExpiringMedicinesList() {
        return medicinesExpiring;
    }

    @Override
    public ObservableList<Medicine> getLowQuantityMedicinesList() {
        return medicinesLowQuantity;
    }

    @Override
    public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        filteredMedicines.setPredicate(predicate);
    }

    /**
     * Sets initial predicates for all filtered lists.
     */
    private void setPredicates() {
        filteredMedicines.setPredicate(PREDICATE_SHOW_ALL_MEDICINES);
        medicinesLowQuantity.setPredicate(medicine -> medicine.getQuantity().getNumericValue() < 20);
        medicinesExpiring.setPredicate(medicine -> medicine.getExpiry().getExpiryDate() != null && calculateDaysToExpiry(medicine) < 10);
    }

    /**
     * Calculates and returns number of days from medicine's expiry date to today.
     * @param medicine
     * @return
     */
    private float calculateDaysToExpiry(Medicine medicine) {
        return ChronoUnit.DAYS.between(LocalDate.now(), medicine.getExpiry().getExpiryDate());
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoInventory() {
        return versionedInventory.canUndo();
    }

    @Override
    public boolean canRedoInventory() {
        return versionedInventory.canRedo();
    }

    @Override
    public void undoInventory() {
        versionedInventory.undo();
    }

    @Override
    public void redoInventory() {
        versionedInventory.redo();
    }

    @Override
    public void commitInventory() {
        versionedInventory.commit();
    }

    //=========== Selected medicine ===========================================================================

    @Override
    public ReadOnlyProperty<Medicine> selectedMedicineProperty() {
        return selectedMedicine;
    }

    @Override
    public Medicine getSelectedMedicine() {
        return selectedMedicine.getValue();
    }

    @Override
    public void setSelectedMedicine(Medicine medicine) {
        if (medicine != null && !filteredMedicines.contains(medicine)) {
            throw new MedicineNotFoundException();
        }
        selectedMedicine.setValue(medicine);
    }

    /**
     * Ensures {@code selectedMedicine} is a valid medicine in {@code filteredMedicines}.
     */
    private void ensureSelectedMedicineIsValid(ListChangeListener.Change<? extends Medicine> change) {
        while (change.next()) {
            if (selectedMedicine.getValue() == null) {
                // null is always a valid selected medicine, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedMedicineReplaced = change.wasReplaced() && change.getAddedSize()
                    == change.getRemovedSize() && change.getRemoved().contains(selectedMedicine.getValue());
            if (wasSelectedMedicineReplaced) {
                // Update selectedMedicine to its new value.
                int index = change.getRemoved().indexOf(selectedMedicine.getValue());
                selectedMedicine.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedMedicineRemoved = change.getRemoved().stream()
                    .anyMatch(removedMedicine -> selectedMedicine.getValue().isSameMedicine(removedMedicine));
            if (wasSelectedMedicineRemoved) {
                // Select the medicine that came before it in the list,
                // or clear the selection if there is no such medicine.
                selectedMedicine.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
        return versionedInventory.equals(other.versionedInventory)
                && userPrefs.equals(other.userPrefs)
                && filteredMedicines.equals(other.filteredMedicines)
                && Objects.equals(selectedMedicine.get(), other.selectedMedicine.get());
    }

}
