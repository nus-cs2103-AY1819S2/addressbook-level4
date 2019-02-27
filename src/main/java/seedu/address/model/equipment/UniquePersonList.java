package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.equipment.exceptions.DuplicatePersonException;
import seedu.address.model.equipment.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A equipment is considered unique by comparing using {@code Equipment#isSamePerson(Equipment)}. As such, adding and updating of
 * persons uses Equipment#isSamePerson(Equipment) for equality so as to ensure that the equipment being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a equipment uses Equipment#equals(Object) so
 * as to ensure that the equipment with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Equipment#isSamePerson(Equipment)
 */
public class UniquePersonList implements Iterable<Equipment> {

    private final ObservableList<Equipment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Equipment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent equipment as the given argument.
     */
    public boolean contains(Equipment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a equipment to the list.
     * The equipment must not already exist in the list.
     */
    public void add(Equipment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the equipment {@code target} in the list with {@code editedEquipment}.
     * {@code target} must exist in the list.
     * The equipment identity of {@code editedEquipment} must not be the same as another existing equipment in the list.
     */
    public void setPerson(Equipment target, Equipment editedEquipment) {
        requireAllNonNull(target, editedEquipment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedEquipment) && contains(editedEquipment)) {
            throw new DuplicatePersonException();
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
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code equipment}.
     * {@code equipment} must not contain duplicate equipment.
     */
    public void setPersons(List<Equipment> equipment) {
        requireAllNonNull(equipment);
        if (!personsAreUnique(equipment)) {
            throw new DuplicatePersonException();
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
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code equipment} contains only unique equipment.
     */
    private boolean personsAreUnique(List<Equipment> equipment) {
        for (int i = 0; i < equipment.size() - 1; i++) {
            for (int j = i + 1; j < equipment.size(); j++) {
                if (equipment.get(i).isSamePerson(equipment.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
