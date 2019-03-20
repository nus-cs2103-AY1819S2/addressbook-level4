package seedu.equipment.model;

import static java.util.Objects.requireNonNull;
//import static CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 *
 * @see WorkList#isSameWorkList(WorkList)
 */
public class UniqueWorkListList implements Iterable<WorkList> {

    private final ObservableList<WorkList> internalList = FXCollections.observableArrayList();
    private final ObservableList<WorkList> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent WorkList as the given argument.
     */
    public boolean contains(WorkList toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameWorkList);
    }

    /**
     * Adds a WorkList to the list.
     * The WorkList must not already exist in the list.
     */
    public void add(WorkList toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEquipmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent WorkList from the list.
     * The WorkList must exist in the list.
     */
    public void remove(WorkList toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EquipmentNotFoundException();
        }
    }

    /**
     * Sorts the WorkList list by WorkListId.
     */
    public void sortById() {
        Comparator<WorkList> byId = Comparator.comparing(WorkList -> WorkList.getId().getId());
        internalList.sort(byId);
    }

    /**
     * See whether the given list of WorkList is unique,
     * @param workListList a list of WorkList
     * @return true if the list of WorkList contains unique WorkLists.
     */
    public boolean areWorkListUnique(List<WorkList> workListList) {
        return this.workListAreUnique(workListList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<WorkList> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<WorkList> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueWorkListList // instanceof handles nulls
                && internalList.equals(((UniqueWorkListList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code worklist} contains only unique WorkList.
     */
    private boolean workListAreUnique(List<WorkList> worklist) {
        for (int i = 0; i < worklist.size() - 1; i++) {
            for (int j = i + 1; j < worklist.size(); j++) {
                if (worklist.get(i).isSameWorkList(worklist.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
