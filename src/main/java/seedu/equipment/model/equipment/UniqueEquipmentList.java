package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.equipment.model.equipment.exceptions.DuplicateEquipmentException;
import seedu.equipment.model.equipment.exceptions.EquipmentNotFoundException;

/**
 * A list of equipments that enforces uniqueness between its elements and does not allow nulls.
 * A equipment is considered unique by comparing using {@code Equipment#isSameEquipment(Equipment)}.
 * As such, adding and updating of equipments uses Equipment#isSameEquipment(Equipment) for equality so as to ensure
 * that the equipment being added or updated is unique in terms of identity in the UniqueEquipmentList.
 * However, the removal of a equipment uses Equipment#equals(Object) so as to ensure that the equipment
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Equipment#isSameEquipment(Equipment)
 */
public class UniqueEquipmentList implements Iterable<Equipment> {

    private final ObservableList<Equipment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Equipment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent equipment as the given argument.
     */
    public boolean contains(Equipment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEquipment);
    }

    /**
     * Adds a equipment to the list.
     * The equipment must not already exist in the list.
     */
    public void add(Equipment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEquipmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the equipment {@code target} in the list with {@code editedEquipment}.
     * {@code target} must exist in the list.
     * The equipment identity of {@code editedEquipment} must not be the same as another existing equipment in the list.
     */
    public void setEquipment(Equipment target, Equipment editedEquipment) {
        requireAllNonNull(target, editedEquipment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EquipmentNotFoundException();
        }

        if (!target.isSameEquipment(editedEquipment) && contains(editedEquipment)) {
            throw new DuplicateEquipmentException();
        }

        internalList.set(index, editedEquipment);
    }

    /**
     * Removes the equivalent equipment from the list.
     * The equipment must exist in the list.
     */
    public void remove(Equipment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EquipmentNotFoundException();
        }
    }

    public void setEquipments(UniqueEquipmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code equipment}.
     * {@code equipment} must not contain duplicate equipment.
     */
    public void setEquipments(List<Equipment> equipment) {
        requireAllNonNull(equipment);
        if (!equipmentsAreUnique(equipment)) {
            throw new DuplicateEquipmentException();
        }

        internalList.setAll(equipment);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Equipment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Equipment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEquipmentList // instanceof handles nulls
                        && internalList.equals(((UniqueEquipmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code equipment} contains only unique equipment.
     */
    private boolean equipmentsAreUnique(List<Equipment> equipment) {
        for (int i = 0; i < equipment.size() - 1; i++) {
            for (int j = i + 1; j < equipment.size(); j++) {
                if (equipment.get(i).isSameEquipment(equipment.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
