package seedu.equipment.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.equipment.commons.core.GuiSettings;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Equipment> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<WorkList> PREDICATE_SHOW_ALL_WORKLISTS = unused -> true;

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
     * Returns the user prefs' equipment book file path.
     */
    Path getEquipmentManagerFilePath();

    /**
     * Sets the user prefs' equipment book file path.
     */
    void setEquipmentManagerFilePath(Path addressBookFilePath);

    /**
     * Replaces equipment book data with the data in {@code addressBook}.
     */
    void setEquipmentManager(ReadOnlyEquipmentManager addressBook);

    /** Returns the EquipmentManager */
    ReadOnlyEquipmentManager getEquipmentManager();

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyEquipmentManager newData);

    /**
     * Returns true if a equipment with the same identity as {@code equipment} exists in the equipment manager.
     */
    boolean hasEquipment(Equipment equipment);

    /**
     * Returns true if a WorkList with the same identity as {@code workList} exists in the equipment manager.
     */
    boolean hasWorkList(WorkList workList);

    /**
     * Deletes the given equipment.
     * The equipment must exist in the equipment book.
     */
    void deleteEquipment(Equipment target);

    /**
     * Adds the given equipment.
     * {@code equipment} must not already exist in the equipment manager.
     */
    void addEquipment(Equipment equipment);

    /**
     * Adds the given WorkList.
     * {@code workList} must not already exist in the equipment manager.
     */
    void addWorkList(WorkList workList);

    /**
     * Replaces the given equipment {@code target} with {@code editedEquipment}.
     * {@code target} must exist in the equipment book.
     * The equipment identity of {@code editedEquipment} must not be the same as another
     * existing equipment in the equipment book.
     */
    void setEquipment(Equipment target, Equipment editedEquipment);

    /** Returns an unmodifiable view of the filtered equipment list */
    ObservableList<Equipment> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered worklist list */
    ObservableList<WorkList> getFilteredWorkListList();

    /**
     * Updates the filter of the filtered equipment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Equipment> predicate);

    /**
     * Updates the filter of the filtered WorkList list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWorkListList(Predicate<WorkList> predicate);

    /**
     * Returns true if the model has previous equipment book states to restore.
     */
    boolean canUndoEquipmentManager();

    /**
     * Returns true if the model has undone equipment book states to restore.
     */
    boolean canRedoEquipmentManager();

    /**
     * Restores the model's equipment book to its previous state.
     */
    void undoEquipmentManager();

    /**
     * Restores the model's equipment book to its previously undone state.
     */
    void redoEquipmentManager();

    /**
     * Saves the current equipment book state for undo/redo.
     */
    void commitEquipmentManager();

    /**
     * Selected equipment in the filtered equipment list.
     * null if no equipment is selected.
     */
    ReadOnlyProperty<Equipment> selectedEquipmentProperty();

    /**
     * Returns the selected equipment in the filtered equipment list.
     * null if no equipment is selected.
     */
    Equipment getSelectedEquipment();

    /**
     * Sets the selected equipment in the filtered equipment list.
     */
    void setSelectedEquipment(Equipment equipment);

    /** Removes the given {@code tag} from all {@code Equipment}s. */
    void deleteTag(Tag tag);

    void updateEquipment(Equipment target, Equipment editedEquipment);

}
