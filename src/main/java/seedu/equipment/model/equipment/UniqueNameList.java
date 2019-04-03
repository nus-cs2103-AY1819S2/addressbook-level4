package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
//import static CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.exceptions.DuplicateEquipmentException;
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


    /**
     * Returns true if the list contains an equivalent name as the given argument.
     */
    public boolean contains(Name toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameName);
    }

    /**
     * Adds a WorkList to the list.
     * The WorkList must not already exist in the list.
     */
    public void add(Name toAdd) {
        requireNonNull(toAdd);
        if (!contains(toAdd)) {
            internalList.add(toAdd);
        }
    }

    public ObservableList<Name> getClientList() {
        return internalList;
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
