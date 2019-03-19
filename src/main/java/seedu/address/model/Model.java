package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.equipment.Equipment;
import seedu.address.model.equipment.WorkList;
import seedu.address.model.tag.Tag;

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
    void setAddressBook(ReadOnlyEquipmentManager addressBook);

    /** Returns the EquipmentManager */
    ReadOnlyEquipmentManager getAddressBook();

    /**
     * Returns true if a equipment with the same identity as {@code equipment} exists in the address book.
     */
    boolean hasPerson(Equipment equipment);

    /**
     * Deletes the given equipment.
     * The equipment must exist in the address book.
     */
    void deletePerson(Equipment target);

    /**
     * Adds the given equipment.
     * {@code equipment} must not already exist in the address book.
     */
    void addPerson(Equipment equipment);

    /**
     * Replaces the given equipment {@code target} with {@code editedEquipment}.
     * {@code target} must exist in the address book.
     * The equipment identity of {@code editedEquipment} must not be the same as another
     * existing equipment in the address book.
     */
    void setPerson(Equipment target, Equipment editedEquipment);

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
     * Selected equipment in the filtered equipment list.
     * null if no equipment is selected.
     */
    ReadOnlyProperty<Equipment> selectedPersonProperty();

    /**
     * Returns the selected equipment in the filtered equipment list.
     * null if no equipment is selected.
     */
    Equipment getSelectedPerson();

    /**
     * Sets the selected equipment in the filtered equipment list.
     */
    void setSelectedPerson(Equipment equipment);

    /** Removes the given {@code tag} from all {@code Equipment}s. */
    void deleteTag(Tag tag);

    void updatePerson(Equipment target, Equipment editedEquipment);

    /** Sorts the equipment list by name. */
    void sortByName();
}
