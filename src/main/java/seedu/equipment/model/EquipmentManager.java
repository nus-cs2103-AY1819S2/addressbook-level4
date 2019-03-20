package seedu.equipment.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.equipment.commons.util.InvalidationListenerManager;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.UniqueEquipmentList;
import seedu.equipment.model.tag.Tag;

/**
 * Wraps all data at the equipment-book level
 * Duplicates are not allowed (by .isSameEquipment comparison)
 */
public class EquipmentManager implements ReadOnlyEquipmentManager {

    private final UniqueEquipmentList equipment;
    private final UniqueWorkListList worklist;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        equipment = new UniqueEquipmentList();
        worklist = new UniqueWorkListList();
    }

    public EquipmentManager() {}

    /**
     * Creates an EquipmentManager using the Persons in the {@code toBeCopied}
     */
    public EquipmentManager(ReadOnlyEquipmentManager toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the equipment list with {@code equipment}.
     * {@code equipment} must not contain duplicate equipment.
     */
    public void setEquipment(List<Equipment> equipment) {
        this.equipment.setEquipments(equipment);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code EquipmentManager} with {@code newData}.
     */
    public void resetData(ReadOnlyEquipmentManager newData) {
        requireNonNull(newData);

        setEquipment(newData.getPersonList());
    }

    //// equipment-level operations

    /**
     * Returns true if a equipment with the same identity as {@code equipment} exists in the equipment book.
     */
    public boolean hasPerson(Equipment equipment) {
        requireNonNull(equipment);
        return this.equipment.contains(equipment);
    }

    /**
     * Returns true if a WorkList with the same WorkListId as {@code WorkList} exists in the Equipment Manager.
     */
    public boolean hasWorkList(WorkList worklist) {
        requireNonNull(worklist);
        return this.worklist.contains(worklist);
    }

    /**
     * Adds a equipment to the equipment book.
     * The equipment must not already exist in the equipment book.
     */
    public void addPerson(Equipment p) {
        equipment.add(p);
        indicateModified();
    }

    /**
     * Adds a WorkList to the Equipment Manager.
     * The WorkList must not already exist in the Equipment Manager.
     */
    public void addWorkList(WorkList w) {
        worklist.add(w);
        indicateModified();
    }

    /**
     * Replaces the given equipment {@code target} in the list with {@code editedEquipment}.
     * {@code target} must exist in the equipment book.
     * The equipment identity of {@code editedEquipment} must not be the same as another existing equipment
     * in the equipment book.
     */
    public void setPerson(Equipment target, Equipment editedEquipment) {
        requireNonNull(editedEquipment);

        equipment.setEquipment(target, editedEquipment);
        indicateModified();
    }

    public void sortByName() {
        equipment.sortByName();
    }

    /**
     * Removes {@code key} from this {@code EquipmentManager}.
     * {@code key} must exist in the equipment book.
     */
    public void removePerson(Equipment key) {
        equipment.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code EquipmentManager}.
     * {@code key} must exist in the Equipment Manager.
     */
    public void removeWorkList(WorkList key) {
        requireNonNull(key);
        worklist.remove(key);
        indicateModified();
    }

    /**
     * Replaces the given equipment {@code target} in the list with {@code editedEquipment}.
     * {@code target} must exist in the equipment book.
     * The equipment identity of {@code editedEquipment} must not be the same as another existing equipment
     * in the equipment book.
     */
    public void updatePerson(Equipment target, Equipment editedEquipment) {
        requireNonNull(editedEquipment);

        equipment.setEquipment(target, editedEquipment);
    }

    /**
     * Removes {@code tag} from {@code equipment} in this {@code EquipmentManager}.
     */
    private void removeTagFromPerson(Tag tag, Equipment equipment) {
        Set<Tag> newTags = new HashSet<>(equipment.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Equipment newEquipment =
                new Equipment(equipment.getName(), equipment.getPhone(),
                        equipment.getEmail(), equipment.getAddress(), equipment.getSerialNumber(), newTags);

        updatePerson(equipment, newEquipment);
    }

    /**
     * Removes {@code tag} from all equipment in this {@code EquipmentManager}.
     */
    public void removeTag(Tag tag) {
        equipment.forEach(person -> removeTagFromPerson(tag, person));
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the equipment book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return equipment.asUnmodifiableObservableList().size() + " equipment";
        // TODO: refine later
    }

    @Override
    public ObservableList<Equipment> getPersonList() {
        return equipment.asUnmodifiableObservableList();
    }

    public ObservableList<WorkList> getWorkListList() {
        return worklist.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EquipmentManager // instanceof handles nulls
                && equipment.equals(((EquipmentManager) other).equipment));
    }

    @Override
    public int hashCode() {
        return equipment.hashCode();
    }
}
