package seedu.equipment.model;

import static java.util.Objects.requireNonNull;

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
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.commons.core.LogsCenter;
import seedu.equipment.commons.util.CollectionUtil;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.equipment.exceptions.EquipmentNotFoundException;
import seedu.equipment.model.tag.Tag;

/**
 * Represents the in-memory model of the equipment manager data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedEquipmentManager versionedEquipmentManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Name> filteredClient;
    private final FilteredList<Equipment> filteredEquipments;
    private final FilteredList<WorkList> filteredWorkList;
    private final SimpleObjectProperty<Equipment> selectedEquipment = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<WorkList> selectedWorkList = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Name> selectedClient = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given equipmentManager and userPrefs.
     */
    public ModelManager(ReadOnlyEquipmentManager equipmentManager, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(equipmentManager, userPrefs);

        logger.fine("Initializing with equipment manager: " + equipmentManager + " and user prefs " + userPrefs);

        versionedEquipmentManager = new VersionedEquipmentManager(equipmentManager);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredEquipments = new FilteredList<>(versionedEquipmentManager.getEquipmentList());
        filteredEquipments.addListener(this::ensureSelectedPersonIsValid);
        filteredWorkList = new FilteredList<>(versionedEquipmentManager.getWorkListList());
        filteredClient = new FilteredList<>(versionedEquipmentManager.getClientList());
        //filteredWorkList.addListener(this::ensureSelectedworkListIsValid);
    }

    public ModelManager() {
        this(new EquipmentManager(), new UserPrefs());
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
    public Path getEquipmentManagerFilePath() {
        return userPrefs.getEquipmentManagerFilePath();
    }

    @Override
    public void setEquipmentManagerFilePath(Path equipmentManagerFilePath) {
        requireNonNull(equipmentManagerFilePath);
        userPrefs.setEquipmentManagerFilePath(equipmentManagerFilePath);
    }

    //=========== EquipmentManager ================================================================================

    @Override
    public void setEquipmentManager(ReadOnlyEquipmentManager equipmentManager) {
        versionedEquipmentManager.resetData(equipmentManager);
    }

    @Override
    public ReadOnlyEquipmentManager getEquipmentManager() {
        return versionedEquipmentManager;
    }


    @Override
    public boolean hasEquipment(Equipment equipment) {
        requireNonNull(equipment);
        return versionedEquipmentManager.hasEquipment(equipment);
    }

    @Override
    public boolean hasClient(Name name) {
        requireNonNull(name);
        return versionedEquipmentManager.hasClient(name);
    }

    @Override
    public boolean hasWorkList(WorkList workList) {
        requireNonNull(workList);
        return versionedEquipmentManager.hasWorkList(workList);
    }

    @Override
    public void deleteEquipment(Equipment target) {
        versionedEquipmentManager.removePerson(target);
    }

    @Override
    public void deleteWorkList(WorkList target) {
        versionedEquipmentManager.removeWorkList(target);
    }

    @Override
    public void addEquipment(Equipment equipment) {
        versionedEquipmentManager.addPerson(equipment);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_EQUIPMENT);
    }

    @Override
    public void addWorkList(WorkList workList) {
        versionedEquipmentManager.addWorkList(workList);
        updateFilteredWorkListList(PREDICATE_SHOW_ALL_WORKLISTS);
    }

    @Override
    public void addClient(Name equipment) {
        versionedEquipmentManager.addClient(equipment);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENT);
    }

    @Override
    public void putEquipment(WorkListId workListId, SerialNumber serialNumber) {
        versionedEquipmentManager.putEquipment(workListId, serialNumber);
        updateFilteredWorkListList(PREDICATE_SHOW_ALL_WORKLISTS);
    }

    @Override
    public void removeEquipment(WorkListId workListId, SerialNumber serialNumber) {
        versionedEquipmentManager.removeEquipment(workListId, serialNumber);
        updateFilteredWorkListList(PREDICATE_SHOW_ALL_WORKLISTS);
    }

    @Override
    public boolean hasEquipmentWithSerialNumber(SerialNumber serialNumber) {
        requireNonNull(serialNumber);
        return versionedEquipmentManager.hasEquipmentWithSerialNumber(serialNumber);
    }

    @Override
    public boolean hasEquipmentInWorkList(WorkListId workListId, SerialNumber serialNumber) {
        requireNonNull(workListId);
        requireNonNull(serialNumber);
        return versionedEquipmentManager.hasEquipmentInWorkList(workListId, serialNumber);
    }

    @Override
    public void resetData(ReadOnlyEquipmentManager newData) {
        versionedEquipmentManager.resetData(newData);

    }

    @Override
    public void setEquipment(Equipment target, Equipment editedEquipment) {
        CollectionUtil.requireAllNonNull(target, editedEquipment);
        versionedEquipmentManager.setPerson(target, editedEquipment);
    }

    @Override
    public void setClient(Name target, Name editedEquipment) {
        CollectionUtil.requireAllNonNull(target, editedEquipment);
        versionedEquipmentManager.setClient(target, editedEquipment);
    }

    @Override
    public void updateEquipment(Equipment target, Equipment editedEquipment) {
        CollectionUtil.requireAllNonNull(target, editedEquipment);
        versionedEquipmentManager.updateEquipment(target, editedEquipment);
    }

    @Override
    public void sortFilteredEquipmentList(Comparator<Equipment> comparator) {
        requireNonNull(comparator);
        versionedEquipmentManager.sortEquipmentList(comparator);

    }


    //=========== Filtered WorkList List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code WorkList} backed by the internal list of
     * {@code versionedEquipmentManager}
     */
    public ObservableList<WorkList> getFilteredWorkListList() {
        return filteredWorkList;
    }

    @Override
    public void updateFilteredWorkListList(Predicate<WorkList> predicate) {
        requireNonNull(predicate);
        filteredWorkList.setPredicate(predicate);
    }

    //=========== Filtered Equipment List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Equipment} backed by the internal list of
     * {@code versionedEquipmentManager}
     */
    @Override
    public ObservableList<Equipment> getFilteredPersonList() {
        return filteredEquipments;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Equipment> predicate) {
        requireNonNull(predicate);
        filteredEquipments.setPredicate(predicate);
    }

    //=========== Filtered Client List Accessors =============================================================
    /**
     * Returns an unmodifiable view of the list of {@code Equipment} backed by the internal list of
     * {@code versionedEquipmentManager}
     */
    @Override
    public ObservableList<Name> getFilteredClientList() {
        return filteredClient;
    }

    @Override
    public void updateFilteredClientList(Predicate<Name> predicate) {
        requireNonNull(predicate);
        filteredClient.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoEquipmentManager() {
        return versionedEquipmentManager.canUndo();
    }

    @Override
    public boolean canRedoEquipmentManager() {
        return versionedEquipmentManager.canRedo();
    }

    @Override
    public void undoEquipmentManager() {
        versionedEquipmentManager.undo();
    }

    @Override
    public void redoEquipmentManager() {
        versionedEquipmentManager.redo();
    }

    @Override
    public void commitEquipmentManager() {
        versionedEquipmentManager.commit();
    }

    //=========== Selected WorkList ===========================================================================

    @Override
    public ReadOnlyProperty<WorkList> selectedWorkListProperty() {
        return selectedWorkList;
    }

    @Override
    public WorkList getSelectedWorkList() {
        return selectedWorkList.getValue();
    }

    @Override
    public void setSelectedWorkList(WorkList workList) {
        if (workList != null && !filteredWorkList.contains(workList)) {
            throw new EquipmentNotFoundException();
        }
        selectedWorkList.setValue(workList);
    }

    //=========== Selected equipment ===========================================================================

    @Override
    public ReadOnlyProperty<Equipment> selectedEquipmentProperty() {
        return selectedEquipment;
    }

    @Override
    public Equipment getSelectedEquipment() {
        return selectedEquipment.getValue();
    }

    @Override
    public void setSelectedEquipment(Equipment equipment) {
        if (equipment != null && !filteredEquipments.contains(equipment)) {
            throw new EquipmentNotFoundException();
        }
        selectedEquipment.setValue(equipment);
    }

    //=========== Selected client ===========================================================================

    @Override
    public ReadOnlyProperty<Name> selectedClientProperty() {
        return selectedClient;
    }

    @Override
    public Name getSelectedClient() {
        return selectedClient.getValue();
    }

    @Override
    public void setSelectedClient(Name equipment) {
        if (equipment != null && !filteredClient.contains(equipment)) {
            throw new EquipmentNotFoundException();
        }
        selectedClient.setValue(equipment);
    }

    @Override
    public void unsetSelectedEquipment() {
        selectedEquipment.setValue(null);
    }

    @Override
    public void deleteTag(Tag tag) {
        versionedEquipmentManager.removeTag(tag);
    }

    /**
     * Ensures {@code selectedEquipment} is a valid equipment in {@code filteredEquipments}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Equipment> change) {
        while (change.next()) {
            if (selectedEquipment.getValue() == null) {
                // null is always a valid selected equipment, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedEquipmentReplaced = change.wasReplaced() && change.getAddedSize() == change
                    .getRemovedSize() && change.getRemoved().contains(selectedEquipment.getValue());
            if (wasSelectedEquipmentReplaced) {
                // Update selectedEquipment to its new value.
                int index = change.getRemoved().indexOf(selectedEquipment.getValue());
                selectedEquipment.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedEquipment.getValue().isSameEquipment(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the equipment that came before it in the list,
                // or clear the selection if there is no such equipment.
                selectedEquipment.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
        return versionedEquipmentManager.equals(other.versionedEquipmentManager)
                && userPrefs.equals(other.userPrefs)
                && filteredEquipments.equals(other.filteredEquipments)
                && Objects.equals(selectedEquipment.get(), other.selectedEquipment.get());
    }
}
