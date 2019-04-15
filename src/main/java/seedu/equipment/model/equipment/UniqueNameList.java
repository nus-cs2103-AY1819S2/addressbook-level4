package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.equipment.model.equipment.exceptions.EquipmentNotFoundException;

/**
 * A list of worklists that enforces uniqueness between its elements and does not allow nulls.
 * A WorkList is considered unique by comparing using {@code WorkList#isSameWorkList(WorkList)}.
 * As such, adding and updating of worklists uses WorkList#isSameWorkList(WorkList) for equality so as to ensure
 * that the WorkList being added or updated is unique in terms of identity in the UniqueWorkListList.
 * However, the removal of a WorkList uses WorkList#equals(Object) so as to ensure that the WorkList
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueNameList implements Iterable<Name> {

    private final ObservableList<Name> internalList = FXCollections.observableArrayList();
    private final ObservableList<Name> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent name as the given argument.
     */
    public boolean contains(Name toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameName);
    }

    /**
     * Adds a name to the list.
     * The WorkList must not already exist in the list.
     */
    public void add(Name toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
    }

    /**
     * Replaces the equipment {@code target} in the list with {@code editedEquipment}.
     * {@code target} must exist in the list.
     * The equipment identity of {@code editedEquipment} must not be the same as another existing equipment in the list.
     */
    public void setClient2(Name target, Name editedName) {
        requireAllNonNull(target, editedName);

        int index = internalList.indexOf(target);

        if (index == -1) {
            throw new EquipmentNotFoundException();
        }

        // I removed editing and inputting duplicated names because different equipments can ahve the same client names
        // a client name have own multiple equipments as long as no duplicated serial numbers

        internalList.set(index, editedName);

    }

    /**
     * Removes the equivalent client name from the list.
     * The equipment must exist in the list.
     */
    public void remove(Name toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EquipmentNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code equipment}.
     * {@code equipment} must not contain duplicate client.
     */
    public void setClient(List<Name> equipmentName) {
        requireAllNonNull(equipmentName);

        if (equipmentName.size() != 0) {
            for (int i = 0; i < equipmentName.size(); i++) {
                if (!internalList.contains(equipmentName.get(i))) {
                    internalList.add(equipmentName.get(i));
                }
            }
        } else {
            internalList.clear();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Name> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    @Override
    public Iterator<Name> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
