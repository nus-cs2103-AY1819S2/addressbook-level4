package seedu.address.model.apparel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.apparel.exceptions.ApparelNotFoundException;
import seedu.address.model.apparel.exceptions.DuplicateApparelException;

/**
 * A list of apparels that enforces uniqueness between its elements and does not allow nulls.
 * An apparel is considered unique by comparing using {@code Apparel#isSameApparel(Apparel)}.
 * As such, adding and updating of apparels uses Apparel#isSameApparel(Apparel) for equality
 * so as to ensure that the apparel being added or updated is unique in terms of identity in
 * the UniqueApparelList. However, the removal of a apparel uses Apparel#equals(Object) so
 * as to ensure that the apparel with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Apparel#isSameApparel(Apparel)
 */
public class UniqueApparelList implements Iterable<Apparel> {

    private final ObservableList<Apparel> internalList = FXCollections.observableArrayList();
    private final ObservableList<Apparel> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent apparel as the given argument.
     */
    public boolean contains(Apparel toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameApparel);
    }

    /**
     * Adds a apparel to the list.
     * The apparel must not already exist in the list.
     */
    public void add(Apparel toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateApparelException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the apparel {@code target} in the list with {@code editedApparel}.
     * {@code target} must exist in the list.
     * The apparel identity of {@code editedApparel} must not be the same as another existing apparel in the list.
     */
    public void setApparel(Apparel target, Apparel editedApparel) {
        requireAllNonNull(target, editedApparel);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ApparelNotFoundException();
        }

        if (!target.isSameApparel(editedApparel) && contains(editedApparel)) {
            throw new DuplicateApparelException();
        }

        internalList.set(index, editedApparel);
    }

    /**
     * Removes the equivalent apparel from the list.
     * The apparel must exist in the list.
     */
    public void remove(Apparel toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ApparelNotFoundException();
        }
    }

    public void setApparels(UniqueApparelList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code apparels}.
     * {@code apparels} must not contain duplicate apparels.
     */
    public void setApparels(List<Apparel> apparels) {
        requireAllNonNull(apparels);
        if (!apparelsAreUnique(apparels)) {
            throw new DuplicateApparelException();
        }

        internalList.setAll(apparels);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Apparel> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Apparel> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueApparelList // instanceof handles nulls
                        && internalList.equals(((UniqueApparelList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code apparels} contains only unique apparels.
     */
    private boolean apparelsAreUnique(List<Apparel> apparels) {
        for (int i = 0; i < apparels.size() - 1; i++) {
            for (int j = i + 1; j < apparels.size(); j++) {
                if (apparels.get(i).isSameApparel(apparels.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
