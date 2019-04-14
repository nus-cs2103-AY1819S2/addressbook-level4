package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.WarningPanelSettings;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.exceptions.MedicineNotFoundException;
import seedu.address.model.medicine.predicates.MedicineExpiryThresholdPredicate;
import seedu.address.model.medicine.predicates.MedicineLowStockThresholdPredicate;
import seedu.address.model.threshold.Threshold;

/**
 * Represents the in-memory model of the inventory data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final Comparator<Medicine> comparator = Comparator.naturalOrder();

    private final VersionedInventory versionedInventory;
    private final UserPrefs userPrefs;
    private final WarningPanelPredicateAccessor warningPanelPredicateAccessor;
    private final FilteredList<Medicine> filteredMedicines;
    private final FilteredList<Medicine> medicinesExpiring;
    private final FilteredList<Medicine> medicinesLowStock;
    private final SimpleObjectProperty<Medicine> selectedMedicine = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<InformationPanelSettings> informationPanelSettings =
            new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given inventory and userPrefs.
     */
    public ModelManager(ReadOnlyInventory inventory, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(inventory, userPrefs);

        logger.fine("Initializing with inventory: " + inventory + " and user prefs " + userPrefs);

        versionedInventory = new VersionedInventory(inventory);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredMedicines = new FilteredList<>(versionedInventory.getSortedMedicineList(comparator));
        filteredMedicines.addListener(this::ensureSelectedMedicineIsValid);

        warningPanelPredicateAccessor = new WarningPanelPredicateAccessor();
        medicinesExpiring = new FilteredList<>(versionedInventory.getSortedMedicineList(comparator));
        medicinesLowStock = new FilteredList<>(versionedInventory.getSortedMedicineList(comparator));
        configureWarningPanelLists();

        informationPanelSettings.setValue(userPrefs.getInformationPanelSettings());
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
    public WarningPanelSettings getWarningPanelSettings() {
        return userPrefs.getWarningPanelSettings();
    }

    @Override
    public void setWarningPanelSettings(WarningPanelSettings warningPanelSettings) {
        requireNonNull(warningPanelSettings);
        userPrefs.setWarningPanelSettings(warningPanelSettings);
    }

    @Override
    public void configureWarningPanelLists() {
        try {
            warningPanelPredicateAccessor
                    .updateMedicineExpiringThreshold(userPrefs.getWarningPanelSettings().getExpiryThresholdValue());
            warningPanelPredicateAccessor
                    .updateBatchExpiringThreshold(userPrefs.getWarningPanelSettings().getExpiryThresholdValue());

        } catch (IllegalArgumentException ie) {
            // last threshold set was maximum for medicines expiring on 31/12/9999, but from a different start date
            warningPanelPredicateAccessor.updateMedicineExpiringThreshold(Threshold.MAX_EXPIRY_THRESHOLD);
            warningPanelPredicateAccessor.updateBatchExpiringThreshold(Threshold.MAX_EXPIRY_THRESHOLD);
            setWarningPanelSettings(new WarningPanelSettings(
                    Threshold.MAX_EXPIRY_THRESHOLD, userPrefs.getWarningPanelSettings().getLowStockThresholdValue()));

        } finally {
            warningPanelPredicateAccessor
                    .updateMedicineLowStockThreshold(userPrefs.getWarningPanelSettings().getLowStockThresholdValue());
            updateFilteredExpiringMedicineList(warningPanelPredicateAccessor.getMedicineExpiryPredicate());
            updateFilteredLowStockMedicineList(warningPanelPredicateAccessor.getMedicineLowStockPredicate());
        }
    }

    @Override
    public void setInformationPanelSettings(InformationPanelSettings informationPanelSettings) {
        requireNonNull(informationPanelSettings);
        userPrefs.setInformationPanelSettings(informationPanelSettings);
        this.informationPanelSettings.setValue(informationPanelSettings);
    }

    @Override
    public ReadOnlyProperty<InformationPanelSettings> getInformationPanelSettings() {
        return informationPanelSettings;
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
    public ObservableList<Medicine> getLowStockMedicinesList() {
        return medicinesLowStock;
    }

    @Override
    public WarningPanelPredicateAccessor getWarningPanelPredicateAccessor() {
        return warningPanelPredicateAccessor;
    }

    @Override
    public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        filteredMedicines.setPredicate(predicate);
    }

    @Override
    public void updateFilteredExpiringMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        medicinesExpiring.setPredicate(predicate);
    }

    @Override
    public void updateFilteredLowStockMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        medicinesLowStock.setPredicate(predicate);
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

    //=========== Warning Panel =========================================================================

    @Override
    public void changeWarningPanelListThreshold(WarningPanelPredicateType type, Threshold threshold) {
        int thresholdValue = threshold.getNumericValue();

        if (type.equals(WarningPanelPredicateType.EXPIRY)) {
            updateFilteredExpiringMedicineList(new MedicineExpiryThresholdPredicate(threshold));

            warningPanelPredicateAccessor.updateMedicineExpiringThreshold(thresholdValue);
            warningPanelPredicateAccessor.updateBatchExpiringThreshold(thresholdValue);

            // update user prefs
            setWarningPanelSettings(new WarningPanelSettings(
                    thresholdValue, warningPanelPredicateAccessor.getLowStockThreshold().getNumericValue()));

        } else {
            // WarningPanelPredicateType.LOW_STOCK
            updateFilteredLowStockMedicineList(new MedicineLowStockThresholdPredicate(threshold));

            warningPanelPredicateAccessor.updateMedicineLowStockThreshold(thresholdValue);

            // update user prefs
            setWarningPanelSettings(new WarningPanelSettings(
                    warningPanelPredicateAccessor.getExpiryThreshold().getNumericValue(), thresholdValue));
        }
    }

    @Override
    public Threshold getWarningPanelThreshold(WarningPanelPredicateType type) {
        if (type.equals(WarningPanelPredicateType.EXPIRY)) {
            return warningPanelPredicateAccessor.getExpiryThreshold();
        } else {
            // WarningPanelPredicateType.LOW_STOCK
            return warningPanelPredicateAccessor.getLowStockThreshold();
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
                && warningPanelPredicateAccessor.equals(other.warningPanelPredicateAccessor)
                && filteredMedicines.equals(other.filteredMedicines)
                && medicinesExpiring.equals(other.medicinesExpiring)
                && medicinesLowStock.equals(other.medicinesLowStock)
                && Objects.equals(selectedMedicine.get(), other.selectedMedicine.get());
    }

}
